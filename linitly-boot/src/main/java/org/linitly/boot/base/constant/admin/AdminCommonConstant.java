package org.linitly.boot.base.constant.admin;

import org.linitly.boot.base.constant.global.GlobalConstant;

/**
 * @author: linxiunan
 * @date: 2020/5/25 10:38
 * @descrption:
 */
public interface AdminCommonConstant {

    /**
     * admin系统路由前缀
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

    /**
     * 后台token过期时间(秒)
     */
    long ADMIN_TOKEN_EXPIRE_SECOND = 30 * 60;

    /**
     * 后台refresh_token过期时间(秒)
     */
    long ADMIN_REFRESH_TOKEN_EXPIRE_SECOND = 24 * 60 * 60;

    /**
     * 后台token对应角色信息、权限信息、岗位信息、部门信息过期时间(秒)
     */
    long ADMIN_RPPD_EXPIRE_SECOND = 24 * 60 * 60;

    /**
     * 后台token存入redis的前缀
     */
    String ADMIN_TOKEN_PREFIX = GlobalConstant.REDIS_KEY_PREFIX + AdminCommonConstant.REDIS_KEY_INFIX + "token:";

    /**
     * 后台refresh_token存入redis的前缀
     */
    String ADMIN_REFRESH_TOKEN_PREFIX = GlobalConstant.REDIS_KEY_PREFIX + AdminCommonConstant.REDIS_KEY_INFIX + "refresh_token:";

    /**
     * 后台用户对应角色set存储前缀
     */
    String ADMIN_ROLES_PREFIX = GlobalConstant.REDIS_KEY_PREFIX + AdminCommonConstant.REDIS_KEY_INFIX + "roles:";

    /**
     * 后台用户对应权限set存储前缀
     */
    String ADMIN_FUNCTION_PERMISSIONS_PREFIX = GlobalConstant.REDIS_KEY_PREFIX + AdminCommonConstant.REDIS_KEY_INFIX + "permissions:";

    /**
     * 后台用户对应岗位set存储前缀
     */
    String ADMIN_POSTS_PREFIX = GlobalConstant.REDIS_KEY_PREFIX + AdminCommonConstant.REDIS_KEY_INFIX + "posts:";

    /**
     * 后台用户对应部门set存储前缀
     */
    String ADMIN_DEPTS_PREFIX = GlobalConstant.REDIS_KEY_PREFIX + AdminCommonConstant.REDIS_KEY_INFIX + "depts:";

    /**
     * 后台最后一次过期token存入redis的前缀
     */
    String ADMIN_LAST_EXPIRED_TOKEN_PREFIX = GlobalConstant.REDIS_KEY_PREFIX + AdminCommonConstant.REDIS_KEY_INFIX + "expire_token:";
}
