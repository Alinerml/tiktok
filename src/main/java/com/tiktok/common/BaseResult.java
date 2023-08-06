package com.tiktok.common;

import com.tiktok.common.contants.ResponseConstants;
import lombok.Data;

import java.util.HashMap;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class BaseResult extends HashMap<String,Object> {
//    @JsonProperty("status_code")
//    private Integer statusCode;
//
//    @JsonProperty("status_message")
//    private String statusMessage;


    //写为键值对存储的方式
    public BaseResult() {
        put("status_code",ResponseConstants.SC_OK);
        put("status_message","success");
    }


    public static BaseResult ok() {
        return new BaseResult();
    }

    public static BaseResult error(String msg) {
        return error(ResponseConstants.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static BaseResult error(Integer status_code,String status_message){
        BaseResult baseResult = new BaseResult();
        baseResult.put("status_code",status_code);
        baseResult.put("status_message",status_message);

        return baseResult;
    }

    public BaseResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
