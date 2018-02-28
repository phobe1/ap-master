package com.you.ap.provider.interceptor;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class ExceptionInterceptor {

//    @ExceptionHandler(Exception.class)
//    public String handleAll(Throwable t){
//        //t.printStackTrace();
//        return t.getMessage();
//    }
}
