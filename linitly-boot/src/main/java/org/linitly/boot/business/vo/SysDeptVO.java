package org.linitly.boot.business.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.helper.entity.BaseVO;

/**
 * @author: linitly-generator
 * @date: 2020-11-24 10:41
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统部门VO")
public class SysDeptVO extends BaseVO {

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "上级部门id")
    private Long parentId;

    @ApiModelProperty(value = "部门层级")
    private String level;

    @ApiModelProperty(value = "当前层级的排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;
}