package org.linitly.boot.base.annotation;


import org.linitly.boot.base.enums.Logical;

import java.lang.annotation.*;

/**
 * @Description 所需角色注解
 * @author linxiunan
 * @date 2018年10月23日
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {

	String[] value();

	Logical logical() default Logical.AND;
}
