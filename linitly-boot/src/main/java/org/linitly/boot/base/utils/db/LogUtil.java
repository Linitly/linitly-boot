package org.linitly.boot.base.utils.db;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.annotation.LogIgnore;
import org.linitly.boot.base.enums.DateFormat;
import org.linitly.boot.base.helper.entity.BaseLog;
import org.linitly.boot.base.helper.entity.LogHelper;
import org.linitly.boot.base.utils.IDateUtil;
import org.linitly.boot.base.utils.JsonUtils;
import org.linitly.boot.base.utils.LinitlyUtil;
import org.linitly.boot.base.utils.bean.SpringBeanUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: linxiunan
 * @date: 2020/5/9 9:45
 * @descrption:
 */
public class LogUtil {

    public static String generatorLogJson(Object object) {
        Field[] fields = ClassUtil.getAllFields(object, true);
        String json = JsonUtils.objectToJson(object, false);
        JSONObject jsonObject = JSONObject.parseObject(json);
        for (Field field : fields) {
            LogIgnore logIgnoreAnno = field.getAnnotation(LogIgnore.class);
            if (logIgnoreAnno != null) {
                jsonObject.remove(field.getName());
            }
        }
        return jsonObject.toJSONString();
    }

    public static String getEntityLog(Object object, LogHelper helper)
            throws Exception {
        String logBegin = GeneratorHelperUtil.getLogBegin(object, helper);
        if (StringUtils.isBlank(logBegin)) return null;

        StringBuilder log = new StringBuilder(logBegin + ":: ");
        Field[] fields = ClassUtil.getAllFields(object, true);
        Class<?> classes = object.getClass();
        for (Field field : fields) {
            ApiModelProperty propertyAnno = field.getAnnotation(ApiModelProperty.class);
            LogIgnore logIgnoreAnno = field.getAnnotation(LogIgnore.class);
            if (propertyAnno != null && StringUtils.isNotBlank(propertyAnno.value()) && logIgnoreAnno == null) {
                if (field.getGenericType().toString().equals("class java.lang.String")) {
                    Method method = classes.getMethod("get" + ClassUtil.getMethodName(field.getName()));
                    String value = (String) method.invoke(object);
                    if (StringUtils.isNotBlank(value)) {
                        log.append(propertyAnno.value()).append(": ").append(value).append("; ");
                    }
                }
                if (field.getGenericType().toString().equals("class java.lang.Integer")) {
                    Method method = classes.getMethod("get" + ClassUtil.getMethodName(field.getName()));
                    Integer value = (Integer) method.invoke(object);
                    if (value != null) {
                        log.append(propertyAnno.value()).append(": ").append(value).append("; ");
                    }
                }
                if (field.getGenericType().toString().equals("class java.lang.Double")) {
                    Method method = classes.getMethod("get" + ClassUtil.getMethodName(field.getName()));
                    Double value = (Double) method.invoke(object);
                    if (value != null) {
                        log.append(propertyAnno.value()).append(": ").append(value).append("; ");
                    }
                }
                if (field.getGenericType().toString().equals("double")) {
                    Method method = classes.getMethod("get" + ClassUtil.getMethodName(field.getName()));
                    Double value = (Double) method.invoke(object);
                    if (value != null) {
                        log.append(propertyAnno.value()).append(": ").append(value).append("; ");
                    }
                }
                if (field.getGenericType().toString().equals("class java.lang.Boolean")) {
                    Method method = classes.getMethod("get" + ClassUtil.getMethodName(field.getName()));
                    Boolean value = (Boolean) method.invoke(object);
                    if (value != null) {
                        log.append(propertyAnno.value()).append(": ").append(value).append("; ");
                    }
                }
                if (field.getGenericType().toString().equals("boolean")) {
                    Method method = classes.getMethod("is" + ClassUtil.getMethodName(field.getName()));
                    Boolean value = (Boolean) method.invoke(object);
                    if (value != null) {
                        log.append(propertyAnno.value()).append(": ").append(value).append("; ");
                    }
                }
                if (field.getGenericType().toString().equals("class java.util.Date")) {
                    Method method = classes.getMethod("get" + ClassUtil.getMethodName(field.getName()));
                    Date value = (Date) method.invoke(object);
                    if (value != null) {
                        log.append(propertyAnno.value()).append(": ").append(IDateUtil.dateToString(value, DateFormat.DASH_D))
                                .append("; ");
                    }
                }
                if (field.getGenericType().toString().equals("class java.math.BigDecimal")) {
                    Method method = classes.getMethod("get" + ClassUtil.getMethodName(field.getName()));
                    BigDecimal value = (BigDecimal) method.invoke(object);
                    if (value != null) {
                        log.append(propertyAnno.value()).append(": ").append(value).append("; ");
                    }
                }
            }
        }
        return log.toString();
    }

    public static BaseLog generatorCommonBaseLog(LogHelper helper) {
        BaseLog baseLog = new BaseLog();
        HttpServletRequest request = SpringBeanUtil.getRequest();
        Integer systemCode = RequestUtil.getSystemCode(request);
        baseLog.setSystemCode(systemCode);
        baseLog.setIp(RequestUtil.getIp(request));
        baseLog.setOperation(GeneratorHelperUtil.getOperation(helper));
        baseLog.setOperatorId(LinitlyUtil.getCurrentUserId().toString());
        return baseLog;
    }
}
