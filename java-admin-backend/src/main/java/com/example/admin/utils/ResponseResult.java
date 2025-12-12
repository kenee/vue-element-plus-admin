package com.example.admin.utils;

/**
 * 全局响应结果类
 *
 * @author example
 */
public class ResponseResult<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    private ResponseResult() {
    }

    /**
     * 成功响应
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = 0;
        result.msg = "success";
        result.data = data;
        return result;
    }

    /**
     * 成功响应
     *
     * @param <T> 数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    /**
     * 成功响应
     *
     * @param msg  消息
     * @param data 数据
     * @param <T>  数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> success(String msg, T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = 0;
        result.msg = msg;
        result.data = data;
        return result;
    }

    /**
     * 失败响应
     *
     * @param code 状态码
     * @param msg  消息
     * @param <T>  数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> fail(Integer code, String msg) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = code;
        result.msg = msg;
        return result;
    }

    /**
     * 失败响应
     *
     * @param msg 消息
     * @param <T> 数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> fail(String msg) {
        return fail(500, msg);
    }

    /**
     * 失败响应
     *
     * @param code 状态码
     * @param msg  消息
     * @param data 数据
     * @param <T>  数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> fail(Integer code, String msg, T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = code;
        result.msg = msg;
        result.data = data;
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}