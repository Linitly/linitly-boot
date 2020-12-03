package org.linitly.boot.base.helper.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: linxiunan
 * @date: 2020/12/2 9:58
 * @descrption:
 */
@Data
@Accessors(chain = true)
public class BaseLog {

    private Long id;

    private String ip;

    private String operation;

    private Long entityId;

    private String log;

    private String changeJson;

    private String operatorId;

    private Integer systemCode;
}
