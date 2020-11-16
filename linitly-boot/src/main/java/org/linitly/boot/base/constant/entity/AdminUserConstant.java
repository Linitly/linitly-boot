package org.linitly.boot.base.constant.entity;

/**
 * @author: linxiunan
 * @date: 2020/4/29 9:05
 * @descrption:
 */
public interface AdminUserConstant {

    /**
     * 后台用户token存入redis时对于用户id加密使用盐
     */
    String TOKEN_ID_SALT = "UWOQMFSdsaffa……*……&@@($)!)(@#KJSLKASJRW(E*QW)(RUOIWJFKSAHFOIYR*O&YWQIPOHRFIOAESYI";

    String USERNAME_NOT_EXISTS = "用户名不存在";

    String PASSWORD_ERROR = "密码不正确";

    String USERNAME_EMPTY_ERROR = "用户名不能为空";

    String PASSWORD_EMPTY_ERROR = "密码不能为空";
}
