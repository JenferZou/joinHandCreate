package com.atxbai.online.common.responseUtils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-09 19:17
 * @content: 分页统一返回对象,该类继承 Response 对象，同时该对象的 data 类型为 List
 */
@Data
public class PageResponse<T> extends Response<List<T>> {

    /**
     * 总记录数
     */
    private long total = 0L;

    /**
     * 每页显示的记录数，默认每页显示 10 条
     */
    private long size = 10L;

    /**
     * 当前页码
     */
    private long current;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 成功返回
     * @param page Mybatis Plus 提供的分页接口
     * @param data 返回的类型
     * @return
     * @param <T> 泛型
     */
    public static <T> PageResponse<T> success(IPage page,List<T> data){
        // 创建一个返回对象
        PageResponse<T> response = new PageResponse<>();
        // 设置参数
        response.setSuccess(true);
        // 设置当前页面
        response.setCurrent(Objects.isNull(page) ? 1L : page.getCurrent());
        // 每页显示的页数
        response.setSize(Objects.isNull(page) ? 10L : page.getSize());
        // 总页数
        response.setPages(Objects.isNull(page) ? 0L : page.getPages());
        // 总记录数
        response.setTotal(Objects.isNull(page) ? 0L : page.getTotal());
        // 返回数据
        response.setData(data);
        return response;
    }

}
