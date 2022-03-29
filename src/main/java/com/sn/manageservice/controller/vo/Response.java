package com.sn.manageservice.controller.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 向南
 * @date 2021/12/30 19:37
 * @description
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private int code;
    private String msg;
    private T data;

    public static <T> Response<T> ok(T data) {
        int okCode = 200;
        return new Response<>(okCode, "请求成功", data);
    }
    public static <T> Response<T> fail(String msg, T data) {
        int okCode = 500;
        return new Response<>(okCode, msg, data);
    }
}
