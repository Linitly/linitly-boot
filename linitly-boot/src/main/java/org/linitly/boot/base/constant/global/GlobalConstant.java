package org.linitly.boot.base.constant.global;


public interface GlobalConstant {

    /**
     * 自定义线程池名字
     */
	String THREAD_POOL_NAME = "taskExecutor";

	/**
	 * 常规错误码
	 */
	int GENERAL_ERROR = 405;

	/**
	 * 手机号码正则表达式
	 */
	String MOBILE_NUMBER_REG = "^(1[3-9])\\d{9}$";

	/**
	 * 手机号格式不正确提示
	 */
	String MOBILE_ERROR = "手机号格式不正确";

	/**
	 * 日志表后缀
	 */
	String LOG_TABLE_SUFFIX = "_log";

	/**
	 * restful使用的aes加密算法key值
	 * AES加密解密测试:AES/CBC/PKCS5Padding,iv=key,结果输出为base64,128位。由于将key当iv，所以限制key必须为16位，否则iv会报错
	 */
	String REST_AES_KEY = "06f1MQd42U9wcI11";

	/**
	 * id最小值，使用整形或者长整型id时校验使用
	 */
	int ID_MIN = 1;

	String ID_NOTNULL_TIP = "唯一标识不能为空";

	String ID_ERROR_TIP = "唯一标识大小不符合限制";

	/**
	 * redis存储key前缀
	 */
	String REDIS_KEY_PREFIX = "linitly:boot:";

	/**
	 * request存储system_code请求头的key
	 */
	String SYSTEM_CODE_KEY = "system_code";
}
