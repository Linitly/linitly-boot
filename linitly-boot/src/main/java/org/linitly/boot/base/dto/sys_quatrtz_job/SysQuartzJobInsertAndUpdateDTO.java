package org.linitly.boot.base.dto.sys_quatrtz_job;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.linitly.boot.base.constant.entity.SysQuartzJobConstant;
import org.linitly.boot.base.helper.entity.BaseDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author: linxiunan
 * @date: 2020/5/27 10:47
 * @descrption:
 */
@Data
@ApiModel(value = "定时任务添加/修改DTO")
public class SysQuartzJobInsertAndUpdateDTO extends BaseDTO {

    @ApiModelProperty(value = "任务名称", required = true)
    @NotBlank(message = SysQuartzJobConstant.JOB_NAME_EMPTY_ERROR)
    @Size(max = SysQuartzJobConstant.MAX_JOB_NAME_LENGTH, message = SysQuartzJobConstant.JOB_NAME_LENGTH_ERROR)
    private String jobName;

    @ApiModelProperty(value = "执行类", required = true)
    @NotBlank(message = SysQuartzJobConstant.JOB_CLASS_NAME_EMPTY_ERROR)
    @Size(max = SysQuartzJobConstant.MAX_JOB_CLASS_NAME_LENGTH, message = SysQuartzJobConstant.JOB_CLASS_NAME_LENGTH_ERROR)
    private String jobClassName;

    @ApiModelProperty(value = "执行corn表达式", required = true)
    @NotBlank(message = SysQuartzJobConstant.CORN_EXPRESSION_EMPTY_ERROR)
    @Size(max = SysQuartzJobConstant.MAX_CORN_EXPRESSION_LENGTH, message = SysQuartzJobConstant.CORN_EXPRESSION_LENGTH_ERROR)
    private String cronExpression;

    @ApiModelProperty(value = "任务描述")
    @Size(max = SysQuartzJobConstant.MAX_DESCRIPTION_LENGTH, message = SysQuartzJobConstant.DESCRIPTION_LENGTH_ERROR)
    private String description;
}
