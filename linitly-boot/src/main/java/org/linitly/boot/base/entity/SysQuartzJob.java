package org.linitly.boot.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.linitly.boot.base.helper.entity.BaseEntity;

@Data
@ApiModel(value = "定时任务实体类")
public class SysQuartzJob extends BaseEntity {

	@ApiModelProperty(value = "任务名称", required = true)
	private String jobName;

	@ApiModelProperty(value = "执行类", required = true)
	private String jobClassName;

	@ApiModelProperty(value = "执行corn表达式", required = true)
	private String cronExpression;

	@ApiModelProperty(value = "任务描述")
	private String description;

	@ApiModelProperty(value = "任务状态")
	private Integer status;
}