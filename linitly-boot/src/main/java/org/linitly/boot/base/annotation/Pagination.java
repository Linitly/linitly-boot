package org.linitly.boot.base.annotation;

import java.lang.annotation.*;

/**
 * @author: linxiunan
 * @date: 2020/11/16 20:45
 * @descrption: 需要返回时获取分布信息注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Pagination {
}
