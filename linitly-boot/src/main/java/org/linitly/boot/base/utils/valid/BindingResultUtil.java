package org.linitly.boot.base.utils.valid;

import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.helper.entity.ResponseResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @Description 获取验证消息工具类
 * @author siwei
 * @date 2018年8月24日
 */
public class BindingResultUtil {

	public static String getBindingResultErrMsg(BindingResult bindingResult) {
		StringBuffer errorMsg = new StringBuffer();
		bindingResult.getAllErrors().forEach(e -> {
			if(e instanceof FieldError){
				FieldError fe = (FieldError)e;
				// 返回验证信息去除 field显示
//				errorMsg.append(fe.getField()).append(fe.getDefaultMessage()).append(";");
				errorMsg.append(fe.getDefaultMessage()).append(";");
				return;
			}
			errorMsg.append(e.getDefaultMessage()).append(";");
		});
		return errorMsg.toString().substring(0, errorMsg.length() - 1);
	}

	/**
	 * 根据校验结果类返回统一返回封装类
	 * @param bindingResult 校验结果类
	 * @return
	 */
	public static ResponseResult paramError(BindingResult bindingResult) {
		return new ResponseResult(GlobalConstant.GENERAL_ERROR, getBindingResultErrMsg(bindingResult));
	}
}
