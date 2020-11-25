package org.linitly.boot.base.dto.sys_admin_user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: linxiunan
 * @date: 2020/11/25 13:31
 * @descrption:
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统用户查询DTO")
public class SysAdminUserSearchDTO {

    @ApiModelProperty(value = "登录用户名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String mobileNumber;

    @ApiModelProperty(value = "工号")
    private String jobNumber;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "性别(1:男;2:女;)")
    private Integer sex;

    @ApiModelProperty(value = "用户所在部门的id")
    private Long sysDeptId;

    @ApiModelProperty(value = "创建起始时间")
    private Date startTime;

    @ApiModelProperty(value = "创建结束时间")
    private Date endTime;

    @ApiModelProperty(value = "启用禁用状态")
    private Integer enabled;
}
