package org.linitly.boot.base.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * 
 * @Title: JsonUtils.java
 * @Package com.lee.utils
 * @Description: 自定义响应结构, 转换类 Copyright: Copyright (c) 2016
 *               Company:Nathan.Lee.Salvatore
 * 
 * @author leechenxiang
 * @date 2016年4月29日 下午11:05:03
 * @version V1.0
 */
public class JsonUtils {

	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static String objectToJsonNull(Object data) {
		try {
			return MAPPER.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String objectToJsonNotNull(Object data) {
		MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		try {
			return MAPPER.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将对象转换成json字符串。
	 */
	public static String objectToJson(Object data, boolean notNull) {
		if (notNull) {
			return objectToJsonNotNull(data);
		} else {
			return objectToJsonNull(data);
		}
	}

	private static <T> T jsonToEntityUnknownError(String jsonData, Class<T> beanType) throws IOException {
		return MAPPER.readValue(jsonData, beanType);
	}

	private static <T> T jsonToEntityIgnoreUnknown(String jsonData, Class<T> beanType) throws IOException {
		MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return MAPPER.readValue(jsonData, beanType);
	}

	/**
	 * 将json结果集转化为对象
	 */
	public static <T> T jsonToEntity(String jsonData, Class<T> beanType, boolean ignoreUnknown) throws IOException {
		if (ignoreUnknown) {
			return jsonToEntityIgnoreUnknown(jsonData, beanType);
		} else {
			return jsonToEntityUnknownError(jsonData, beanType);
		}
	}

	/**
	 * 将json数据转换成entity对象list
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) throws IOException {
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
		return MAPPER.readValue(jsonData, javaType);
	}


}
