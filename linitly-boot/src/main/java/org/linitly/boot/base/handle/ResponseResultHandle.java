package org.linitly.boot.base.handle;

import lombok.extern.slf4j.Slf4j;
import org.linitly.boot.base.annotation.Pagination;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.helper.entity.ResponseResult;
import org.linitly.boot.base.utils.page.ResponseResultUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * @author: linxiunan
 * @date: 2020/11/16 16:52
 * @descrption: 统一返回对象处理
 */
@Slf4j
@ControllerAdvice
public class ResponseResultHandle implements ResponseBodyAdvice {

    /**
     * @author linxiunan
     * @date 16:53 2020/11/16
     * @description 判断是否要执行beforeBodyWrite方法，true为执行，false不执行
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (methodParameter.getMethod() == null) return false;
        return methodParameter.getDeclaringClass().isAnnotationPresent(Result.class) || methodParameter.getMethod().isAnnotationPresent(Result.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest request, ServerHttpResponse response) {
        if (methodParameter.getMethod() == null) return null;
        if (o instanceof ResponseResult) return o;
        if (methodParameter.getMethodAnnotation(Pagination.class) != null && o instanceof List) return ResponseResultUtil.getResponseResult((List) o);
        return new ResponseResult<>(o);
    }
}
