package org.linitly.boot.base.utils.bean;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: linxiunan
 * @date: 2020/11/30 10:26
 * @descrption:
 */
public class SpringBeanUtil {

    public static ConfigurableApplicationContext applicationContext;

    public static <T> T getBean(Class<T> c) {
        return applicationContext.getBean(c);
    }

    public static <T> T getBean(String className, Class<T> c) {
        return applicationContext.getBean(className, c);
    }

    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
