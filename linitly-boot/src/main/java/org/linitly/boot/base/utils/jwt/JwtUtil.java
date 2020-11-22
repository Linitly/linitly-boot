package org.linitly.boot.base.utils.jwt;

import org.linitly.boot.base.helper.entity.BaseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Map;

/**
 * @author: linxiunan
 * @date: 2020/11/22 16:55
 * @descrption:
 */
interface JwtUtil {

    Key getKeyInstance();

    Map<String, Object> parseJwt(String jwt);

    String generateJwt(Map<String, Object> claims, String subject, long expireSecond);

    String[] generateToken(BaseEntity entity);

    String getToken(HttpServletRequest request);

    String getRefreshToken(HttpServletRequest request);

    void setToken(HttpServletResponse response, BaseEntity baseEntity);

    String getUserId(HttpServletRequest request);
}
