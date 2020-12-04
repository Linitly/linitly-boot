package org.linitly.boot.base.helper.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.annotation.Dict;

import java.io.Serializable;
import java.util.Date;

/**
 * @author linxiunan
 * @Description 基础实体类
 * @date 2018年10月18日
 */
@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "创建人id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long createdUserId;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "最后修改人id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long lastModifiedUserId;

    @ApiModelProperty(value = "最后一次更新时间")
    private Date lastModifiedTime;

    @Dict
    @ApiModelProperty(value = "启用禁用状态", notes = "1:启用(默认);0:禁用")
    private Integer enabled;
}
