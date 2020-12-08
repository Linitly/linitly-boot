package org.linitly.boot.base.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.linitly.boot.base.enums.PermissionTypeEnum;

/**
 * @author: linxiunan
 * @date: 2020/12/8 10:31
 * @descrption:
 */
@Data
@ApiModel
public class SysFunctionPermissionTreeVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "权限名")
    private String name;

    @ApiModelProperty(value = "权限码")
    private String code;

    @ApiModelProperty(value = "所属菜单id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long sysMenuId;

    @ApiModelProperty(value = "是否具有此权限", notes = "0:不具有;1:具有;")
    private Integer selected = 0;

    @ApiModelProperty(value = "权限类型", notes = "1:菜单;2:功能权限;")
    private Integer type = PermissionTypeEnum.FUNCTION_PERMISSION.getType();
}
