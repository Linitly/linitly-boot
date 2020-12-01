package org.linitly.boot.base.interceptor.web;

import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.constant.global.GlobalConstant;
import org.linitly.boot.base.enums.ResultEnum;
import org.linitly.boot.base.enums.SystemEnum;
import org.linitly.boot.base.exception.CommonException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: linxiunan
 * @date: 2020/12/1 10:58
 * @descrption:
 */
@Component
public class SystemInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String systemCode = request.getHeader(GlobalConstant.SYSTEM_CODE_KEY);
        if (StringUtils.isBlank(systemCode)) throw new CommonException(ResultEnum.SYSTEM_CODE_EMPTY);
        boolean trueCode = false;
        SystemEnum[] systemEnums = SystemEnum.class.getEnumConstants();
        for (SystemEnum systemEnum : systemEnums) {
            if (systemEnum.getSystemCode().equals(Integer.valueOf(systemCode))) trueCode = true;
        }
        if (trueCode) return true;
        throw new CommonException(ResultEnum.SYSTEM_CODE_ERROR);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
