package com.springboot_demo.common.exception;

/**
 * @ClassName BusinessException
 * @Description: TODO  业务异常
 * @Author Administrator
 * @Date 2020/6/11
 * @Version V1.0
 **/

public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 7524075320515958506L;

    public BusinessException(String message) {
        super(message);
    }
}
