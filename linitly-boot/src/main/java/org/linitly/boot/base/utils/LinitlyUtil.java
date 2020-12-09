package org.linitly.boot.base.utils;

import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.helper.entity.BaseEntity;
import org.linitly.boot.base.utils.auth.AbstractAuth;
import org.linitly.boot.base.utils.bean.SpringBeanUtil;
import org.linitly.boot.base.utils.jwt.AbstractJwtUtil;
import org.linitly.boot.base.utils.jwt.JwtUtilFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author: linxiunan
 * @date: 2020/12/9 8:51
 * @descrption:
 */
public class LinitlyUtil {

    public static void setJwt() {
        HttpServletRequest request = SpringBeanUtil.getRequest();
        setJwt(request);
    }

    public static void setJwt(HttpServletRequest request) {
        request.setAttribute(GlobalConstant.JWT_ATTRIBUTE, getJwt(request));
    }

    public static AbstractJwtUtil getJwt() {
        return (AbstractJwtUtil) SpringBeanUtil.getRequest().getAttribute(GlobalConstant.JWT_ATTRIBUTE);
    }

    private static AbstractJwtUtil getJwt(HttpServletRequest request) {
        return JwtUtilFactory.getJwtUtil(request);
    }

    public static AbstractAuth getAuth() {
        return getJwt().getAbstractAuth();
    }

    public static String generateToken(BaseEntity entity) {
        return getJwt().generateToken(entity);
    }

    public static String generateRefreshToken(BaseEntity entity) {
        return getJwt().generateRefreshToken(entity);
    }

    public static String getRequestToken() {
        return getJwt().getToken(SpringBeanUtil.getRequest());
    }

    public static String getRequestRefreshToken() {
        return getJwt().getRefreshToken(SpringBeanUtil.getRequest());
    }

    public static Long getCurrentUserId() {
        return Long.valueOf(getJwt().getUserId(SpringBeanUtil.getRequest()));
    }

    public static void interceptorValid() {
        getJwt().interceptorValid(SpringBeanUtil.getRequest());
    }

    public static String getCacheToken(Long userId) {
        return getAuth().getToken(userId.toString());
    }

    public static String getCurrentCacheToken() {
        return getCacheToken(getCurrentUserId());
    }

    public static String getCacheRefreshToken(Long userId) {
        return getAuth().getRefreshToken(userId.toString());
    }

    public static String getCurrentCacheRefreshToken() {
        return getCacheRefreshToken(getCurrentUserId());
    }

    public static Set getCacheRoles(Long userId) {
        return getAuth().getRoles(userId.toString());
    }

    public static Set getCurrentCacheRoles() {
        return getCacheRoles(getCurrentUserId());
    }

    public static Set getCacheDepts(Long userId) {
        return getAuth().getDepts(userId.toString());
    }

    public static Set getCurrentCacheDepts() {
        return getCacheDepts(getCurrentUserId());
    }

    public static Set getCachePosts(Long userId) {
        return getAuth().getPosts(userId.toString());
    }

    public static Set getCurrentCachePosts() {
        return getCachePosts(getCurrentUserId());
    }

    public static Set getCacheFunctionPermissions(Long userId) {
        return getAuth().getFunctionPermissions(userId.toString());
    }

    public static Set getCurrentCacheFunctionPermissions() {
        return getCacheFunctionPermissions(getCurrentUserId());
    }

    public static void loginCacheSet(Long userId, String token, String refreshToken, Set deptIds, Set postIds, Set roles, Set functionPermissions) {
        getAuth().loginRedisSet(userId.toString(), token, refreshToken, deptIds, postIds, roles, functionPermissions);
    }

    public static void logoutCacheDel(Long userId) {
        getAuth().logoutRedisDel(userId.toString());
    }

    public static void updateCacheRoles(Long userId, Set roles) {
        getAuth().updateRoles(userId.toString(), roles);
    }

    public static void updateCacheDepts(Long userId, Set deptIds) {
        getAuth().updateDepts(userId.toString(), deptIds);
    }

    public static void updateCachePosts(Long userId, Set postIds) {
        getAuth().updatePosts(userId.toString(), postIds);
    }

    public static void updateCacheFunctionPermissions(Long userId, Set functionPermissions) {
        getAuth().updateFunctionPermissions(userId.toString(), functionPermissions);
    }
}
