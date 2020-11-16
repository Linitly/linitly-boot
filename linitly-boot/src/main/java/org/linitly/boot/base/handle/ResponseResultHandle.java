package org.linitly.boot.base.handle;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author: linxiunan
 * @date: 2020/11/16 16:52
 * @descrption: 统一返回对象处理
 */
public class ResponseResultHandle implements ResponseBodyAdvice {

    /**
     * @author linxiunan
     * @date 16:53 2020/11/16
     * @description  判断是否要执行beforeBodyWrite方法，true为执行，false不执行
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {

        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest request, ServerHttpResponse response) {

        return null;
    }
}
