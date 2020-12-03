package org.linitly.boot.base.annotation;

import java.lang.annotation.*;

/**
 * @author linxiunan
 * @date 2019/8/27 10:57
 * @description 记录日志时忽略的字段和方法
 */
@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogIgnore {
}
