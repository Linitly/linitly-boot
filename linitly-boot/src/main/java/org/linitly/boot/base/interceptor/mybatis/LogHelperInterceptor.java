package org.linitly.boot.base.interceptor.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.linitly.boot.base.constant.global.MyBatisConstant;
import org.linitly.boot.base.enums.DBCommandTypeEnum;
import org.linitly.boot.base.helper.entity.BaseEntity;
import org.linitly.boot.base.helper.entity.LogHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author: linxiunan
 * @date: 2020/12/2 10:22
 * @descrption:
 */
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class LogHelperInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Boolean pass = MyBatisConstant.MYBATIS_INTERCEPT_PASS.get();
        if (pass != null && pass) return invocation.proceed();
        try {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            Object parameter = invocation.getArgs()[1];
            if (parameter == null) {
                return invocation.proceed();
            }
            if (SqlCommandType.INSERT == sqlCommandType) {
                List<BaseEntity> changeEntities = dealInsertOrUpdate(parameter);
                if (!changeEntities.isEmpty()) {
                    LogHelper helper = new LogHelper().setCommandTypeEnum(DBCommandTypeEnum.INSERT).setChangeEntities(changeEntities);
                    MyBatisConstant.LOG_HELPER.set(helper);
                }
            }
            if (SqlCommandType.UPDATE == sqlCommandType) {
                List<BaseEntity> changeEntities = dealInsertOrUpdate(parameter);
                if (!changeEntities.isEmpty()) {
                    LogHelper helper = new LogHelper().setCommandTypeEnum(DBCommandTypeEnum.UPDATE).setChangeEntities(changeEntities);
                    MyBatisConstant.LOG_HELPER.set(helper);
                }
            }
            if (SqlCommandType.DELETE == sqlCommandType) {
                if (parameter instanceof Long) {
                    Long id = (Long) parameter;
                    LogHelper helper = new LogHelper().setCommandTypeEnum(DBCommandTypeEnum.DELETE);
                    helper.getEntityIds().add(id);
                    MyBatisConstant.LOG_HELPER.set(helper);
                }
                if (parameter instanceof BaseEntity) {
                    BaseEntity baseEntity = (BaseEntity) parameter;
                    LogHelper helper = new LogHelper().setCommandTypeEnum(DBCommandTypeEnum.DELETE);
                    helper.getChangeEntities().add(baseEntity);
                    MyBatisConstant.LOG_HELPER.set(helper);
                }
                if (parameter instanceof List) {
                    LogHelper helper = new LogHelper().setCommandTypeEnum(DBCommandTypeEnum.DELETE);
                    List parameterList = (List) parameter;
                    if (parameterList.get(0) instanceof Long) {
                        helper.getEntityIds().addAll(parameterList);
                    }
                    if (parameterList.get(0) instanceof BaseEntity) {
                        helper.getChangeEntities().addAll(parameterList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private List<BaseEntity> dealInsertOrUpdate(Object parameter) {
        List<BaseEntity> changeEntities = new ArrayList<>();
        if (parameter instanceof BaseEntity) {
            changeEntities.add((BaseEntity) parameter);
        } else if (parameter instanceof List) {
            if (((List) parameter).get(0) instanceof BaseEntity) {
                changeEntities.addAll((List<BaseEntity>) parameter);
            }
        }
        return changeEntities;
    }
}
