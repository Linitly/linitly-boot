package org.linitly.boot.base.constant.admin;

/**
 * @author: linxiunan
 * @date: 2020/5/25 10:38
 * @descrption:
 */
public interface AdminCommonConstant {

    /**
     * 路径前缀
     */
    String URL_PREFIX = "/admin";

    /**
     * redis存储后台key中缀
     */
    String REDIS_KEY_INFIX = "admin:";

    /**
     * MD5加密普通盐(后台用户)
     */
    String PWD_MD5_SALT = "sadsjdf87981!!&^*(ADMIN!^@*&hfasdf;laskdhghas8f]oewirpjpjvnh}[]]jjjjasdfadsdaiu899999999asdfksdjfhga;dkfjahioghioadsiofj";

    /**
     * 默认分页每页数量
     */
    String PAGE_SIZE = "10";

    /**
     * 默认分页的页码
     */
    String PAGE_NUMBER = "1";

    /**
     * 返回给后台用户的token名称
     */
    String ADMIN_TOKEN = "token";

    /**
     * 返回给后台用户的refresh_token名称
     */
    String ADMIN_REFRESH_TOKEN = "refresh_token";

    /**
     * 后台用户使用restful风格请求路径中带参数时使用AES加密算法使用的key
     * AES加密解密测试:AES/CBC/PKCS5Padding,iv=key,结果输出为base64,128位。由于将key当iv，所以限制key必须为16位，否则iv会报错
     */
    String ADMIN_REST_AES_KEY = "06f1MQd42U9wcI11";
}
