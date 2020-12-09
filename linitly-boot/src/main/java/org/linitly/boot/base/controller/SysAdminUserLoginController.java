package org.linitly.boot.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.dto.SysAdminUserLoginDTO;
import org.linitly.boot.base.service.SysAdminUserLoginService;
import org.linitly.boot.base.vo.SysAdminUserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/login")
    @ApiModelProperty(value = "后台用户登陆")
    public SysAdminUserLoginVO login(@RequestBody @Valid SysAdminUserLoginDTO dto, BindingResult bindingResult) {
        return loginService.login(dto);
    }

    @PostMapping("/logout/{userId}")
    @ApiModelProperty(value = "后台用户退出登陆")
    public void logout(@PathVariable Long userId) {
        loginService.logout(userId);
    }
}
