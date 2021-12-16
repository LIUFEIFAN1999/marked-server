package com.mark.blog.handler;

import com.mark.blog.controller.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 对controller层进行AOP拦截处理
 */
@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e){
        e.printStackTrace();
        return Result.fail(-999, "系统异常");
    }
}
