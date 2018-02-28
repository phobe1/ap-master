package com.you.ap.domain.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;
import static com.alibaba.fastjson.serializer.SerializerFeature.WriteNonStringValueAsString;

public class ApiResponse<T> implements Serializable {
   private T data;
   private String message;
   private boolean success;

    public static <T> ApiResponse<T> buildByResult(int result) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setSuccess(result>0?true:false);
        return response;
    }

    public static <T> ApiResponse<T> buildSuccess(T date) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setData(date);
        response.setSuccess(true);
        return response;
    }

    public static <T> ApiResponse<T> buildSuccess(String message) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }

    public static <T> ApiResponse<T> buildSuccess(T data,String message) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setSuccess(true);
        response.setData(data);
        response.setMessage(message);
        return response;
    }

    public static <T> ApiResponse<T> buildSuccess() {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setSuccess(true);
        return response;
    }

    public static <T> ApiResponse<T> buildFailure() {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setSuccess(false);
        return response;
    }

    public static <T> ApiResponse<T> buildFailure(T date,String errorMsg) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setData(date);
        response.setSuccess(false);
        response.setMessage(errorMsg);
        return response;
    }

    public static <T> ApiResponse<T> buildFailure(String errorMsg) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setSuccess(false);
        response.setMessage(errorMsg);
        return response;
    }

    public static <T> ApiResponse<T> buildFailure(int errorCode, String errorMsg) {
        ApiResponse<T> response = new ApiResponse<T>();
        response.setSuccess(false);
        response.setMessage(errorMsg);
        return response;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static void main(String[]args){
       String str=  JSON.toJSONString(new ApiResponse<>(),SerializerFeature.PrettyFormat,
               SerializerFeature.WriteDateUseDateFormat,
               SerializerFeature.WriteNullListAsEmpty,
               SerializerFeature.WriteNullStringAsEmpty,
               SerializerFeature.WriteNullNumberAsZero,
               SerializerFeature.WriteNullBooleanAsFalse,
               SerializerFeature.WriteMapNullValue,
               WriteNonStringValueAsString);
       System.out.println(str);
    }
}
