package org.linitly.boot.base.annotation;

import org.linitly.boot.base.enums.DateFormat;

import java.lang.annotation.*;

/**
 * @author: linxiunan
 * @date: 2020/5/8 15:39
 * @descrption: excel属性读取注解
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelProperty {

    String name() default "";

    /**
     * 导入时，对应的列数，-1为默认顺序，暂时不实现按列数来读取
     */
    int cell() default -1;

    /**
     * 导入时，允许空值，默认为true
     */
    boolean blank() default true;

    /**
     * 导入时，小数保留的位数，默认为-1，即不做处理
     */
    int importDecimal() default -1;

    /**
     * 导入时日期格式format格式
     */
    DateFormat importFormat() default DateFormat.NONE;

    /**
     * 导入时转义，输入json字符串，目前仅支持integer和boolean的转义，例：{"男" : 1, "女" : 2}，{"男" : true, "女" : false}
     */
    String importEscape() default "";

    /**
     * 是否导出此字段，默认为false
     */
    boolean isExport() default false;

    /**
     * 导出时，小数保留的位数，默认为-1，即不做处理
     */
    int exportDecimal() default -1;

    /**
     * 导出时日期格式format格式
     */
    DateFormat exportFormat() default DateFormat.DASH_D;

    /**
     * 导出时转义，输入json字符串，目前仅支持integer和boolean的转义，例：{"1" : "男", "2" : "女"}，{"true" : "男", "false" : 女}
     */
    String exportEscape() default "";
}
