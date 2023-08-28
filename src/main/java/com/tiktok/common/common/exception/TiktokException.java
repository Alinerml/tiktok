package com.tiktok.common.common.exception;

import com.tiktok.common.common.contants.enums.ExceptionEnum;

public class TiktokException extends RuntimeException{

    public TiktokException(String message){
        super(message);
    }

    public TiktokException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getValue());
    }

    public TiktokException(Throwable cause)
    {
        super(cause);
    }

    public TiktokException(String message,Throwable cause)
    {
        super(message,cause);
    }
}
