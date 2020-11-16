package org.linitly.boot.base.constant.admin;

import io.jsonwebtoken.SignatureAlgorithm;
import org.linitly.boot.base.constant.global.GlobalConstant;

public interface AdminJwtConstant {

    /**
     * 后台token加解密算法
     */
    SignatureAlgorithm ADMIN_ALGORITHM = SignatureAlgorithm.HS512;

    /**
     * 后台token过期时间(秒)
     */
    long ADMIN_TOKEN_EXPIRE_SECOND = 30 * 60;

    /**
     * 后台refresh_token过期时间(秒)
     */
    long ADMIN_REFRESH_TOKEN_EXPIRE_SECOND = 15 * 24 * 60 * 60;

    /**
     * 后台token对应角色信息和权限信息过期时间(秒)
     */
    long ADMIN_RP_EXPIRE_SECOND = 60 * 60 + 10;

    /**
     * 后台用户id存储jwt的key值
     */
    String ADMIN_USER_ID = "admin_user_id";

    /**
     * JWT加密盐
     */
    String JWT_SALT = "jwt_salt!!!!@@##haskdjalk$&*(@!&$(@!JKLAJsad(*&*)(dsadDLKSJKLDSAKL:-=23891IOP";

    /**
     * 后台token存入redis的前缀
     */
    String ADMIN_TOKEN_PREFIX = GlobalConstant.REDIS_KEY_PREFIX + AdminCommonConstant.REDIS_KEY_INFIX + "token:";

    /**
     * 后台refresh_token存入redis的前缀
     */
    String ADMIN_REFRESH_TOKEN_PREFIX = GlobalConstant.REDIS_KEY_PREFIX + AdminCommonConstant.REDIS_KEY_INFIX + "refresh_token:";

    /**
     * 后台用户token对应角色set存储前缀
     */
    String ADMIN_ROLES_PREFIX = GlobalConstant.REDIS_KEY_PREFIX + AdminCommonConstant.REDIS_KEY_INFIX + "roles:";

    /**
     * 后台用户token对应权限set存储前缀
     */
    String ADMIN_PERMISSIONS_PREFIX = GlobalConstant.REDIS_KEY_PREFIX + AdminCommonConstant.REDIS_KEY_INFIX + "permissions:";
}
