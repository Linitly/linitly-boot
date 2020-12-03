package org.linitly.boot.base.utils.db;

import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.helper.entity.TableColumn;

import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/12/1 13:51
 * @descrption:
 */
public class GeneratorTableUtil {

    private static final String ID_COLUMN = "id";
    private static final String ENABLED_COLUMN = "enabled";
    private static final String CREATED_USER_ID_COLUMN = "created_user_id";
    private static final String CREATED_TIME_COLUMN = "created_time";
    private static final String LAST_MODIFIED_USER_ID_COLUMN = "last_modified_user_id";
    private static final String LAST_MODIFIED_TIME_COLUMN = "last_modified_time";

    private static final String INDENT = "    ";
    private static final String NOT_NULL = "NOT NULL";
    private static final String DEFAULT = "DEFAULT";
    private static final String COMMENT = "COMMENT";

    private static final String PRIMARY_KEY_DDL = INDENT + "PRIMARY KEY (id)\n";
    private static final String DEFAULT_TABLE_END_DDL = "ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;";

    private static final String DELETE_TABLE_COMMON_COLUMN_DDL =
            INDENT + "`delete_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '删除时间',\n" +
            INDENT + "`deleted_user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '删除用户id',\n" +
            INDENT + "`system_code` int(11) NOT NULL DEFAULT 0 COMMENT '系统码',\n";
    private static final String LOG_TABLE_COLUMN_DDL =
            INDENT + "`id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
            INDENT + "`ip` varchar(63) NOT NULL DEFAULT '' COMMENT '用户ip',\n" +
            INDENT + "`operation` varchar(15) NOT NULL DEFAULT '' COMMENT '操作',\n" +
            INDENT + "`entity_id` bigint DEFAULT 0 COMMENT '操作实体id',\n" +
            INDENT + "`log` TEXT NOT NULL COMMENT '日志信息',\n" +
            INDENT + "`change_json` TEXT NOT NULL COMMENT '此次变化的json字符串',\n" +
            INDENT + "`operator_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '操作人id',\n" +
            INDENT + "`system_code` int(11) NOT NULL DEFAULT 0 COMMENT '系统码',\n" +
            INDENT + "`created_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',\n" +
            PRIMARY_KEY_DDL + ") " + DEFAULT_TABLE_END_DDL;

    private static boolean needGeneratorDeleteTable(List<String> tableNames, String tableName) {
        return !tableNames.contains(getDeleteTableName(tableName));
    }

    private static boolean isLinitlyBusinessTable(List<TableColumn> tableColumns, String tableName) {
        if (tableName.endsWith(GlobalConstant.LOG_TABLE_SUFFIX) || tableName.endsWith(GlobalConstant.DELETE_TABLE_SUFFIX)) return false;
        if (tableColumns.size() < 7) return false;
        boolean hasIdColumn = false;
        boolean hasEnabledColumn = false;
        boolean hasCreatedUserIdColumn = false;
        boolean hasCreatedTimeColumn = false;
        boolean hasLastModifiedUserIdColumn = false;
        boolean hasLastModifiedTimeColumn = false;
        for (TableColumn tableColumn : tableColumns) {
            if (tableColumn.getColumnName().equals(ID_COLUMN)) hasIdColumn = true;
            if (tableColumn.getColumnName().equals(ENABLED_COLUMN)) hasEnabledColumn = true;
            if (tableColumn.getColumnName().equals(CREATED_USER_ID_COLUMN)) hasCreatedUserIdColumn = true;
            if (tableColumn.getColumnName().equals(CREATED_TIME_COLUMN)) hasCreatedTimeColumn = true;
            if (tableColumn.getColumnName().equals(LAST_MODIFIED_USER_ID_COLUMN)) hasLastModifiedUserIdColumn = true;
            if (tableColumn.getColumnName().equals(LAST_MODIFIED_TIME_COLUMN)) hasLastModifiedTimeColumn = true;
        }
        return hasIdColumn || hasEnabledColumn || hasCreatedUserIdColumn || hasCreatedTimeColumn || hasLastModifiedUserIdColumn || hasLastModifiedTimeColumn;
    }

    private static boolean needGeneratorLogTable(List<String> tableNames, String tableName) {
        return !tableNames.contains(getLogTableName(tableName));
    }

    public static String[] getDeleteAndLogTableDDL(List<String> tableNames, String tableName, List<TableColumn> tableColumns) {
        String[] ddls = new String[2];
        if (!isLinitlyBusinessTable(tableColumns, tableName)) return ddls;
        if (needGeneratorDeleteTable(tableNames, tableName))
            ddls[0] = getDeleteTableDDL(tableName, tableColumns);
        if (needGeneratorLogTable(tableNames, tableName))
            ddls[1] = getLogTableDDL(tableName);
        return ddls;
    }

    private static String getLogTableDDL(String tableName) {
        StringBuilder ddl = new StringBuilder();
        ddl.append("CREATE TABLE `");
        ddl.append(getLogTableName(tableName));
        ddl.append("` (\n");
        ddl.append(LOG_TABLE_COLUMN_DDL);
        return ddl.toString();
    }

    private static String getDeleteTableDDL(String tableName, List<TableColumn> tableColumns) {
        StringBuilder ddl = new StringBuilder();
        ddl.append("CREATE TABLE `");
        ddl.append(getDeleteTableName(tableName));
        ddl.append("` (\n");
        tableColumns.forEach( e -> getColumnDDL(e, ddl));
        ddl.append(DELETE_TABLE_COMMON_COLUMN_DDL);
        ddl.append(PRIMARY_KEY_DDL);
        ddl.append(") ");
        ddl.append(DEFAULT_TABLE_END_DDL);
        return ddl.toString();
    }

    private static void getColumnDDL(TableColumn tableColumn, StringBuilder ddl) {
        ddl.append(INDENT);
        ddl.append("`");
        ddl.append(tableColumn.getColumnName());
        ddl.append("` ");
        ddl.append(tableColumn.getColumnType());
        ddl.append(" ");
        if ("NO".equals(tableColumn.getIsNullable())) {
            ddl.append(NOT_NULL);
            ddl.append(" ");
        }
        if (!tableColumn.getColumnName().equals(ID_COLUMN)) {
            if (tableColumn.getColumnDefault() == null) {
                if (!"NO".equals(tableColumn.getIsNullable())) {
                    ddl.append(DEFAULT);
                    ddl.append(" NULL");
                }
            } else if (tableColumn.getColumnDefault().equals("")) {
                ddl.append(DEFAULT);
                ddl.append(" ''");
            } else {
                ddl.append(DEFAULT);
                ddl.append(" ");
                ddl.append(tableColumn.getColumnDefault());
            }
            ddl.append(" ");
        }
        ddl.append(tableColumn.getExtra());
        ddl.append(" ");
        ddl.append(COMMENT);
        ddl.append(" '");
        ddl.append(tableColumn.getColumnComment());
        ddl.append("',\n");
    }

    private static String getDeleteTableName(String tableName) {
        return tableName + GlobalConstant.DELETE_TABLE_SUFFIX;
    }

    private static String getLogTableName(String tableName) {
        return tableName + GlobalConstant.LOG_TABLE_SUFFIX;
    }
}
