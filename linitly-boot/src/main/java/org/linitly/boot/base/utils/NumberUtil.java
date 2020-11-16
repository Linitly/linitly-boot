package org.linitly.boot.base.utils;

import java.math.BigDecimal;

/**
 * @author siwei
 * @Description 数字工具类
 * @date 2018年8月6日
 */
public class NumberUtil {

    /**
     * @param number
     * @return
     */
    public static int stringToInt(String number) {
        double doubleNumber = Double.parseDouble(number);
        return Integer.parseInt(new java.text.DecimalFormat("0").format(doubleNumber));
    }

    /**
     * double值相加
     * @param d1
     * @param d2
     * @return
     */
    public static double sum(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue();
    }

    /**
     * 将string类型数据保留newScale位数四舍五入，返回BigDecimal类型数据
     */
    public static BigDecimal stringFormat2BigDecimal(int newScale, String value) {
        return new BigDecimal(value).setScale(newScale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 将string类型数据保留newScale位数四舍五入，返回BigDecimal类型数据
     */
    public static String stringFormat2String(int newScale, String value) {
        return String.valueOf(stringFormat2BigDecimal(newScale, value));
    }

    /**
     * 将double类型数据保留newScale位数四舍五入，返回String类型数据
     */
    public static String doubleFormat2String(int newScale, double value) {
        return String.valueOf(doubleFormat2BigDecimal(newScale, value));
    }

    /**
     * 将double类型数据保留newScale位数四舍五入，返回BigDecimal类型数据
     */
    public static BigDecimal doubleFormat2BigDecimal(int newScale, double value) {
        return new BigDecimal(value).setScale(newScale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 将double类型数据保留newScale位数四舍五入，返回Double类型数据
     */
    public static Double doubleFormat2Double(int newScale, double value) {
        return doubleFormat2BigDecimal(newScale, value).doubleValue();
    }

    /**
     * 计算两个值相除，求百分比，保留两位小数，四舍五入，返回double
     */
    public static double percentage(Integer a, Integer b) {
        if (b == 0) {
            return 0d;
        } else {
            return (new BigDecimal((float) a / b * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
    }
}
