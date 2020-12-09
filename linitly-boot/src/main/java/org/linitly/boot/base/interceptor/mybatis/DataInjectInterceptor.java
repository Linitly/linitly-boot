package org.linitly.boot.base.interceptor.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.linitly.boot.base.constant.global.MyBatisConstant;
import org.linitly.boot.base.helper.entity.BaseEntity;
import org.linitly.boot.base.utils.LinitlyUtil;
import org.linitly.boot.base.utils.db.ClassUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;

/**
 * @author: linxiunan
 * @date: 2020/11/22 21:19
 * @descrption:
 */
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class DataInjectInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Boolean pass = MyBatisConstant.MYBATIS_INTERCEPT_PASS.get();
        if (pass != null && pass) return invocation.proceed();
        try {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            Object parameter = invocation.getArgs()[1];

            if (SqlCommandType.INSERT == sqlCommandType) {
                if (parameter instanceof BaseEntity) {
                    dealInsertSql(parameter);
                } else if (parameter instanceof List) {
                    List parameterList = (List) parameter;
                    for (Object o : parameterList) {
                        if (o instanceof BaseEntity) dealInsertSql(o);
                    }
                }
            }
            if (SqlCommandType.UPDATE == sqlCommandType) {
                if (parameter instanceof BaseEntity) {
                    dealUpdateSql(parameter);
                } else if (parameter instanceof List) {
                    List parameterList = (List) parameter;
                    for (Object o : parameterList) {
                        if (o instanceof BaseEntity) dealUpdateSql(parameter);
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

    private void dealInsertSql(Object parameter) throws Exception {
        Field[] fields = ClassUtil.getAllFields(parameter, true);
        for (Field field : fields) {
            if (MyBatisConstant.CREATED_USER_ID_FIELD.equals(field.getName()) || MyBatisConstant.LAST_MODIFIED_USER_ID_FIELD.equals(field.getName())) {
                dealField(field, parameter);
            }
        }
    }

    private void dealUpdateSql(Object parameter) throws Exception {
        Field[] fields = ClassUtil.getAllFields(parameter, true);
        for (Field field : fields) {
            if (MyBatisConstant.LAST_MODIFIED_USER_ID_FIELD.equals(field.getName())) {
                dealField(field, parameter);
            }
        }
    }

    private void dealField(Field field, Object parameter) throws Exception {
        field.setAccessible(true);
        Object localCreateUserId = field.get(parameter);
        if (localCreateUserId == null) {
            setId(LinitlyUtil.getCurrentUserId().toString(), parameter, field);
        }
        field.setAccessible(false);
    }

    private void setId(String idString, Object parameter, Field field) throws Exception {
        Class fieldType = ClassUtil.getFieldType(field);
        if (fieldType == Long.class) {
            field.set(parameter, Long.valueOf(idString));
        } else if (fieldType == Integer.class) {
            field.set(parameter, Integer.valueOf(idString));
        } else if (fieldType == String.class) {
            field.set(parameter, idString);
        }
    }
}
