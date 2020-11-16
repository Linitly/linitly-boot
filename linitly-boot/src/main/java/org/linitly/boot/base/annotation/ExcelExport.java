package org.linitly.boot.base.annotation;

import org.linitly.boot.base.enums.ExcelType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.lang.annotation.*;

/**
 * @author: linxiunan
 * @date: 2020/5/8 10:25
 * @descrption: excel导出类注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelExport {

    String name() default "";

    /**
     * 导出excel时表格名字
     */
    String exportName() default "";

    /**
     * 导出excel类型，默认07版
     */
    ExcelType excelType() default ExcelType.XLSX;

    /**
     * 导出sheet名字
     */
    String sheetName() default "";

    /**
     * 导出时单元格的宽度
     */
    int width() default 35 * 60;

    /**
     * 导出时表头row的高度
     */
    int headHeight() default 15 * 20;

    /**
     * 导出时内容row的高度
     */
    int cellHeight() default 15 * 20;

    /**
     * 表头内容
     */
    String[] headers();

    /**
     * 表头背景色
     */
    short headBackGroundColor() default 9;

    /**
     * 表格背景色
     */
    short cellBackGroundColor() default 9;

    /**
     * 表头字体
     */
    String headFontName() default "宋体";

    /**
     * 表格字体
     */
    String cellFontName() default "宋体";

    /**
     * 表头字体大小
     */
    short headFontSize() default 12;

    /**
     * 表格字体大小
     */
    short cellFontSize() default 12;

    /**
     * 表头字体加粗
     */
    boolean headBold() default false;

    /**
     * 表格字体加粗
     */
    boolean cellBold() default false;

    /**
     * 表头字体颜色
     */
    short headFontColor() default 8;

    /**
     * 表格字体颜色
     */
    short cellFontColor() default 8;

    /**
     * 表头左右对齐方式
     */
    HorizontalAlignment headAlignment() default HorizontalAlignment.LEFT;

    /**
     * 表格左右对齐方式
     */
    HorizontalAlignment cellAlignment() default HorizontalAlignment.LEFT;

    /**
     * 表头上下对齐方式
     */
    VerticalAlignment headVAlignment() default VerticalAlignment.BOTTOM;

    /**
     * 表格上下对齐方式
     */
    VerticalAlignment cellVAlignment() default VerticalAlignment.BOTTOM;

    // 表格样式，暂不实现
    // 背景色，字号，字体颜色，字体，加粗，左右居中格式，上下居中格式，均分为头部和内容两部分
}
