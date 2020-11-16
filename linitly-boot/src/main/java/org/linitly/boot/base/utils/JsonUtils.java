package org.linitly.boot.base.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
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
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String objectToJsonNotNull(Object data) {
		MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将对象转换成json字符串。
	 * <p>
	 * Title: entityToJson
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @param data
	 * @return
	 */
	public static String objectToJson(Object data, boolean notNull) {
		if (notNull) {
			return objectToJsonNotNull(data);
		} else {
			return objectToJsonNull(data);
		}
	}

	private static <T> T jsonToEntityUnknownError(String jsonData, Class<T> beanType) throws JsonParseException, JsonMappingException, IOException {
		T t = MAPPER.readValue(jsonData, beanType);
		return t;
	}

	private static <T> T jsonToEntityIgnoreUnknown(String jsonData, Class<T> beanType) throws JsonParseException, JsonMappingException, IOException {
		MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		T t = MAPPER.readValue(jsonData, beanType);
		return t;
	}

	/**
	 * 将json结果集转化为对象
	 *
	 * @param jsonData
	 *            json数据
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> T jsonToEntity(String jsonData, Class<T> beanType, boolean ignoreUnknown) throws JsonParseException, JsonMappingException, IOException {
		if (ignoreUnknown) {
			return jsonToEntityIgnoreUnknown(jsonData, beanType);
		} else {
			return jsonToEntityUnknownError(jsonData, beanType);
		}
	}

	/**
	 * 将json数据转换成entity对象list
	 * <p>
	 * Title: jsonToList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param jsonData
	 * @param beanType
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) throws JsonParseException, JsonMappingException, IOException {
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
		List<T> list = MAPPER.readValue(jsonData, javaType);
		return list;
	}

}
