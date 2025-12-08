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
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 成功
     */
    private boolean success;

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
        result.code = 200;
        result.message = "success";
        result.data = data;
        result.success = true;
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
     * @param message 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> success(String message, T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = 200;
        result.message = message;
        result.data = data;
        result.success = true;
        return result;
    }

    /**
     * 失败响应
     *
     * @param code    状态码
     * @param message 消息
     * @param <T>     数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> fail(Integer code, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = code;
        result.message = message;
        result.success = false;
        return result;
    }

    /**
     * 失败响应
     *
     * @param message 消息
     * @param <T>     数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> fail(String message) {
        return fail(500, message);
    }

    /**
     * 失败响应
     *
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return ResponseResult
     */
    public static <T> ResponseResult<T> fail(Integer code, String message, T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = code;
        result.message = message;
        result.data = data;
        result.success = false;
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}