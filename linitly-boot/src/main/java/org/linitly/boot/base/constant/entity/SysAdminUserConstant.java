package org.linitly.boot.base.constant.entity;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 10:26
 * @description: 
 */
public interface SysAdminUserConstant {

    String DEFAULT_PASSWORD = "123456";

    String USERNAME_EMPTY_ERROR = "登录用户名不能为空";

    int MAX_USERNAME_SIZE = 32;

    String USERNAME_SIZE_ERROR = "登录用户名长度不符合限制";

    String MOBILE_NUMBER_EMPTY_ERROR = "手机号不能为空";

    int MAX_MOBILE_NUMBER_SIZE = 13;

    String MOBILE_NUMBER_SIZE_ERROR = "手机号长度不符合限制";

    int MAX_SALT_SIZE = 32;

    String PASSWORD_REG = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z\\W]{8,}$";

    String PASSWORD_REG_ERROR = "密码不符合规则";

    String JOB_NUMBER_EMPTY_ERROR = "工号不能为空";

    int MAX_JOB_NUMBER_SIZE = 32;

    String JOB_NUMBER_SIZE_ERROR = "工号长度不符合限制";

    String NICK_NAME_EMPTY_ERROR = "昵称不能为空";

    int MAX_NICK_NAME_SIZE = 32;

    String NICK_NAME_SIZE_ERROR = "昵称长度不符合限制";

    int MAX_REAL_NAME_SIZE = 16;

    String REAL_NAME_SIZE_ERROR = "真实姓名长度不符合限制";

    int MAX_EMAIL_SIZE = 255;

    String EMAIL_SIZE_ERROR = "邮箱长度不符合限制";

    int MIN_SEX_RANGE = 1;

    int MAX_SEX_RANGE = 2;

    String SEX_EMPTY_ERROR = "性别不能为空";

    String SEX_RANGE_ERROR = "性别大小不符合限制";

    int MAX_HEAD_IMG_URL_SIZE = 255;

    String HEAD_IMG_URL_SIZE_ERROR = "用户头像url地址长度不符合限制";

    String ROLE_IDS_EMPTY_ERROR = "所属角色不能为空";

    String POST_IDS_EMPTY_ERROR = "所属岗位不能为空";

    ////////////////////////////////////////////////////////////////////////////////////////////////
    String PASSWORD_EMPTY_ERROR = "登陆密码不能为空";



    /**
     * 后台用户token存入redis时对于用户id加密使用盐
     */
    String TOKEN_ID_SALT = "UWOQMFSdsaffa……*……&@@($)!)(@#KJSLKASJRW(E*QW)(RUOIWJFKSAHFOIYR*O&YWQIPOHRFIOAESYI";
}