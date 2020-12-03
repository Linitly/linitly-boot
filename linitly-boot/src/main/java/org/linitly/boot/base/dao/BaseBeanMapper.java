package org.linitly.boot.base.dao;

import org.apache.ibatis.annotations.Param;
import org.linitly.boot.base.helper.entity.BaseLog;
import org.linitly.boot.base.helper.entity.TableColumn;

import java.util.List;
import java.util.Map;

public interface BaseBeanMapper {

    List<String> findTableNames();

    List<TableColumn> findColumnsByTableName(String tableName);

    void createTables(@Param("ddls") List<String> ddls);

    void insertAllLog(@Param("tableName") String tableName, @Param("baseLogs") List<BaseLog> baseLogs);

    List<Map<String, Object>> findByIds(@Param("tableName") String tableName, @Param("ids") List<Long> ids);

    void insertDeleteData(@Param("tableName") String tableName, @Param("columnMap") Map<String, Object> columnMap, @Param("datas") List<Map<String, Object>> datas);
}
