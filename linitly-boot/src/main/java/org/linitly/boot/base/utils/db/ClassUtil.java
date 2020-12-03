package org.linitly.boot.base.utils.db;

import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.exception.CommonException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassUtil {

    private static final String MAPPER_CLASS_NAME_SUFFIX = "Mapper";

	public static Long getEntityId(Object object, Field[] fields) throws Exception {
		Class<?> classes = object.getClass();
		Long entityId = null;
		for (Field field : fields) {
			if (field.getName().equals("id")) {
				Method method = classes.getMethod("get" + getMethodName(field.getName()));
				entityId = (Long) method.invoke(object);
				break;
			}
		}
		return entityId;
	}

	public static String getMethodName(String filedName) {
		byte[] items = filedName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	/**
	 * 获取某个类的所有属性(可以包含父类的属性)
	 * @param object
	 * @return
	 */
	public static Field[] getAllFields(Object object, boolean getParent) {
		Class clazz = object.getClass();
		return getAllFields(clazz, getParent);
	}

    public static Field[] getAllFields(Class<?> clazz, boolean getParent) {
        List<Field> fieldList = new ArrayList<>();
        if (getParent) {
            while (clazz != null){
                fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
                clazz = clazz.getSuperclass();
            }
        } else {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

	public static Method getSetMethod(Field field, Class<?> classes) throws NoSuchMethodException {
		String methodName = "set" + getMethodName(field.getName());
		return classes.getDeclaredMethod(methodName, field.getType());
	}

	public static Class getFieldType(Field field) {
		if (field == null) {
			return null;
		}
		if (field.getGenericType().toString().equals("class java.lang.String")) {
			return String.class;
		}
		if (field.getGenericType().toString().equals("class java.lang.Integer")) {
			return Integer.class;
		}
		if (field.getGenericType().toString().equals("class java.lang.Double") || field.getGenericType().toString().equals("double")) {
			return Double.class;
		}
		if (field.getGenericType().toString().equals("class java.lang.Float") || field.getGenericType().toString().equals("float")) {
			return Float.class;
		}
		if (field.getGenericType().toString().equals("class java.lang.Long") || field.getGenericType().toString().equals("long")) {
			return Long.class;
		}
		if (field.getGenericType().toString().equals("class java.lang.Boolean") || field.getGenericType().toString().equals("boolean")) {
			return Boolean.class;
		}
		if (field.getGenericType().toString().equals("class java.util.Date")) {
			return Date.class;
		}
		if (field.getGenericType().toString().equals("class java.math.BigDecimal")) {
			return BigDecimal.class;
		}
		return null;
	}

	public static Class getClassByName(String className) {
		Class<?> classes = null;
		try {
			classes = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new CommonException(GlobalConstant.GENERAL_ERROR, "获取class对象出错");
		}
		return classes;
	}

	public static String getTableName(String mapperClassName) {
		StringBuffer tableName = new StringBuffer();
		if (StringUtils.isNotBlank(mapperClassName)) {
			if (mapperClassName.contains(".")) {
				int last = mapperClassName.lastIndexOf(".");
				mapperClassName = mapperClassName.substring(last + 1);
			}
			mapperClassName = String.valueOf(mapperClassName.charAt(0)).toLowerCase().concat(mapperClassName.substring(1));
			mapperClassName = mapperClassName.replace(MAPPER_CLASS_NAME_SUFFIX, "");
			Pattern pattern = Pattern.compile("[A-Z]");
			Matcher matcher = pattern.matcher(mapperClassName);
			while (matcher.find()) {
				matcher.appendReplacement(tableName, "_" + matcher.group(0).toLowerCase());
			}
			matcher.appendTail(tableName);
		}
		return StringUtils.isBlank(tableName) ? null : tableName.toString();
	}

    public static <T> String getTableName(T interfaze) {
        String className = interfaze.getClass().getName();
        return getTableName(className);
    }

    public static <T> String getLogTableName(T interfaze) {
        String tableName = getTableName(interfaze);
        return StringUtils.isBlank(tableName) ? null : tableName + GlobalConstant.LOG_TABLE_SUFFIX;
    }

    public static <T> String getDeleteTableName(T interfaze) {
        String tableName = getTableName(interfaze);
        return StringUtils.isBlank(tableName) ? null : tableName + GlobalConstant.DELETE_TABLE_SUFFIX;
    }

    public static String getLogTableName(String mapperClassName) {
		String tableName = getTableName(mapperClassName);
		return StringUtils.isBlank(tableName) ? null : tableName + GlobalConstant.LOG_TABLE_SUFFIX;
	}

	public static <T> String getDeleteTableName(String mapperClassName) {
		String tableName = getTableName(mapperClassName);
		return StringUtils.isBlank(tableName) ? null : tableName + GlobalConstant.DELETE_TABLE_SUFFIX;
	}
}
