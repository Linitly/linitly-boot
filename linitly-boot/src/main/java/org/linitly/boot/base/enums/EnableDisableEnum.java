package org.linitly.boot.base.enums;

/**
 * @author: linxiunan
 * @date: 2020/5/27 9:45
 * @descrption: 启用禁用状态枚举
 */
public enum EnableDisableEnum {

    ENABLED(1), // 启用状态
    DISABLED(0), // 禁用状态
    ;

    private Integer status;

    EnableDisableEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }
}
