package org.linitly.boot.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.constant.entity.SysAdminUserConstant;
import org.linitly.boot.base.vo.CaptchaVO;

import javax.validation.constraints.NotBlank;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 10:26
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统用户登陆DTO")
public class SysAdminUserLoginDTO extends CaptchaVO {

    @ApiModelProperty(value = "登陆用户名")
    @NotBlank(message = SysAdminUserConstant.USERNAME_EMPTY_ERROR)
    private String username;

    @ApiModelProperty(value = "登陆密码")
    @NotBlank(message = SysAdminUserConstant.PASSWORD_EMPTY_ERROR)
    private String password;
}