package com.alex.weblog.common.utils;

import com.alex.weblog.common.exception.BaseExceptionInterface;
import com.alex.weblog.common.exception.BizException;
import lombok.Data;

import java.io.Serializable;

@Data
public class Response<T> implements Serializable {

    private boolean isSuccess = true;
    private String message;
    private String errorCode;
    private T data;

    // ============================成功响应==========================
    public static<T> Response<T> success(){
        Response<T> response = new Response<>();
        return response;
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }
    // ==========================失败响应=============================
    public static <T> Response<T> fail() {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        return response;
    }


    public static <T> Response<T> fail (String errorMessage) {
        Response<T> response = new Response<>();
        response.setMessage(errorMessage);
        response.setSuccess(false);
        return response;
    }

    public static <T> Response<T> fail(String errorCode,String errorMessage) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(errorMessage);
        response.setErrorCode(errorCode);
        return response;
    }

    public static <T> Response<T> fail(BizException bizException){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(bizException.getErrorMessage());
        response.setErrorCode(bizException.getErrorCode());
        return response;
    }

    public static <T> Response<T> fail(BaseExceptionInterface exception){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(exception.getErrorMessage());
        response.setErrorCode(exception.getErrorCode());
        return response;
    }

}
