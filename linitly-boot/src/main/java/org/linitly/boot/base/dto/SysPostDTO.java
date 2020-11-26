package org.linitly.boot.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.linitly.boot.base.constant.entity.SysPostConstant;
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
@ApiModel(value = "系统岗位DTO")
public class SysPostDTO extends BaseDTO {

    @ApiModelProperty(value = "岗位名称", required = true)
    @NotBlank(message = SysPostConstant.NAME_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysPostConstant.MAX_NAME_SIZE, message = SysPostConstant.NAME_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String name;

    @ApiModelProperty(value = "系统部门id", required = true)
    @NotNull(message = SysPostConstant.SYS_DEPT_ID_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Range(message = SysPostConstant.SYS_DEPT_ID_RANGE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private Long sysDeptId;
}