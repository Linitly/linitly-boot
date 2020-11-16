package org.linitly.boot.base.utils.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.constant.admin.AdminJwtConstant;
import org.linitly.boot.base.helper.entity.BaseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtAdminUtil {

    private static String UUID_JWT = "uuid";

    /**
     * @author linxiunan
     * @date 10:17 2020/5/7
     * @description
     */
    public static String[] generateAdminTokens(BaseEntity entity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AdminJwtConstant.ADMIN_USER_ID, entity.getId());
        String token = generateJwt(claims, entity.getId().toString(), AdminJwtConstant.ADMIN_ALGORITHM, AdminJwtConstant.ADMIN_TOKEN_EXPIRE_SECOND);
        String refresh_token = generateJwt(claims, entity.getId().toString(), AdminJwtConstant.ADMIN_ALGORITHM, AdminJwtConstant.ADMIN_REFRESH_TOKEN_EXPIRE_SECOND);
        return new String[]{token, refresh_token};
    }

    /**
     * @author linxiunan
     * @date 10:17 2020/5/7
     * @description
     */
    private static String generateJwt(Map<String, Object> claims, String subject, SignatureAlgorithm algorithm, long expireSecond) {
        claims = claims == null ? new HashMap<>() : claims;
        claims.put(UUID_JWT, UUID.randomUUID().toString());
        String compactJws = Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expireSecond * 1000))
                .signWith(algorithm, JwtCommonUtil.getKeyInstance())
                .compact();
        return compactJws;
    }

    /**
     * 根据request对象获取token信息，解密token获取后台用户id
     */
    public static String getAdminUserId(HttpServletRequest request) {
        String refreshToken = getAdminRefreshToken(request);
        if (StringUtils.isBlank(refreshToken)) {
            return null;
        }
        Map<String, Object> claims = JwtCommonUtil.parseJwt(refreshToken);
        return claims.get(AdminJwtConstant.ADMIN_USER_ID).toString();
    }

    public static String getAdminToken(HttpServletRequest request) {
        if (request != null) {
            return request.getHeader(AdminCommonConstant.ADMIN_TOKEN);
        }
        return null;
    }

    public static String getAdminRefreshToken(HttpServletRequest request) {
        if (request != null) {
            return request.getHeader(AdminCommonConstant.ADMIN_REFRESH_TOKEN);
        }
        return null;
    }
}
