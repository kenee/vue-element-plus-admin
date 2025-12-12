package com.example.admin.exception;

import com.example.admin.utils.ResponseResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 *
 * @author example
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     *
     * @param ex      异常
     * @param request 请求
     * @return ResponseResult
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseResult<?> handleBusinessException(BusinessException ex, WebRequest request) {
        logger.error("Business exception: {}, request: {}", ex.getMessage(), request.getDescription(false));
        return ResponseResult.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理认证异常
     *
     * @param ex      异常
     * @param request 请求
     * @return ResponseResult
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseResult<?> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        logger.error("Authentication exception: {}, request: {}", ex.getMessage(), request.getDescription(false));
        String message;
        if (ex instanceof BadCredentialsException) {
            message = "用户名或密码错误";
        } else if (ex instanceof UsernameNotFoundException) {
            message = "用户不存在";
        } else {
            message = "认证失败";
        }
        return ResponseResult.fail(401, message);
    }

    /**
     * 处理参数验证异常
     *
     * @param ex      异常
     * @param request 请求
     * @return ResponseResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        logger.error("Validation exception: {}, request: {}", ex.getMessage(), request.getDescription(false));
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseResult.fail(400, "参数验证失败", errors);
    }

    /**
     * 处理其他异常
     *
     * @param ex      异常
     * @param request 请求
     * @return ResponseResult
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<?> handleException(Exception ex, WebRequest request) {
        logger.error("Unexpected exception: {}, request: {}", ex.getMessage(), request.getDescription(false), ex);
        return ResponseResult.fail(500, "服务器内部错误");
    }

}