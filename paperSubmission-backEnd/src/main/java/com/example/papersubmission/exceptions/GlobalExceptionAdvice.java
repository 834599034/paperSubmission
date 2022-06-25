package com.example.papersubmission.exceptions;

import com.example.papersubmission.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.papersubmission.constant.BizCodeEnum.TOKEN_EXCEPTION;

@Slf4j
@RestControllerAdvice(basePackages = "com.example.papersubmission.controller")
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = TokenException.class)
    public CommonResult handlTokenException(TokenException e){
        return CommonResult.error(TOKEN_EXCEPTION.getCode(),e.getMessage());
    }



    @ExceptionHandler(value = Throwable.class)
    public CommonResult handleException(Throwable e){
        log.error(e.getMessage());
        return CommonResult.error(400,e.getMessage());
    }


}
