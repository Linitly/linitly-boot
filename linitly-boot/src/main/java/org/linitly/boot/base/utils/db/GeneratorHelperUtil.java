package org.linitly.boot.base.utils.db;

import io.swagger.annotations.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.helper.entity.BaseEntity;
import org.linitly.boot.base.helper.entity.BaseLog;
import org.linitly.boot.base.helper.entity.LogHelper;

/**
 * @author: linxiunan
 * @date: 2020/12/2 11:41
 * @descrption:
 */
public class GeneratorHelperUtil {

    public static BaseLog generatorInsertLog(BaseEntity baseEntity, LogHelper helper, BaseLog baseLog) {
        baseLog.setChangeJson(LogUtil.generatorLogJson(baseEntity));
        try {
            baseLog.setLog(LogUtil.getEntityLog(baseEntity, helper));
        } catch (Exception e) {
            baseLog.setLog("");
            e.printStackTrace();
        }
        return baseLog;
    }

    public static BaseLog generatorUpdateLog(BaseEntity baseEntity, LogHelper helper, BaseLog baseLog) {
        baseLog.setEntityId(baseEntity.getId());
        return baseLog;
    }

    public static BaseLog generatorDeleteLog(BaseEntity baseEntity, LogHelper helper, BaseLog baseLog) {
        baseLog.setEntityId(baseEntity.getId());
        baseLog.setOperation(getOperation(helper));
        baseLog.setLog(getLogBegin(baseEntity, helper));
        return baseLog;
    }

    public static BaseLog generatorDeleteLog(Long entityId, LogHelper helper, BaseLog baseLog) {
        baseLog.setEntityId(entityId);
        baseLog.setOperation(getOperation(helper));
        baseLog.setLog(getOperation(helper));
        baseLog.setChangeJson("");
        return baseLog;
    }

    public static String getOperation(LogHelper helper) {
        if (helper == null || helper.getCommandTypeEnum() == null) return null;
        return helper.getCommandTypeEnum().getOperator();
    }

    public static String getLogBegin(Object object, LogHelper helper) {
        ApiModel apiModel = object.getClass().getAnnotation(ApiModel.class);
        if (apiModel == null) return null;
        String entityProperty = apiModel.value();
        String operation = getOperation(helper);
        if (StringUtils.isBlank(operation)) return null;
        return operation + entityProperty;
    }
}
