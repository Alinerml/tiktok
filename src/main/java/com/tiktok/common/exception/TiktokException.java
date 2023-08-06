package com.tiktok.common.exception;

public class TiktokException extends RuntimeException{

    public TiktokException(String message){
        super(message);
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
