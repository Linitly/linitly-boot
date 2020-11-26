package org.linitly.boot.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.linitly.boot.base.constant.entity.SysDeptConstant;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.base.helper.entity.BaseDTO;

/**
 * @author: linitly-generator
 * @date: 2020-11-24 10:41
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统部门DTO")
public class SysDeptDTO extends BaseDTO {

    @ApiModelProperty(value = "部门名称", required = true)
    @NotBlank(message = SysDeptConstant.NAME_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysDeptConstant.MAX_NAME_SIZE, message = SysDeptConstant.NAME_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String name;

    @ApiModelProperty(value = "上级部门id", required = true)
    @NotNull(message = SysDeptConstant.PARENT_ID_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Range(message = SysDeptConstant.PARENT_ID_RANGE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private Long parentId;

    @ApiModelProperty(value = "当前层级的排序")
    @NotNull(message = SysDeptConstant.SORT_EMPTY_ERROR)
    @Range(message = SysDeptConstant.SORT_RANGE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private Integer sort;

    @ApiModelProperty(value = "备注")
    @Size(max = SysDeptConstant.MAX_REMARK_SIZE, message = SysDeptConstant.REMARK_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String remark;
}