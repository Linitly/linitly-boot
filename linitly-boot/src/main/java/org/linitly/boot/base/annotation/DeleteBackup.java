package org.linitly.boot.base.annotation;

import java.lang.annotation.*;

/**
 * @author linxiunan
 * @date 10:11 2020/12/3
 * @description 删除数据备份注解
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DeleteBackup {
}
