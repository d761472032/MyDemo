package com.demo.skill;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 */
public class GlobalExceptionCatch {

    @RestControllerAdvice
    static public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public String handleException(Exception e) {
            if (e instanceof ArithmeticException) {
                return "数据异常";
            }
            if (e instanceof Exception) {
                return "服务器内部异常";
            }
            return null;
        }
    }

}
