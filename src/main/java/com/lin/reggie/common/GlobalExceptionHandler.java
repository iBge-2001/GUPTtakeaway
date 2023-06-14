package com.lin.reggie.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/*
* 全局异处捕获
*Springl的@ExceptionHandler可以用来统一处理方法抛出的异常
* */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@Component
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException exception){
        log.error(exception.getMessage());

        if (exception.getMessage().contains("Duplicate entry")){
            String[] split = exception.getMessage().split(" ");
            String msg = split[2]+"已存在";
            return Result.error(msg);
        }
        return Result.error("未知错误");
    }
    @ExceptionHandler(CustomException.class)
    public Result<String> exceptionHandler(CustomException exception){
        log.error(exception.getMessage());
        return Result.error(exception.getMessage());
    }
}
