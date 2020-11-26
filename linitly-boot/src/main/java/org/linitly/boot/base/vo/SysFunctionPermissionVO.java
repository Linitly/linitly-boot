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
@ApiModel(value = "系统功能权限VO")
public class SysFunctionPermissionVO extends BaseVO {

    @ApiModelProperty(value = "权限名")
    private String name;

    @ApiModelProperty(value = "权限代码")
    private String code;

    @ApiModelProperty(value = "权限描述")
    private String description;

    @ApiModelProperty(value = "所属菜单id")
    private Long sysMenuId;
}