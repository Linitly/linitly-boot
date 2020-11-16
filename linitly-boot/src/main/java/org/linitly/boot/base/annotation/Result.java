package org.linitly.boot.base.annotation;

import java.lang.annotation.*;

/**
 * @author linxiunan
 * @date 16:16 2020/11/16
 * @description 返回对象封装注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Result {
}
