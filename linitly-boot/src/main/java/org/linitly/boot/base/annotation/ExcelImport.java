package org.linitly.boot.base.annotation;

import org.linitly.boot.base.enums.SheetReadType;

import java.lang.annotation.*;

/**
 * @author: linxiunan
 * @date: 2020/5/8 10:25
 * @descrption: excel读取类注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelImport {

    /**
     * sheet读取的方式，分为只读第一个sheet，读取全部sheet，自定义起始sheet三种
     */
    SheetReadType sheetReadType() default SheetReadType.FIRST;

    /**
     * 默认开始读取的sheet，默认从第一个sheet开始读取
     */
    int startReadSheet() default 0;

    /**
     * 默认结束读取的sheet，默认从第一个sheet结束读取
     */
    int endReadSheet() default 0;

    String name() default "";

    /**
     * 默认读取的起始行，默认从第二行开始读取
     */
    int startReadRow() default 1;

    /**
     * 默认读取的起始列，默认从第一列开始读取
     */
    int startReadCell() default 0;

    /**
     * 单次处理最大行数，默认1000
     */
    int maxReadRow() default 1000;
}
