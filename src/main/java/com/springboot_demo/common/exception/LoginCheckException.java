package com.springboot_demo.common.exception;

/**
 * @ClassName LoginCheckException
 * @Description: TODO  登录异常
 * @Author Administrator
 * @Date 2020/6/11
 * @Version V1.0
 **/
public class LoginCheckException extends RuntimeException{
    private static final long serialVersionUID = 7524075320515958506L;

    public LoginCheckException(String message) {
        super(message);
    }
}
