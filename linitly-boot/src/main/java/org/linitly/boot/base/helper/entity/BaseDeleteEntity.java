package org.linitly.boot.base.helper.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: linxiunan
 * @date: 2020/9/27 17:52
 * @descrption:
 */
@Data
@Accessors(chain = true)
public class BaseDeleteEntity {

    @ApiModelProperty(value = "删除时间")
    private Date deletedTime;

    @ApiModelProperty(value = "删除用户id")
    private Long deletedUserId;

    @ApiModelProperty(value = "系统码")
    private Integer systemCode;
}
