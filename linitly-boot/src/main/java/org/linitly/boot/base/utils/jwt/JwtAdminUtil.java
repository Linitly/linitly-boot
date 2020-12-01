package org.linitly.boot.base.utils.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.constant.admin.AdminJwtConstant;
import org.linitly.boot.base.constant.entity.SysAdminUserConstant;
import org.linitly.boot.base.constant.global.JwtConstant;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.enums.SystemEnum;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.helper.entity.BaseEntity;
import org.linitly.boot.base.utils.algorithm.EncryptionUtil;
import org.linitly.boot.base.utils.auth.AuthFactory;
import org.linitly.boot.base.utils.bean.SpringBeanUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
                    abstractAuth = AuthFactory.getAuth(SystemEnum.ADMIN.getSystemCode());
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
    public String generateToken(BaseEntity entity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AdminJwtConstant.ADMIN_USER_ID, entity.getId());
        return generateJwt(claims, entity.getId().toString(), AdminCommonConstant.ADMIN_TOKEN_EXPIRE_SECOND);
    }

    @Override
    public String generateRefreshToken(BaseEntity entity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AdminJwtConstant.ADMIN_USER_ID, entity.getId());
        return generateJwt(claims, entity.getId().toString(), AdminCommonConstant.ADMIN_REFRESH_TOKEN_EXPIRE_SECOND);
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
    public String getUserId(HttpServletRequest request) {
        String refreshToken = getRefreshToken(request);
        if (StringUtils.isBlank(refreshToken)) {
            return null;
        }
        Map<String, Object> claims = parseJwt(refreshToken);
        return claims.get(AdminJwtConstant.ADMIN_USER_ID).toString();
    }

    @Override
    public void interceptorValid(HttpServletRequest request) {
        String token = getToken(request);
        String refreshToken = getRefreshToken(request);
        if (StringUtils.isBlank(token) || StringUtils.isBlank(refreshToken)) {
            throw new CommonException(ResultEnum.UNAUTHORIZED);
        }
        Map<String, Object> claims;
        try {
            claims = parseToken(token);
        } catch (ExpiredJwtException e) {
            tokenExpired(token, refreshToken);
            return;
        }
        validToken(token, claims);
        claims = parseRefreshToken(refreshToken);
        validRefreshToken(refreshToken, claims);
    }

    private void tokenExpired(String token, String refreshToken) {
        Map<String, Object> claims = parseRefreshToken(refreshToken);
        validRefreshToken(refreshToken, claims);
        validExpiredToken(token);
        generateNewToken();
    }

    @Override
    public void validToken(String token, Map<String, Object> claims) {
        String userId = claims.get(AdminJwtConstant.ADMIN_USER_ID).toString();
        String redisKey = AdminCommonConstant.ADMIN_TOKEN_PREFIX + EncryptionUtil.md5(userId, SysAdminUserConstant.TOKEN_ID_SALT);
        String redisToken = String.valueOf(redisTemplate.opsForValue().get(redisKey));
        if (StringUtils.isBlank(redisToken)) {
            throw new CommonException(ResultEnum.LOGIN_FAILURE);
        } else if (!token.equals(redisToken)) {
            throw new CommonException(ResultEnum.REMOTE_LOGIN);
        }
    }

    @Override
    public void validExpiredToken(String token) {
        String userId = getUserId(SpringBeanUtil.getRequest());
        String lastExpiredToken = String.valueOf(redisTemplate.opsForHash().get(AdminCommonConstant.ADMIN_LAST_EXPIRED_TOKEN_KEY, userId));
        if (StringUtils.isBlank(lastExpiredToken)) {
            redisTemplate.opsForHash().put(AdminCommonConstant.ADMIN_LAST_EXPIRED_TOKEN_KEY, userId, token);
            redisTemplate.expire(AdminCommonConstant.ADMIN_LAST_EXPIRED_TOKEN_KEY, AdminCommonConstant.ADMIN_TOKEN_EXPIRE_SECOND * 2, TimeUnit.SECONDS);
            return;
        }
        if (!token.equals(lastExpiredToken)) {
            redisTemplate.opsForHash().put(AdminCommonConstant.ADMIN_LAST_EXPIRED_TOKEN_KEY, userId, token);
            return;
        }
        if (token.equals(lastExpiredToken)) {
            throw new CommonException(ResultEnum.LOGIN_FAILURE);
        }
    }

    @Override
    public void validRefreshToken(String refreshToken, Map<String, Object> claims) {
        String userId = claims.get(AdminJwtConstant.ADMIN_USER_ID).toString();
        String redisKey = AdminCommonConstant.ADMIN_REFRESH_TOKEN_PREFIX + EncryptionUtil.md5(userId, SysAdminUserConstant.TOKEN_ID_SALT);
        String redisRefreshToken = String.valueOf(redisTemplate.opsForValue().get(redisKey));
        if (StringUtils.isBlank(redisRefreshToken)) {
            throw new CommonException(ResultEnum.LOGIN_FAILURE);
        } else if (!refreshToken.equals(redisRefreshToken)) {
            throw new CommonException(ResultEnum.REMOTE_LOGIN);
        }
    }

    @Override
    public void generateNewToken() {
        String userId = getUserId(SpringBeanUtil.getRequest());
        BaseEntity baseEntity = new BaseEntity().setId(Long.valueOf(userId));
        String token = generateToken(baseEntity);
        SpringBeanUtil.getResponse().setHeader(AdminCommonConstant.ADMIN_TOKEN, token);
        abstractAuth.newTokenRedisSet(userId, token);
    }
}
