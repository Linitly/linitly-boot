package org.linitly.boot.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.linitly.boot.base.constant.entity.SysFunctionPermissionConstant;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.helper.entity.BaseDTO;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;

/**
 * @author: linitly-generator
 * @date: 2020-11-26 15:22
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统功能权限DTO")
public class SysFunctionPermissionDTO extends BaseDTO {

    @ApiModelProperty(value = "权限名", required = true)
    @NotBlank(message = SysFunctionPermissionConstant.NAME_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysFunctionPermissionConstant.MAX_NAME_SIZE, message = SysFunctionPermissionConstant.NAME_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String name;

    @ApiModelProperty(value = "权限代码", required = true)
    @Pattern(regexp = SysFunctionPermissionConstant.CODE_REG, message = SysFunctionPermissionConstant.CODE_REG_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @NotBlank(message = SysFunctionPermissionConstant.CODE_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysFunctionPermissionConstant.MAX_CODE_SIZE, message = SysFunctionPermissionConstant.CODE_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String code;

    @ApiModelProperty(value = "权限描述")
    @Size(max = SysFunctionPermissionConstant.MAX_DESCRIPTION_SIZE, message = SysFunctionPermissionConstant.DESCRIPTION_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String description;

    @ApiModelProperty(value = "所属菜单id", required = true)
    @NotNull(message = SysFunctionPermissionConstant.SYS_MENU_ID_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Range(min = GlobalConstant.ID_MIN, message = SysFunctionPermissionConstant.SYS_MENU_ID_RANGE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private Long sysMenuId;
}