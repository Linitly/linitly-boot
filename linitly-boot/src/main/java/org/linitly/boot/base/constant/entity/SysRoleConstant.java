package org.linitly.boot.base.constant.entity;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 14:02
 * @description: 
 */
public interface SysRoleConstant {

    String NAME_EMPTY_ERROR = "角色名不能为空";

    int MAX_NAME_SIZE = 16;

    String NAME_SIZE_ERROR = "角色名长度不符合限制";

    String CODE_REG = "[A-Za-z0-9:]*";

    String CODE_REG_ERROR = "角色代码格式错误";

    String CODE_EMPTY_ERROR = "角色代码不能为空";

    int MAX_CODE_SIZE = 100;

    String CODE_SIZE_ERROR = "角色代码长度不符合限制";

    int MAX_DESCRIPTION_SIZE = 255;

    String DESCRIPTION_SIZE_ERROR = "角色描述长度不符合限制";

    String MENU_IDS_EMPTY_ERROR  = "菜单id集合不能为空";

    String FUNCTION_PERMISSION_IDS_EMPTY_ERROR  = "功能权限id集合不能为空";
}