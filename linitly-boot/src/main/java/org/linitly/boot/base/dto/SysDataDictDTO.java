package org.linitly.boot.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.linitly.boot.base.constant.entity.SysDataDictConstant;
import org.linitly.boot.base.helper.entity.BaseDTO;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;

/**
 * @author: linitly-generator
 * @date: 2020-12-04 11:48
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "数据字典DTO")
public class SysDataDictDTO extends BaseDTO {

    @ApiModelProperty(value = "字典名称", required = true)
    @NotBlank(message = SysDataDictConstant.NAME_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysDataDictConstant.MAX_NAME_SIZE, message = SysDataDictConstant.NAME_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String name;

    @ApiModelProperty(value = "字典编码", required = true)
    @NotBlank(message = SysDataDictConstant.CODE_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysDataDictConstant.MAX_CODE_SIZE, message = SysDataDictConstant.CODE_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String code;

    @ApiModelProperty(value = "字典描述")
    @Size(max = SysDataDictConstant.MAX_DESCRIPTION_SIZE, message = SysDataDictConstant.DESCRIPTION_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String description;
}