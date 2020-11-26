package org.linitly.boot.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.linitly.boot.base.constant.entity.SysRoleConstant;
import org.linitly.boot.base.helper.entity.BaseDTO;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 14:02
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统角色DTO")
public class SysRoleDTO extends BaseDTO {

    @ApiModelProperty(value = "角色名", required = true)
    @NotBlank(message = SysRoleConstant.NAME_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysRoleConstant.MAX_NAME_SIZE, message = SysRoleConstant.NAME_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String name;

    @ApiModelProperty(value = "角色代码", required = true)
    @NotBlank(message = SysRoleConstant.CODE_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Pattern(regexp = SysRoleConstant.CODE_REG, message = SysRoleConstant.CODE_REG_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysRoleConstant.MAX_CODE_SIZE, message = SysRoleConstant.CODE_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String code;

    @ApiModelProperty(value = "角色描述")
    @Size(max = SysRoleConstant.MAX_DESCRIPTION_SIZE, message = SysRoleConstant.DESCRIPTION_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String description;
}