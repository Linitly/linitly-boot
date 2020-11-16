package org.linitly.boot.base.handle;

import org.linitly.boot.base.annotation.ResponseResult;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: linxiunan
 * @date: 2020/11/16 17:19
 * @descrption:
 */
@Component
@Order(Integer.MAX_VALUE - 1)
public class ResponseResultAware implements ApplicationContextAware, CommandLineRunner {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);

        List<Method> methods = new ArrayList<>();

        controllers.forEach((k, v) -> dealClass(v.getClass(), methods));

        methods.forEach(e -> dealMethod(e));
    }

    private void dealClass(Class<?> controllerClass, List<Method> methods) {
        if (controllerClass.isAnnotationPresent(ResponseResult.class)) {
            methods.addAll(Arrays.asList(controllerClass.getMethods()));
        } else {
            for (Method method : controllerClass.getMethods()) {
                if (method.isAnnotationPresent(ResponseResult.class)) {
                    methods.add(method);
                }
            }
        }
    }

    /**
     * @author linxiunan
     * @date 17:35 2020/11/16
     * @description 处理单个method
     */
    private void dealMethod(Method method) {
        Class<?> returnType = method.getReturnType();
    }
}
