package org.linitly.boot.base.constant.admin;

import io.jsonwebtoken.SignatureAlgorithm;
import org.linitly.boot.base.constant.global.GlobalConstant;

public interface AdminJwtConstant {

    /**
     * 后台token加解密算法
     */
    SignatureAlgorithm ADMIN_ALGORITHM = SignatureAlgorithm.HS512;

    /**
     * 后台用户id存储jwt的key值
     */
    String ADMIN_USER_ID = "admin_user_id";

    /**
     * JWT加密盐
     */
    String JWT_SALT = "jwt_salt!!!!@@##haskdjalk$&*(@!&$(@!JKLAJsad(*&*)(dsadDLKSJKLDSAKL:-=23891IOP";
}
