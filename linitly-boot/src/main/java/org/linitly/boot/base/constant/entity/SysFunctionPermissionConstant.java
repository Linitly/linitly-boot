package org.linitly.boot.base.constant.entity;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
public interface SysFunctionPermissionConstant {

    String NAME_EMPTY_ERROR = "权限名不能为空";

    int MAX_NAME_SIZE = 16;

    String NAME_SIZE_ERROR = "权限名长度不符合限制";

    String CODE_EMPTY_ERROR = "权限代码不能为空";

    String CODE_REG = "[A-Za-z0-9:]*";

    String CODE_REG_ERROR = "权限代码格式错误";

    int MAX_CODE_SIZE = 100;

    String CODE_SIZE_ERROR = "权限代码长度不符合限制";

    int MAX_DESCRIPTION_SIZE = 255;

    String DESCRIPTION_SIZE_ERROR = "权限描述长度不符合限制";

    String SYS_MENU_ID_EMPTY_ERROR = "所属菜单id不能为空";

    String SYS_MENU_ID_RANGE_ERROR = "所属菜单id大小不符合限制";
}