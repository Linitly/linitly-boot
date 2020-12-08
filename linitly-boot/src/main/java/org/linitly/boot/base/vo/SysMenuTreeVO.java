package org.linitly.boot.base.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.linitly.boot.base.enums.PermissionTypeEnum;

import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/12/8 10:06
 * @descrption:
 */
@Data
@ApiModel(value = "菜单和功能权限返回VO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysMenuTreeVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "菜单名")
    private String name;

    @ApiModelProperty(value = "菜单url")
    private String url;

    @ApiModelProperty(value = "父菜单id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long parentId;

    @ApiModelProperty(value = "子菜单数")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer childNumber;

    @ApiModelProperty(value = "排序")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer sort;

    @ApiModelProperty(value = "是否具有此权限", notes = "0:不具有;1:具有;")
    private Integer selected = 0;

    @ApiModelProperty(value = "权限类型", notes = "1:菜单;2:功能权限;")
    private Integer type = PermissionTypeEnum.MENU.getType();

    @ApiModelProperty(value = "子菜单列表")
    private List<SysMenuTreeVO> childs;

    @ApiModelProperty(value = "功能权限列表")
    private List<SysFunctionPermissionTreeVO> functionPermissions;
}
