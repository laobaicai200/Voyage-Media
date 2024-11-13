package com.gec.system.exception;


import com.aliyuncs.exceptions.ClientException;
import com.gec.util.Result;
import com.gec.util.ResultCodeEnum;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@ControllerAdvice
public class GlobalException {


    /**
     * spring security异常
     * @param
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result handleAccessDeniedException(AccessDeniedException exception) {
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("没有当前操作权限");
    }


    @ExceptionHandler(ClientException.class)
    @ResponseBody
    public Result handleAccessDeniedException(ClientException exception) {
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("该影视没有链接");
    }
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public Result exceptionHandler(AuthenticationException e) {
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("没有当前操作权限");
    }
    public void handlerParam(){
        System.out.println("程序出错了，牛马，快去修复");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        handlerParam();
        e.printStackTrace();
        return Result.fail().message("这里后台出现全局错误了哦，非常抱歉，我们会让牛马立马去修复");
    }


    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public Result error(DuplicateKeyException e){
        handlerParam();
        e.printStackTrace();
        return Result.fail().message("已经有重复的用户名了");
    }

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseBody
//    public Result error(RuntimeException e){
//        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("没有当前操作权限");
//    }

    @ExceptionHandler(MyCustomerException.class)
    @ResponseBody
    public Result  error(MyCustomerException e)
    {
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }


}
