package org.linitly.boot.base.constant.entity;

/**
 * @author: linitly-generator
 * @date: 2020-11-24 10:24
 * @description:
 */
public interface SysDeptConstant {

    String NAME_EMPTY_ERROR = "部门名称不能为空";

    int MAX_NAME_SIZE = 20;

    String NAME_SIZE_ERROR = "部门名称长度不符合限制";

    String PARENT_ID_EMPTY_ERROR = "上级部门id不能为空";

    String PARENT_ID_RANGE_ERROR = "上级部门id大小不符合限制";

    String SORT_EMPTY_ERROR = "当前层级的排序不能为空";

    String SORT_RANGE_ERROR = "当前层级的排序大小不符合限制";

    int MAX_REMARK_SIZE = 255;

    String REMARK_SIZE_ERROR = "备注长度不符合限制";
}