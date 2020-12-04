package org.linitly.boot.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.linitly.boot.base.constant.entity.SysDataDictItemConstant;
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
@ApiModel(value = "数据字典详情DTO")
public class SysDataDictItemDTO extends BaseDTO {

    @ApiModelProperty(value = "字典值", required = true)
    @NotBlank(message = SysDataDictItemConstant.VALUE_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysDataDictItemConstant.MAX_VALUE_SIZE, message = SysDataDictItemConstant.VALUE_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String value;

    @ApiModelProperty(value = "字典文本", required = true)
    @NotBlank(message = SysDataDictItemConstant.TEXT_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysDataDictItemConstant.MAX_TEXT_SIZE, message = SysDataDictItemConstant.TEXT_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String text;

    @ApiModelProperty(value = "排序")
//    @NotNull(message = SysDataDictItemConstant.SORT_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Range(message = SysDataDictItemConstant.SORT_RANGE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private Integer sort;

    @ApiModelProperty(value = "字典id", required = true)
    @NotNull(message = SysDataDictItemConstant.SYS_DATA_DICT_ID_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Range(message = SysDataDictItemConstant.SYS_DATA_DICT_ID_RANGE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private Long sysDataDictId;
}