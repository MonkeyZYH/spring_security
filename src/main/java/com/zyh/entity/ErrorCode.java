package com.zyh.entity;

/**
 *@author zyh
 */
public enum ErrorCode {
    PARAMS_ERROR("参数有误",1000),
    USERPWD_NOT_EXIST("用户名或密码不存在", 1001),
    TOKEN_ERROR("token不合法", 1002),
    NO_PERMISSION("无访问权限", 1003),
    USER_EXIST("账号已存在", 1004),
    ;

    private String msg;
    private Integer code;

    ErrorCode(String msg, int code) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
