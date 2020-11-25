package org.linitly.boot.base.dto.sys_admin_user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.linitly.boot.base.constant.entity.SysAdminUserConstant;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.helper.entity.BaseDTO;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;

/**
 * @author: linitly-generator
 * @date: 2020-11-25 10:26
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "系统用户DTO")
public class SysAdminUserDTO extends BaseDTO {

    @ApiModelProperty(value = "登录用户名", required = true)
    @NotBlank(message = SysAdminUserConstant.USERNAME_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysAdminUserConstant.MAX_USERNAME_SIZE, message = SysAdminUserConstant.USERNAME_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String username;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = SysAdminUserConstant.MOBILE_NUMBER_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysAdminUserConstant.MAX_MOBILE_NUMBER_SIZE, message = SysAdminUserConstant.MOBILE_NUMBER_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Pattern(regexp = GlobalConstant.MOBILE_NUMBER_REG, message = GlobalConstant.MOBILE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String mobileNumber;

    @ApiModelProperty(value = "工号", required = true)
    @NotBlank(message = SysAdminUserConstant.JOB_NUMBER_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysAdminUserConstant.MAX_JOB_NUMBER_SIZE, message = SysAdminUserConstant.JOB_NUMBER_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String jobNumber;

    @ApiModelProperty(value = "昵称", required = true)
    @NotBlank(message = SysAdminUserConstant.NICK_NAME_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = SysAdminUserConstant.MAX_NICK_NAME_SIZE, message = SysAdminUserConstant.NICK_NAME_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String nickName;

    @ApiModelProperty(value = "真实姓名")
    @Size(max = SysAdminUserConstant.MAX_REAL_NAME_SIZE, message = SysAdminUserConstant.REAL_NAME_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String realName;

    @ApiModelProperty(value = "邮箱")
    @Size(max = SysAdminUserConstant.MAX_EMAIL_SIZE, message = SysAdminUserConstant.EMAIL_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String email;

    @ApiModelProperty(value = "性别(1:男;2:女;)", required = true)
    @NotNull(message = SysAdminUserConstant.SEX_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Range(max = SysAdminUserConstant.MAX_SEX_RANGE, message = SysAdminUserConstant.SEX_RANGE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private Integer sex;

    @ApiModelProperty(value = "用户头像url地址")
    @Size(max = SysAdminUserConstant.MAX_HEAD_IMG_URL_SIZE, message = SysAdminUserConstant.HEAD_IMG_URL_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String headImgUrl;

    @ApiModelProperty(value = "用户所在部门的id", required = true)
    @NotNull(message = SysAdminUserConstant.SYS_DEPT_ID_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Range(message = SysAdminUserConstant.SYS_DEPT_ID_RANGE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private Long sysDeptId;
}