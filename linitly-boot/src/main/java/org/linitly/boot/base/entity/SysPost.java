package org.linitly.boot.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.helper.entity.BaseEntity;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统岗位")
public class SysPost extends BaseEntity {

    @ApiModelProperty(value = "岗位名称")
    private String name;

    @ApiModelProperty(value = "系统部门id")
    private Long sysDeptId;
}