package org.linitly.boot.base.annotation;

import java.lang.annotation.*;

/**
 * @author linxiunan
 * @date 14:28 2020/12/4
 * @description 字典注解
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {

    String code() default "";
}
