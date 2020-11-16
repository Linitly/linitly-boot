package org.linitly.boot.base.enums;

/**
 * @author: linxiunan
 * @date: 2020/5/9 10:07
 * @descrption: 日期格式化类型枚举
 */
public enum DateFormat {

    NONE(""),

    DASH_M("yyyy-MM"),
    DASH_D("yyyy-MM-dd"),
    DASH_H("yyyy-MM-dd HH"),
    DASH_MM("yyyy-MM-dd HH:mm"),
    DASH_S("yyyy-MM-dd HH:mm:ss"),

    COLON_SS("HH:mm:ss"),

    SLANT_M("yyyy/MM"),
    SLANT_D("yyyy/MM/dd"),
    SLANT_H("yyyy/MM/dd HH"),
    SLANT_MM("yyyy/MM/dd HH:mm"),
    SLANT_S("yyyy/MM/dd HH:mm:ss"),

    NO_BAR_M("yyyyMM"),
    NO_BAR_D("yyyyMMdd"),
    NO_BAR_H("yyyyMMddHH"),
    NO_BAR_MM("yyyyMMddHHmm"),
    NO_BAR_S("yyyyMMddHHmmss"),
    NO_BAR_SS("HHmmss"),

    CN_M("yyyy年MM月"),
    CN_D("yyyy年MM月dd日"),
    CN_H("yyyy年MM月dd日 HH时"),
    CN_MM("yyyy年MM月dd日 HH时mm分"),
    CN_S("yyyy年MM月dd日 HH时mm分ss秒"),
    CN_SS("HH时mm分ss秒");
    ;

    DateFormat(String format) {
        this.format = format;
    }

    private String format;

    public String getFormat() {
        return format;
    }
}
