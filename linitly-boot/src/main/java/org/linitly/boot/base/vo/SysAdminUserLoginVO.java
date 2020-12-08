package org.linitly.boot.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.entity.SysAdminUser;

import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/11/27 15:50
 * @descrption:
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户登陆返回VO")
public class SysAdminUserLoginVO extends SysAdminUser {

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "refresh_token")
    private String refreshToken;

    @ApiModelProperty(value = "菜单树")
    private List<SysMenuTreeVO> menuTree;
}