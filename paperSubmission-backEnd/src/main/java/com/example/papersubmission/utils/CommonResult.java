package com.example.papersubmission.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T>{
    private Integer code;
    private String message;
    private T data;

    public static CommonResult success(){
        CommonResult result=new CommonResult();
        result.setCode(200);
        result.setMessage("success");
        return result;
    }

    public static <T> CommonResult<T> success(T data){
        CommonResult<T> result=new CommonResult<T>();
        result.setData(data);
        result.setCode(200);
        result.setMessage("success");
        return result;
    }

    public static CommonResult error(Integer code,String msg){
        CommonResult result=new CommonResult();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }
}