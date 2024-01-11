package com.atxbai.online.common.securityUtils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 * @author 小白
 * @version 1.0
 * create: 2023-12-28 08:53
 * content: JWT 工具包
 */

@Component
public class JwtTokenHelper implements InitializingBean {
    /**
     * 签发人
     */
    @Value("${jwt.issuer}")
    private String issuer;
    /**
     * 秘钥，(以 Base64 编码设置为可以 key )
     */
    private Key key;

    /**
     * JWT 解析
     */
    private JwtParser jwtParser;

    /**
     * Token 失效时间 (分钟)
     */
    @Value("${jwt.tokenExpireTime}")
    private Long tokenExpireTime;

    /**
     * 解码配置文件中配置的 Base 64 编码 key 为秘钥
     * @param base64Key
     */
    @Value("${jwt.secret}")
    public void setBase64Key(String base64Key) {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
    }


    /**
     * 初始化 JwtParser  (jwt解析器)
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        jwtParser = Jwts.parserBuilder().requireIssuer(issuer)
                .setSigningKey(key).setAllowedClockSkewSeconds(10)
                .build();
    }

    /**
     * 生成 Token ()
     * @param username
     * @return
     */
    public String generateToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        // Token 的失效时间
        LocalDateTime expireTime = now.plusHours(tokenExpireTime);

        return Jwts.builder().setSubject(username) // 用户名
                .setIssuer(issuer) // 签名人
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())) // 创建JWT时的时间戳
                .setExpiration(Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant())) // 设置过期时间
                .signWith(key) // 密钥
                .compact(); // token
    }

    /**
     * 解析 Token
     * @param token
     * @return 当遇到问题时，把问题抛出
     */
    public Jws<Claims> parseToken(String token) {
        try {
            return jwtParser.parseClaimsJws(token);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Token 不可用", e);
        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("Token 失效", e);
        }
    }

    /**
     * 生成一个 Base64 的安全秘钥
     * @return
     */
    private static String generateBase64Key() {
        // 生成安全秘钥
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        // 将密钥进行 Base64 编码
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        return base64Key;
    }

    /**
     * 校验 Token 是否可用
     * @param token
     * @return 当效验失败时，throw 异常，给过滤器判断
     */
    public void validateToken(String token){
        jwtParser.parseClaimsJws(token);
    }

    /**
     * 解析 Token 获取用户名
     * @param token
     * @return
     */
    public String getUsernameByToken(String token){
        try {
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            return username;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String key = generateBase64Key();
        System.out.println("key: " + key);
   }

}
