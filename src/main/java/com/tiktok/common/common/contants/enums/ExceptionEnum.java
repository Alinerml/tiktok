package com.tiktok.common.common.contants.enums;

/**
 * 常见异常类型
 */

public enum ExceptionEnum {

    /**
     * token鉴权失败
     */
    TOKEN_FAIL ("token鉴权失败"),

    /**
     * token鉴权成功
     */
    TOKEN_SUCCESS ("token鉴权成功");

    private String value;

    ExceptionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
