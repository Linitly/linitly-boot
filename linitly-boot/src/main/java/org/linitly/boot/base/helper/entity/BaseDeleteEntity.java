package org.linitly.boot.base.helper.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: linxiunan
 * @date: 2020/9/27 17:52
 * @descrption:
 */
@Data
public class BaseDeleteEntity extends BaseEntity {

    @ApiModelProperty(value = "删除时间")
    private Date deletedTime;

    @ApiModelProperty(value = "删除用户id")
    private Long deletedUserId;
}
