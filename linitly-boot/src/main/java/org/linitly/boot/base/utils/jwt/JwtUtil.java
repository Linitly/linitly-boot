package org.linitly.boot.base.utils.jwt;

import org.linitly.boot.base.helper.entity.BaseEntity;

import javax.servlet.http.HttpServletRequest;
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

    String generateToken(BaseEntity entity);

    String generateRefreshToken(BaseEntity entity);

    String getToken(HttpServletRequest request);

    String getRefreshToken(HttpServletRequest request);

    String getUserId(HttpServletRequest request);

    void interceptorValid(HttpServletRequest request);

    Map parseToken(String token);

    Map parseRefreshToken(String refreshToken);

    void validToken(String token, Map<String, Object> claims);

    void validRefreshToken(String refreshToken, Map<String, Object> claims);

    void validExpiredToken(String token);

    void generateNewToken();
}