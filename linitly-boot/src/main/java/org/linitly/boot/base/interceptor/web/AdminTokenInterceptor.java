package org.linitly.boot.base.interceptor.web;

import lombok.extern.slf4j.Slf4j;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.utils.jwt.AbstractJwtUtil;
import org.linitly.boot.base.utils.jwt.JwtUtilFactory;
import org.linitly.boot.base.utils.permission.AntPathMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linxiunan
 * @date 17:41 2020/5/6
 * @description 后台请求token验证
 */
@Slf4j
@Component
public class AdminTokenInterceptor implements HandlerInterceptor {

    private static final List<String> UN_CHECK_URIS = new ArrayList<>();

    static {
        // 登录接口
        UN_CHECK_URIS.add(AdminCommonConstant.URL_PREFIX + "/login");

        // 退出接口
        UN_CHECK_URIS.add(AdminCommonConstant.URL_PREFIX + "/logout");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String uri = request.getServletPath();
        for (String unCheckUri : UN_CHECK_URIS) {
            if (AntPathMatcher.getInstance().matches(unCheckUri, uri))
                return true;
        }

        AbstractJwtUtil jwtUtil = JwtUtilFactory.getJwtUtil(request);
        jwtUtil.interceptorValid(request);
        return true;
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
}
