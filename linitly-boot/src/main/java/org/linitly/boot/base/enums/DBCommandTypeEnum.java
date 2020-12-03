package org.linitly.boot.base.enums;

public enum DBCommandTypeEnum {

    INSERT("INSERT", "新增"),
    UPDATE("UPDATE", "修改"),
    DELETE("DELETE", "删除"),
    SELECT("SELECT", "查询"),
    ;

    private String commandType;

    private String operator;

    DBCommandTypeEnum(String commandType, String operator) {
        this.commandType = commandType;
        this.operator = operator;
    }

    public String getCommandType() {
        return commandType;
    }

    public String getOperator() {
        return operator;
    }
}
