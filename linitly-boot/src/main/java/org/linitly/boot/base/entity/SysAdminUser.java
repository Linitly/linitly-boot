package org.linitly.boot.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.helper.entity.BaseEntity;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 10:26
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统用户")
public class SysAdminUser extends BaseEntity {

    @ApiModelProperty(value = "登录用户名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String mobileNumber;

    @ApiModelProperty(value = "密码盐")
    private String salt;

    @ApiModelProperty(value = "加密密码")
    private String password;

    @ApiModelProperty(value = "工号")
    private String jobNumber;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别(1:男;2:女;)")
    private Integer sex;

    @ApiModelProperty(value = "用户头像url地址")
    private String headImgUrl;
}