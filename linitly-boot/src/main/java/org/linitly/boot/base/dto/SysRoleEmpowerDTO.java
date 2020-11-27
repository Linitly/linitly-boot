package org.linitly.boot.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.linitly.boot.base.constant.entity.SysRoleConstant;
import org.linitly.boot.base.helper.entity.BaseDTO;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/11/27 11:22
 * @descrption:
 */
@Data
@ApiModel(value = "角色赋权DTO")
public class SysRoleEmpowerDTO extends BaseDTO {

    @ApiModelProperty(value = "菜单id集合")
    @NotEmpty(message = SysRoleConstant.MENU_IDS_EMPTY_ERROR, groups = {UpdateValidGroup.class})
    List<Long> sysMenuIds;

    @ApiModelProperty(value = "功能权限id集合")
    @NotEmpty(message = SysRoleConstant.FUNCTION_PERMISSION_IDS_EMPTY_ERROR, groups = {UpdateValidGroup.class})
    List<Long> functionPermissionIds;
}
