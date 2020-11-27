package org.linitly.boot.base.utils.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.constant.admin.AdminJwtConstant;
import org.linitly.boot.base.constant.global.JwtConstant;
import org.linitly.boot.base.helper.entity.BaseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtAdminUtil extends AbstractJwtUtil {

    private static JwtAdminUtil jwtAdminUtil;

    private JwtAdminUtil(SignatureAlgorithm algorithm, String salt) {
        super(algorithm, salt);
    }

    public static JwtAdminUtil getInstance() {
        if (jwtAdminUtil == null) {
            synchronized (JwtAdminUtil.class) {
                if (jwtAdminUtil == null) {
                    jwtAdminUtil = new JwtAdminUtil(AdminJwtConstant.ADMIN_ALGORITHM, AdminJwtConstant.JWT_SALT);
                }
            }
        }
        return jwtAdminUtil;
    }

    @Override
    public String generateJwt(Map<String, Object> claims, String subject, long expireSecond) {
        claims = claims == null ? new HashMap<>() : claims;
        claims.put(JwtConstant.UUID_JWT_KEY, UUID.randomUUID().toString());
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expireSecond * 1000))
                .signWith(algorithm, getKeyInstance())
                .compact();
    }

    @Override
    public String[] generateToken(BaseEntity entity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AdminJwtConstant.ADMIN_USER_ID, entity.getId());
        String token = generateJwt(claims, entity.getId().toString(), AdminCommonConstant.ADMIN_TOKEN_EXPIRE_SECOND);
        String refresh_token = generateJwt(claims, entity.getId().toString(), AdminCommonConstant.ADMIN_REFRESH_TOKEN_EXPIRE_SECOND);
        return new String[]{token, refresh_token};
    }

    @Override
    public String getToken(HttpServletRequest request) {
        return request == null ? null : request.getHeader(AdminCommonConstant.ADMIN_TOKEN);
    }

    @Override
    public String getRefreshToken(HttpServletRequest request) {
        return request == null ? null : request.getHeader(AdminCommonConstant.ADMIN_REFRESH_TOKEN);
    }

    @Override
    public void setToken(HttpServletResponse response, BaseEntity baseEntity) {
        String[] tokens = generateToken(baseEntity);
        response.setHeader(AdminCommonConstant.ADMIN_TOKEN, tokens[0]);
        response.setHeader(AdminCommonConstant.ADMIN_REFRESH_TOKEN, tokens[1]);
    }

    @Override
    public String getUserId(HttpServletRequest request) {
        String refreshToken = getRefreshToken(request);
        if (StringUtils.isBlank(refreshToken)) {
            return null;
        }
        Map<String, Object> claims = parseJwt(refreshToken);
        return claims.get(AdminJwtConstant.ADMIN_USER_ID).toString();
    }

    public String getUserId(Map<String, Object> claims) {
        return claims.get(AdminJwtConstant.ADMIN_USER_ID).toString();
    }
}
