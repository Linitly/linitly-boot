package org.linitly.boot.base.utils.excel;

import com.alibaba.fastjson.JSONObject;
import org.linitly.boot.base.annotation.ExcelProperty;
import org.linitly.boot.base.utils.db.ClassUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/5/14 9:29
 * @descrption: excel通用工具类
 */
public class ExcelCommonUtil {

    /**
     * @author linxiunan
     * @date 9:34 2020/5/14
     * @description 获取excelProperty注解，importOrExport：导入还是导出 1：导入；2导出；
     */
    static List<Field> getAnnotationFields(Class<?> classes, int importOrExport) {
        Field[] fields = ClassUtil.getAllFields(classes, false);
        if (fields.length < 1) {
            throw new RuntimeException("target class param was less than 1");
        }
        List<Field> fieldList = new ArrayList<>();
        ExcelProperty excelProperty = null;
        for (int i = 0; i < fields.length; i++) {
            excelProperty = importOrExport == 1 ? getExcelPropertiesAnon(fields[i]) : getIsExportExcelPropertiesAnon(fields[i]);
            if (excelProperty != null) {
                fieldList.add(fields[i]);
            }
        }
        return fieldList;
    }

    static ExcelProperty getExcelPropertiesAnon(Field field) {
        return field.getAnnotation(ExcelProperty.class);
    }

    private static ExcelProperty getIsExportExcelPropertiesAnon(Field field) {
        ExcelProperty excelProperty = getExcelPropertiesAnon(field);
        if (excelProperty != null && excelProperty.isExport()) {
            return excelProperty;
        }
        return null;
    }

    /**
     * @author linxiunan
     * @date 9:34 2020/5/14
     * @description 获取转义后的数据，importOrExport：导入还是导出 1：导入；2导出；
     */
    static Object getEscapeValue(Object value, ExcelProperty excelProperty, int importOrExport) {
        String escapeStr = importOrExport == 1 ? excelProperty.importEscape() : excelProperty.exportEscape();
        if (StringUtils.isNotBlank(escapeStr)) {
            JSONObject jsonObject = JSONObject.parseObject(escapeStr);
            Object escapeValue = jsonObject.get(value.toString());
            value = escapeValue == null ? value : escapeValue;
        }
        return value;
    }
}
