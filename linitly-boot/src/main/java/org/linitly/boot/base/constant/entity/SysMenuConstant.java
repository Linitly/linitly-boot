package org.linitly.boot.base.constant.entity;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
public interface SysMenuConstant {

    String NAME_EMPTY_ERROR = "菜单名不能为空";

    int MAX_NAME_SIZE = 16;

    String NAME_SIZE_ERROR = "菜单名长度不符合限制";

    int MAX_URL_SIZE = 100;

    String URL_SIZE_ERROR = "菜单url长度不符合限制";

    int MAX_DESCRIPTION_SIZE = 255;

    String DESCRIPTION_SIZE_ERROR = "菜单描述长度不符合限制";

    String PARENT_ID_EMPTY_ERROR = "上级菜单id不能为空";

    String PARENT_ID_RANGE_ERROR = "上级菜单id大小不符合限制";

    String SORT_EMPTY_ERROR = "当前层级的排序不能为空";

    String SORT_RANGE_ERROR = "当前层级的排序大小不符合限制";
}