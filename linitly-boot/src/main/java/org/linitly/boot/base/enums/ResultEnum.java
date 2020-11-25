package org.linitly.boot.base.enums;

/**
 * 异常类型枚举类
 * @author linxiunan
 * @date 2017年10月23日
 */
public enum ResultEnum {

	SUCCESS(200, "成功"),
	SYSTEM_ERROR(500, "服务器开小差，请稍后重试"),
	UN_SUPPORT_REQUEST(502, "不支持的请求类型"),
	SYSTEM_CODE_ERROR(503, "系统码错误"),

	NO_PERMISSION(401, "您没有权限访问该内容"),
	UNAUTHORIZED(403, "未认证,token为空"),
	LOGIN_FAILURE(412, "登录失效,请重新登录"),
	TOKEN_ANALYSIS_ERROR(413, "token解析失败"),
	REMOTE_LOGIN(417, "您的账号在异地登录,请重新登录"),

	PARAM_EMPTY_ERROR(420, "所需参数未传入"),
	PATH_PARAM_ERROR(421, "所需路径参数传入错误"),

	EXCEL_UPLOAD_FORMAT_ERROR(431, "上传文件仅限.xls、.xlsx格式，请重新上传"),
	EXCEL_SHEET_ANALYSE_ERROR(432, "excel文件页解析错误，请确认"),
	EXCEL_ROW_NUMBER_ERROR(433, "excel文件有效行不足，请确认"),
	EXCEL_READ_EMPTY_ERROR(434, "未读取到有效内容，请检查"),
	EXCEL_UPPER_LIMIT_ERROR(435, "总记录条数超上限，请分批处理"),

	CLASS_METHOD_ERROR(440, "反射获取方法失败"),
	ANNOTATION_GET_ERROR(441, "获取权限注解内容错误"),

	FILE_NOT_UPLOAD_ERROR(456, "所需文件没有上传"),
	FILE_TOO_BIG_ERROR(458, "文件大小超过限制"),
	FILE_DOWNLOAD_ERROR(459, "文件下载失败"),


	DUPLICATE_KEY_ERROR(461, "唯一键已存在，请检查或刷新重试"),

	SEND_MSG_ERROR(471, "发送短信验证码失败，请稍后重试并检查手机号是否正确"),
	BUSINESS_LIMIT_CONTROL(472, "发送短信过于频繁，请稍后重试并检查手机号是否正确"),

	FACTORY_GET_ERROR(490, "工厂获取对象错误"),
	;

	private Integer code;
	private String message;

	ResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}

