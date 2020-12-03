package org.linitly.boot.base.helper.entity;

import lombok.Data;

/**
 * @author: linxiunan
 * @date: 2020/12/1 14:04
 * @descrption: 基于mysql5.7
 */
@Data
public class TableColumn {

    private String tableName;

    private String columnName;

    private String columnDefault;

    private String isNullable;

    private String columnType;

    private String extra;

    private String columnComment;
}
