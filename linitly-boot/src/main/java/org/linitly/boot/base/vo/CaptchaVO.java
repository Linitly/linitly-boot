package org.linitly.boot.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.constant.entity.SysAdminUserConstant;

import javax.validation.constraints.NotBlank;

/**
 * @author: linxiunan
 * @date: 2020/12/10 15:05
 * @descrption:
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "验证码返回VO")
public class CaptchaVO {

    @ApiModelProperty(value = "验证码key")
    @NotBlank(message = SysAdminUserConstant.CAPTCHA_KEY_EMPTY_ERROR)
    private String key;

    @ApiModelProperty(value = "验证码图片base64")
    private String captchaImage;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = SysAdminUserConstant.CAPTCHA_CODE_EMPTY_ERROR)
    private String verCode;
}
