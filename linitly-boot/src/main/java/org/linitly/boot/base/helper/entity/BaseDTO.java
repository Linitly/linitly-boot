package org.linitly.boot.base.helper.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.helper.groups.DeleteValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: linxiunan
 * @date: 2020/5/27 10:48
 * @descrption: 基础DTO，查询对象
 */
@Data
@Accessors(chain = true)
public class BaseDTO implements Serializable {

    @ApiModelProperty(value = "id")
    @NotNull(message = GlobalConstant.ID_NOTNULL_TIP, groups = {UpdateValidGroup.class, DeleteValidGroup.class})
    @Range(min = GlobalConstant.ID_MIN, message = GlobalConstant.ID_ERROR_TIP)
    private Long id;
}
