package org.linitly.boot.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.helper.entity.BaseVO;

/**
 * @author: linitly-generator
 * @date: 2020-12-04 11:48
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "数据字典VO")
public class SysDataDictVO extends BaseVO {

    @ApiModelProperty(value = "字典名称")
    private String name;

    @ApiModelProperty(value = "字典编码")
    private String code;

    @ApiModelProperty(value = "字典描述")
    private String description;
}