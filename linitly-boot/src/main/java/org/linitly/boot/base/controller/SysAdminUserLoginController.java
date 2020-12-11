package org.linitly.boot.base.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.dto.SysAdminUserLoginDTO;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.service.SysAdminUserLoginService;
import org.linitly.boot.base.utils.ImageUtil;
import org.linitly.boot.base.utils.RedisOperator;
import org.linitly.boot.base.vo.CaptchaVO;
import org.linitly.boot.base.vo.SysAdminUserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * @author: linxiunan
 * @date: 2020/11/27 14:23
 * @descrption:
 */
@Result
@RestController
@RequestMapping(AdminCommonConstant.URL_PREFIX)
@Api(tags = "系统后台用户登陆管理")
public class SysAdminUserLoginController {

    @Autowired
    private SysAdminUserLoginService loginService;
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @PostMapping("/login")
    @ApiModelProperty(value = "后台用户登陆")
    public SysAdminUserLoginVO login(@RequestBody @Valid SysAdminUserLoginDTO dto, BindingResult bindingResult) {
        validCaptcha(dto);
        return loginService.login(dto);
    }

    @PostMapping("/logout")
    @ApiModelProperty(value = "后台用户退出登陆")
    public void logout() {
        loginService.logout();
    }

    @PostMapping("/captcha")
    @ApiModelProperty(value = "生成验证码")
    public CaptchaVO captcha() {
        String verCode = defaultKaptcha.createText();
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        BufferedImage bufferedImage = defaultKaptcha.createImage(verCode);
        String captchaBase64 = ImageUtil.toBase64(bufferedImage);
        redisOperator.set(key, verCode, 2 * 60);
        return new CaptchaVO().setKey(key).setCaptchaImage(captchaBase64);
    }

    private void validCaptcha(SysAdminUserLoginDTO dto) {
        String cacheCode = redisOperator.get(dto.getKey());
        if (StringUtils.isBlank(cacheCode)) throw new CommonException("验证码已失效");
        if (!cacheCode.equalsIgnoreCase(dto.getVerCode())) throw new CommonException("验证码输入错误");
    }
}
