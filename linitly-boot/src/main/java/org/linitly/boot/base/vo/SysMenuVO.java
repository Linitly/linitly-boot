package org.linitly.boot.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.helper.entity.BaseVO;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统菜单VO")
public class SysMenuVO extends BaseVO {

    @ApiModelProperty(value = "菜单名")
    private String name;

    @ApiModelProperty(value = "菜单代码")
    private String url;

    @ApiModelProperty(value = "菜单描述")
    private String description;

    @ApiModelProperty(value = "上级部门id")
    private Long parentId;

    @ApiModelProperty(value = "当前层级的排序")
    private Integer sort;

    @ApiModelProperty(value = "子菜单数量")
    private Integer childNumber;
}