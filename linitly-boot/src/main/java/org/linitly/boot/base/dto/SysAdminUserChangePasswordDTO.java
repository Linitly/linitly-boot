package org.linitly.boot.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.linitly.boot.base.constant.entity.SysAdminUserConstant;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.helper.entity.BaseDTO;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 10:26
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统用户修改密码DTO")
public class SysAdminUserChangePasswordDTO extends BaseDTO {

    @ApiModelProperty(value = "原密码")
    @NotBlank(message = SysAdminUserConstant.BEFORE_PASSWORD_EMPTY_ERROR)
    private String beforePassword;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = SysAdminUserConstant.PASSWORD_EMPTY_ERROR)
    @Pattern(regexp = SysAdminUserConstant.PASSWORD_REG, message = SysAdminUserConstant.PASSWORD_REG_ERROR)
    private String password;

    @ApiModelProperty(value = "确认密码")
    @NotBlank(message = SysAdminUserConstant.CONFIRM_PASSWORD_EMPTY_ERROR)
    private String confirmPassword;
}