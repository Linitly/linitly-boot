package org.linitly.boot.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.linitly.boot.base.constant.entity.SysMenuConstant;
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
@ApiModel(value = "系统菜单DTO")
public class SysMenuDTO extends BaseDTO {

    @ApiModelProperty(value = "菜单名", required = true)
    @NotBlank(message = SysMenuConstant.NAME_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysMenuConstant.MAX_NAME_SIZE, message = SysMenuConstant.NAME_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String name;

    @ApiModelProperty(value = "菜单url")
    @Size(max = SysMenuConstant.MAX_URL_SIZE, message = SysMenuConstant.URL_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String url;

    @ApiModelProperty(value = "菜单描述")
    @Size(max = SysMenuConstant.MAX_DESCRIPTION_SIZE, message = SysMenuConstant.DESCRIPTION_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String description;

    @ApiModelProperty(value = "上级菜单id", required = true)
    @NotNull(message = SysMenuConstant.PARENT_ID_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Range(message = SysMenuConstant.PARENT_ID_RANGE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private Long parentId;

    @ApiModelProperty(value = "当前层级的排序", required = true)
    @NotNull(message = SysMenuConstant.SORT_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Range(message = SysMenuConstant.SORT_RANGE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private Integer sort;
}