package org.linitly.boot.base.enums;

/**
 * @author: linxiunan
 * @date: 2020/11/22 20:24
 * @descrption:
 */
public enum SystemEnum {

    ADMIN(101),
    ;

    private Integer systemCode;

    SystemEnum(Integer systemCode) {
        this.systemCode = systemCode;
    }

    public Integer getSystemCode() {
        return systemCode;
    }
}
