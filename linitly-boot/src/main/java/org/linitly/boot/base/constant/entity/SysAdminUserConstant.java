package org.linitly.boot.base.constant.entity;

/**
 * @author: linitly-generator
 * @date: 2020-11-24 21:26
 * @description: 
 */
public interface SysAdminUserConstant {

    String MOBILE_NUMBER_EMPTY_ERROR = "手机号不能为空";

    int MAX_MOBILE_NUMBER_SIZE = 13;

    String MOBILE_NUMBER_SIZE_ERROR = "手机号长度不符合限制";

    String PASSWORD_EMPTY_ERROR = "加密密码不能为空";

    int MAX_PASSWORD_SIZE = 32;

    String PASSWORD_SIZE_ERROR = "加密密码长度不符合限制";

    String NICK_NAME_EMPTY_ERROR = "昵称不能为空";

    int MAX_NICK_NAME_SIZE = 32;

    String NICK_NAME_SIZE_ERROR = "昵称长度不符合限制";

    int MAX_REAL_NAME_SIZE = 16;

    String REAL_NAME_SIZE_ERROR = "真实姓名长度不符合限制";

    int MAX_EMAIL_SIZE = 255;

    String EMAIL_SIZE_ERROR = "邮箱长度不符合限制";

    String SEX_EMPTY_ERROR = "性别不能为空";

    String SEX_RANGE_ERROR = "性别大小不符合限制";

    int MAX_HEAD_IMG_URL_SIZE = 255;

    String HEAD_IMG_URL_SIZE_ERROR = "用户头像url地址长度不符合限制";

    String SYS_DEPT_ID_EMPTY_ERROR = "用户所在部门的id不能为空";

    String SYS_DEPT_ID_RANGE_ERROR = "用户所在部门的id大小不符合限制";
}