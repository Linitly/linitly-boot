package org.linitly.boot.base.interceptor.mybatis;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.linitly.boot.base.utils.jwt.AbstractJwtUtil;
import org.linitly.boot.base.utils.jwt.JwtUtilFactory;
import org.linitly.boot.base.utils.log.ClassUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author: linxiunan
 * @date: 2020/11/22 21:19
 * @descrption:
 */
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class IdInjectInterceptor implements Interceptor {

    @Autowired
    private HttpServletRequest request;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        if (parameter == null) {
            return invocation.proceed();
        }

        AbstractJwtUtil jwtUtil = JwtUtilFactory.getJwtUtil(request);

        if (SqlCommandType.INSERT == sqlCommandType) {
            dealInsertSql(parameter, jwtUtil);
        }
        if (SqlCommandType.UPDATE == sqlCommandType) {
            Field[] fields = null;
            if (parameter instanceof MapperMethod.ParamMap) {
                MapperMethod.ParamMap<?> p = (MapperMethod.ParamMap<?>) parameter;
                if (p.containsKey("et")) {
                    parameter = p.get("et");
                } else {
                    parameter = p.get("param1");
                }
                if (parameter == null) {
                    return invocation.proceed();
                }
                fields = ClassUtil.getAllFields(parameter, true);
            } else {
                fields = ClassUtil.getAllFields(parameter, true);
            }
            dealUpdateSql(fields, parameter, jwtUtil);
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

    private void dealInsertSql(Object parameter, AbstractJwtUtil jwtUtil) throws Throwable {
        Field[] fields = ClassUtil.getAllFields(parameter, true);
        for (Field field : fields) {
            if ("createdUserId".equals(field.getName()) || "lastModifiedUserId".equals(field.getName())) {
                dealField(field, parameter, jwtUtil);
            }
        }
    }

    private void dealUpdateSql(Field[] fields, Object parameter, AbstractJwtUtil jwtUtil) throws Throwable {
        for (Field field : fields) {
            if ("lastModifiedUserId".equals(field.getName())) {
                dealField(field, parameter, jwtUtil);
            }
        }
    }

    private void dealField(Field field, Object parameter, AbstractJwtUtil jwtUtil) throws Throwable {
        field.setAccessible(true);
        Object localCreateUserId = field.get(parameter);
        if (localCreateUserId == null) {
            setId(jwtUtil.getUserId(request), parameter, field);
        }
        field.setAccessible(false);
    }

    private void setId(String idString, Object parameter, Field field) throws Throwable {
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
