package org.linitly.boot.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.helper.entity.BaseEntity;

/**
 * @author: linitly-generator
 * @date: 2020-12-04 11:48
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "数据字典详情")
public class SysDataDictItem extends BaseEntity {

    @ApiModelProperty(value = "字典值")
    private String value;

    @ApiModelProperty(value = "字典文本")
    private String text;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "字典id")
    private Long sysDataDictId;
}