package org.linitly.boot.base.interceptor;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.utils.RedisOperator;
import org.linitly.boot.base.utils.jwt.JwtAdminUtil;
import org.linitly.boot.base.utils.jwt.JwtCommonUtil;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.constant.admin.AdminJwtConstant;
import org.linitly.boot.base.constant.entity.AdminUserConstant;
import org.linitly.boot.base.exception.CommonException;
import org.linitly.boot.base.helper.entity.BaseEntity;
import org.linitly.boot.base.utils.encrypt.EncryptionUtil;
import org.linitly.boot.base.utils.permission.AntPathMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author linxiunan
 * @date 17:41 2020/5/6
 * @description 后台请求token验证
 */
@Slf4j
@Component
public class AdminTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisOperator redisOperator;

    private static final List<String> UN_CHECK_URIS = new ArrayList<>();

    static {
        // 登录接口
        UN_CHECK_URIS.add(AdminCommonConstant.URL_PREFIX + "/login");

        // 退出接口
        UN_CHECK_URIS.add(AdminCommonConstant.URL_PREFIX + "/logout/**");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String unCheckUri : UN_CHECK_URIS) {
            if (antPathMatcher.matches(unCheckUri, uri))
                return true;
        }
        String token = JwtAdminUtil.getAdminToken(request);
        String refreshToken = JwtAdminUtil.getAdminRefreshToken(request);
        if (StringUtils.isBlank(token) || StringUtils.isBlank(refreshToken)) {
            throw new CommonException(ResultEnum.UNAUTHORIZED);
        }
        return parseToken(token, refreshToken, response);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private boolean parseToken(String token, String refreshToken, HttpServletResponse response) {
        Map<String, Object> claims = null;
        try {
            claims = JwtCommonUtil.parseJwt(token);
        } catch (ExpiredJwtException e) {
            // token过期，使用refresh_token校验
            return parseRefreshToken(refreshToken, response, true);
        } catch (Exception e) {
            log.error("【后台token解密失败】，token为" + token);
            throw new CommonException(ResultEnum.TOKEN_ANALYSIS_ERROR);
        }
        return validToken(claims, token, refreshToken, response);
    }

    private boolean parseRefreshToken(String refreshToken, HttpServletResponse response, boolean generateNewTokens) {
        Map<String, Object> claims = null;
        try {
            claims = JwtCommonUtil.parseJwt(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new CommonException(ResultEnum.LOGIN_FAILURE);
        } catch (Exception e) {
            log.error("【后台refresh_token解密失败】，refresh_token为" + refreshToken);
            throw new CommonException(ResultEnum.TOKEN_ANALYSIS_ERROR);
        }
        return validRefreshToken(claims, refreshToken, response, generateNewTokens);
    }

    private boolean validRefreshToken(Map<String, Object> claims, String refreshToken, HttpServletResponse response, boolean generateNewTokens) {
        String userId = claims.get(AdminJwtConstant.ADMIN_USER_ID).toString();
        String redisKey = AdminJwtConstant.ADMIN_REFRESH_TOKEN_PREFIX + EncryptionUtil.md5(userId, AdminUserConstant.TOKEN_ID_SALT);
        String redisRefreshToken = redisOperator.get(redisKey);
        if (StringUtils.isBlank(redisRefreshToken)) {
            throw new CommonException(ResultEnum.LOGIN_FAILURE);
        } else if (!refreshToken.equals(redisRefreshToken)) {
            throw new CommonException(ResultEnum.REMOTE_LOGIN);
        }
        // 生成新的token对
        if (generateNewTokens) {
            BaseEntity baseEntity = new BaseEntity();
            baseEntity.setId(Long.valueOf(userId));
            String[] tokens = JwtAdminUtil.generateAdminTokens(baseEntity);
            redisOperator.set(AdminJwtConstant.ADMIN_TOKEN_PREFIX + EncryptionUtil.md5(userId, AdminUserConstant.TOKEN_ID_SALT), tokens[0], AdminJwtConstant.ADMIN_TOKEN_EXPIRE_SECOND);
            redisOperator.set(AdminJwtConstant.ADMIN_REFRESH_TOKEN_PREFIX + EncryptionUtil.md5(userId, AdminUserConstant.TOKEN_ID_SALT), tokens[1], AdminJwtConstant.ADMIN_REFRESH_TOKEN_EXPIRE_SECOND);
            response.setHeader(AdminCommonConstant.ADMIN_TOKEN, tokens[0]);
            response.setHeader(AdminCommonConstant.ADMIN_REFRESH_TOKEN, tokens[1]);
            // 如果需要存储用户权限信息，需要设置和refresh_token相同的过期时间，并且此处更新token的同时需要刷新在redis中的过期时间
        }
        return true;
    }

    private boolean validToken(Map<String, Object> claims, String token, String refreshToken, HttpServletResponse response) {
        String userId = claims.get(AdminJwtConstant.ADMIN_USER_ID).toString();
        String redisKey = AdminJwtConstant.ADMIN_TOKEN_PREFIX + EncryptionUtil.md5(userId, AdminUserConstant.TOKEN_ID_SALT);
        String redisToken = redisOperator.get(redisKey);
        if (StringUtils.isBlank(redisToken)) {
            throw new CommonException(ResultEnum.LOGIN_FAILURE);
        } else if (!token.equals(redisToken)) {
            throw new CommonException(ResultEnum.REMOTE_LOGIN);
        }
        return parseRefreshToken(refreshToken, response, false);
    }
}
