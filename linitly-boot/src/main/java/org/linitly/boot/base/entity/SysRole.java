package org.linitly.boot.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.helper.entity.BaseEntity;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 14:02
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统角色")
public class SysRole extends BaseEntity {

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "角色代码")
    private String code;

    @ApiModelProperty(value = "角色描述")
    private String description;
}