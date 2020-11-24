package org.linitly.boot.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.helper.entity.BaseEntity;

/**
 * @author: linitly-generator
 * @date: 2020-11-24 10:41
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统部门")
public class SysDept extends BaseEntity {

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "上级部门id")
    private Long parentId;

    @ApiModelProperty(value = "当前层级的排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;
}