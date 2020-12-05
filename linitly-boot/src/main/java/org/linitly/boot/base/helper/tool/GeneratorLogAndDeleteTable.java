package org.linitly.boot.base.helper.tool;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.config.GeneratorConfig;
import org.linitly.boot.base.constant.global.MyBatisConstant;
import org.linitly.boot.base.dao.BaseBeanMapper;
import org.linitly.boot.base.helper.entity.TableColumn;
import org.linitly.boot.base.utils.db.GeneratorTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/12/1 13:29
 * @descrption:
 */
@Component
@Order(value = Ordered.LOWEST_PRECEDENCE - 1)
public class GeneratorLogAndDeleteTable implements CommandLineRunner {

    @Autowired
    private BaseBeanMapper baseBeanMapper;
    @Autowired
    private GeneratorConfig generatorInfo;

    @Override
    public void run(String... args) throws Exception {
        if (!generatorInfo.isEnabled()) return;
        List<String> tableNames = baseBeanMapper.findTableNames();
        List<TableColumn> tableColumns;

        List<String> deleteTableDDLS = new ArrayList<>();
        List<String> logTableDDLS = new ArrayList<>();
        if (CollectionUtils.isEmpty(tableNames)) return;
        for (String tableName : tableNames) {
            tableColumns = baseBeanMapper.findColumnsByTableName(tableName);
            if (CollectionUtils.isEmpty(tableColumns)) continue;
            String[] ddls = GeneratorTableUtil.getDeleteAndLogTableDDL(tableNames, tableName, tableColumns);
            if (StringUtils.isNotBlank(ddls[0])) deleteTableDDLS.add(ddls[0]);
            if (StringUtils.isNotBlank(ddls[1])) logTableDDLS.add(ddls[1]);
        }
        MyBatisConstant.MYBATIS_INTERCEPT_PASS.set(true);
        if (generatorInfo.generatorDeleteTable()) baseBeanMapper.createTables(deleteTableDDLS);
        if (generatorInfo.generatorLogTable()) baseBeanMapper.createTables(logTableDDLS);
        MyBatisConstant.MYBATIS_INTERCEPT_PASS.remove();
    }
}
