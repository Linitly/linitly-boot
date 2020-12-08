package org.linitly.boot.base.enums;

public enum PermissionTypeEnum {

    MENU(1),
    FUNCTION_PERMISSION(2),
    ;

    private Integer type;

    PermissionTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
