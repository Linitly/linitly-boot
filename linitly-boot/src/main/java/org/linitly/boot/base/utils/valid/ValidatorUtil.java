package org.linitly.boot.base.utils.valid;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * @Description 校验组校验工具类
 * @author siwei
 * @date 2018年8月3日
 */
public class ValidatorUtil {

	/**
	 * 使用默认校验组校验传入的实体类
	 * @param entity
	 * @return
	 */
	public static <T> String defaultValid(T entity){
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> validate = validator.validate(entity, Default.class);
		if(!validate.isEmpty()){
			StringBuffer buffer = new StringBuffer();
			validate.forEach(e -> buffer.append(e.getMessage()).append(";"));
			return buffer.toString();
		}
		return null;
	}
	
	/**
	 * 使用传入校验组(可以多个)校验传入的实体类
	 * @param entity
	 * @return
	 */
	public static <T> String validByGroups(T entity, Class<?>... groupsClass){
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<T>> validate = validator.validate(entity, groupsClass);
		if(!validate.isEmpty()){
			StringBuffer buffer = new StringBuffer();
			validate.forEach(e -> buffer.append(e.getMessage()).append(";"));
			return buffer.toString();
		}
		return null;
	}
}
