package edu.scnu.wiki.exception;

import lombok.Data;

/**
 * @author long
 * @version 1.0
 * @ClassName BusinessExceptionCode
 * @description: TODO
 * @date 2023/10/4 16:02
 */

public enum BusinessExceptionCode {

    USER_LOGIN_NAME_EXIST("登录名已经存在"),
    USER_NAME_NOT_EXIST("用户名不存在或密码错误");

    private String desc;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
