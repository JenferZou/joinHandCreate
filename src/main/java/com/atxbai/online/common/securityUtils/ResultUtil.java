package com.atxbai.online.common.securityUtils;


import com.atxbai.online.common.responseUtils.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 小白
 * @version 1.0
 * create: 2023-12-28 17:00
 * content: 用于在过滤器中方便的返回 JSON 参数
 *  通过 response.writer 写回给前端
 */
public class ResultUtil {

    /**
     * 成功响参
     * @param response
     * @param result
     * @throws IOException
     */
    public static void ok(HttpServletResponse response, Response<?> result ) throws IOException {
        // 设置返回的编码
        response.setCharacterEncoding("UTF-8");
        // 设置返回的状态码 HttpStatus 对象添加状态码
        response.setStatus(HttpStatus.OK.value());
        // 设置返回的类型
        response.setContentType("application/json");

        // 使用 response 的 write
        PrintWriter writer = response.getWriter();

        // 使用 JackJson
        ObjectMapper mapper = new ObjectMapper();

        // 将信息返回给前端
        writer.write(mapper.writeValueAsString(result));
        // 刷新数据 和 关闭
        writer.flush();
        writer.close();
    }

    /**
     * 失败响参 返回 200 的响应结果
     * @param response
     * @param result
     * @throws IOException
     */
    public static void fail(HttpServletResponse response,Response<?> result) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }

    /**
     * 失败响参
     * @param response
     * @param status 可指定响应码，如 401 等
     * @param result
     * @throws IOException
     */
    public static void fail(HttpServletResponse response, int status, Response<?> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
