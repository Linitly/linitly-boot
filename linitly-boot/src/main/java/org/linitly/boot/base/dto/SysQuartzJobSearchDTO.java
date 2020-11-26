/**
 * @author: linxiunan
 * @date: 2020/5/27 11:12
 * @descrption:
 */
package org.linitly.boot.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: linxiunan
 * @date: 2020/5/27 11:12
 * @descrption:
 */
@Data
@ApiModel(value = "定时任务查询条件dto")
public class SysQuartzJobSearchDTO {

    @ApiModelProperty(value = "任务名称", required = true)
    private String jobName;

    @ApiModelProperty(value = "执行类", required = true)
    private String jobClassName;

    @ApiModelProperty(value = "任务状态")
    private Integer status;
}
