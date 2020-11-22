package org.linitly.boot.base.utils;

import org.linitly.boot.base.enums.DateFormat;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author linxiunan
 * @Description 时间处理工具类
 * @date 2018年9月3日
 */
public class IDateUtil {

//    public static final SimpleDateFormat noBarDateFormat = new SimpleDateFormat("yyyyMMdd");
//
//    public static final SimpleDateFormat dayOfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//    public static final SimpleDateFormat secondOfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static LocalDateTime addOneDayToLDT() {
        return LocalDateTime.now().plusDays(1L);
    }

    private static LocalDateTime addOneDayToLDT(Date date) {
        return convertDateToLDT(date).plusDays(1L);
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 14:57
     * @description 将当前时间加上一天，返回
     */
    public static Date addOneDay() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, 1);
//        return calendar.getTime();
        return convertLDTToDate(addOneDayToLDT());
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 14:57
     * @description 将传入时间加上一天，返回
     */
    public static Date addOneDay(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DATE, 1);
//        return calendar.getTime();
        return convertLDTToDate(addOneDayToLDT(date));
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 14:57
     * @description 将当前时间加上一天，根据传入的格式返回格式化后的日期
     */
    @Deprecated
    public static String addOneDay(SimpleDateFormat format) {
        Date date = addOneDay();
        return format.format(date);
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 14:57
     * @description 将当前时间加上一天，根据传入的格式返回格式化后的日期
     */
    public static String addOneDay(DateFormat dateFormat) {
        return formatLDT(addOneDayToLDT(), dateFormat);
    }

    /**
     * @author linxiunan
     * @date 9:51 2020/10/9
     * @description 将传入时间加上一天，根据传入的格式返回格式化后的日期
     */
    public static String addOneDay(Date date, DateFormat dateFormat) {
        return formatLDT(addOneDayToLDT(date), dateFormat);
    }

    /**
     * @return java.lang.String
     * @author linxiunan
     * @date 2019/8/22 14:59
     * @description 将传入时间加上一天，根据传入的格式返回格式化后的日期
     */
    @Deprecated
    public static String addOneDay(Date date, SimpleDateFormat format) {
        Date resultDate = addOneDay(date);
        return format.format(resultDate);
    }

/*****************************************************************************************************************************************************/


    private static LocalDateTime addOneMonthToLDT() {
        return LocalDateTime.now().plusMonths(1L);
    }

    private static LocalDateTime addOneMonthToLDT(Date date) {
        return convertDateToLDT(date).plusMonths(1L);
    }

    /**
     * @return java.lang.String
     * @author linxiunan
     * @date 2019/8/22 15:00
     * @description 将当前时间加上一个月，返回
     */
    public static Date addOneMonth() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH, 1);
//        return calendar.getTime();
        return convertLDTToDate(addOneMonthToLDT());
    }

    /**
     * @return java.lang.String
     * @author linxiunan
     * @date 2019/8/22 15:00
     * @description 将传入时间加上一个月，返回
     */
    public static Date addOneMonth(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.MONTH, 1);
//        return calendar.getTime();
        return convertLDTToDate(addOneMonthToLDT(date));
    }

    /**
     * @return java.lang.String
     * @author linxiunan
     * @date 2019/8/22 15:00
     * @description 将当前时间加上一个月，根据传入的格式返回格式化后的日期
     */
    @Deprecated
    public static String addOneMonth(SimpleDateFormat format) {
        Date resultDate = addOneMonth();
        return format.format(resultDate);
    }

    /**
     * @author linxiunan
     * @date 9:59 2020/10/9
     * @description 将当前时间加上一个月，根据传入的格式返回格式化后的日期
     */
    public static String addOneMonth(DateFormat dateFormat) {
        return formatLDT(addOneMonthToLDT(), dateFormat);
    }

    /**
     * @return java.lang.String
     * @author linxiunan
     * @date 2019/8/22 15:00
     * @description 将传入时间加上一个月，根据传入的格式返回格式化后的日期
     */
    @Deprecated
    public static String addOneMonth(Date date, SimpleDateFormat format) {
        Date resultDate = addOneMonth(date);
        return format.format(resultDate);
    }

    /**
     * @author linxiunan
     * @date 10:00 2020/10/9
     * @description 将传入时间加上一个月，根据传入的格式返回格式化后的日期
     */
    public static String addOneMonth(Date date, DateFormat dateFormat) {
        return formatLDT(addOneMonthToLDT(date), dateFormat);
    }

/*****************************************************************************************************************************************************/

    public static LocalDateTime cutOneMonthToLDT() {
        return LocalDateTime.now().minusMonths(1L);
    }

    public static LocalDateTime cutOneMonthToLDT(Date date) {
        return convertDateToLDT(date).minusMonths(1L);
    }

    /**
     * @return java.lang.String
     * @author linxiunan
     * @date 2019/8/22 15:23
     * @description 将当前时间减一个月，返回
     */
    public static Date cutOneMonth() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH, -1);
//        return calendar.getTime();
        return convertLDTToDate(cutOneMonthToLDT());
    }

    /**
     * @return java.lang.String
     * @author linxiunan
     * @date 2019/8/22 15:23
     * @description 将传入时间减一个月，返回
     */
    public static Date cutOneMonth(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.MONTH, -1);
//        return calendar.getTime();
        return convertLDTToDate(cutOneMonthToLDT(date));
    }

    /**
     * @return java.lang.String
     * @author linxiunan
     * @date 2019/8/22 15:23
     * @description 将当前时间减一个月，根据传入的格式返回格式化后的日期
     */
    @Deprecated
    public static String cutOneMonth(SimpleDateFormat format) {
        Date resultDate = cutOneMonth();
        return format.format(resultDate);
    }

    /**
     * @author linxiunan
     * @date 10:13 2020/10/9
     * @description 将当前时间减一个月，根据传入的格式返回格式化后的日期
     */
    public static String cutOneMonth(DateFormat dateFormat) {
        return formatLDT(cutOneMonthToLDT(), dateFormat);
    }

    /**
     * @return java.lang.String
     * @author linxiunan
     * @date 2019/8/22 15:23
     * @description 将传入时间减一个月，根据传入的格式返回格式化后的日期
     */
    @Deprecated
    public static String cutOneMonth(Date date, SimpleDateFormat format) {
        Date resultDate = cutOneMonth(date);
        return format.format(resultDate);
    }

    /**
     * @author linxiunan
     * @date 10:14 2020/10/9
     * @description 将传入时间减一个月，根据传入的格式返回格式化后的日期
     */
    public static String cutOneMonth(Date date, DateFormat dateFormat) {
        return formatLDT(cutOneMonthToLDT(date), dateFormat);
    }

/*****************************************************************************************************************************************************/

    public static LocalDateTime addFewDaysToLDT(int dayNumbers) {
        return LocalDateTime.now().plusDays(dayNumbers);
    }

    public static LocalDateTime addFewDaysToLDT(Date date, int dayNumbers) {
        return convertDateToLDT(date).plusDays(dayNumbers);
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:53
     * @description 返回当前时间添加几天之后的时间
     */
    public static Date addFewDays(int dayNumber) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, dayNumber);
//        return calendar.getTime();
        return convertLDTToDate(addFewDaysToLDT(dayNumber));
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:53
     * @description 返回传入时间添加几天之后的时间
     */
    public static Date addFewDays(Date date, int dayNumber) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DATE, dayNumber);
//        return calendar.getTime();
        return convertLDTToDate(addFewDaysToLDT(date, dayNumber));
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:53
     * @description 返回当天时间添加几天之后的时间，根据传入的格式返回格式化后的日期
     */
    @Deprecated
    public static String addFewDays(int dayNumber, SimpleDateFormat format) {
        Date date = addFewDays(dayNumber);
        return format.format(date);
    }

    /**
     * @author linxiunan
     * @date 10:19 2020/10/9
     * @description 返回当天时间添加几天之后的时间，根据传入的格式返回格式化后的日期
     */
    public static String addFewDays(DateFormat dateFormat, int dayNumber) {
       return formatLDT(addFewDaysToLDT(dayNumber), dateFormat);
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:53
     * @description 返回传入时间添加几天之后的时间，根据传入的格式返回格式化后的日期
     */
    @Deprecated
    public static String addFewDays(Date date, int dayNumber, SimpleDateFormat format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dayNumber);
        return format.format(calendar.getTime());
    }

    /**
     * @author linxiunan
     * @date 10:21 2020/10/9
     * @description 返回传入时间添加几天之后的时间，根据传入的格式返回格式化后的日期
     */
    public static String addFewDays(Date date, int dayNumber, DateFormat dateFormat) {
        return formatLDT(addFewDaysToLDT(date, dayNumber), dateFormat);
    }

/*****************************************************************************************************************************************************/

    /**
     * @return java.lang.String
     * @author linxiunan
     * @date 2019/8/22 15:59
     * @description 将传入的时间根据传入的格式转换为日期字符串
     */
    @Deprecated
    public static String dateToString(Date date, SimpleDateFormat format) {
        return format.format(date);
    }

    /**
     * @author linxiunan
     * @date 10:22 2020/10/9
     * @description 将传入的时间根据传入的格式转换为日期字符串
     */
    public static String dateToString(Date date, DateFormat dateFormat) {
        return formatLDT(convertDateToLDT(date), dateFormat);
    }

    /**
     * @return java.lang.String
     * @author linxiunan
     * @date 2019/8/22 15:59
     * @description 将传入的时间根据传入的格式转换为日期字符串
     */
    @Deprecated
    public static Date stringToDate(String dateString, SimpleDateFormat format) throws Exception {
        return format.parse(dateString);
    }

    /**
     * @author linxiunan
     * @date 10:25 2020/10/9
     * @description 将传入的时间根据传入的格式转换为日期字符串
     */
    public static Date stringToDate(String dateString, DateFormat dateFormat) {
        return convertLDTToDate(parseToLDT(dateString, dateFormat));
    }

/*****************************************************************************************************************************************************/

    public static LocalDateTime getZeroTimeLDT() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    public static LocalDateTime getZeroTimeLDT(Date date) {
        return LocalDateTime.of(convertDateToLDT(date).toLocalDate(), LocalTime.MIN);
    }

    public static LocalDateTime getZeroTimeLDT(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:02
     * @description 获取今天的零点时间
     */
    public static Date getZeroTime() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        return calendar.getTime();
        return convertLDTToDate(getZeroTimeLDT());
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:02
     * @description 获取某一天的零点时间
     */
    public static Date getZeroTime(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        Date returnDate = calendar.getTime();
//        return returnDate;
        return convertLDTToDate(getZeroTimeLDT(date));
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:02
     * @description 获取今天的零点时间，根据传入的格式返回格式化后的日期
     */
    @Deprecated
    public static String getZeroTime(SimpleDateFormat format) {
        Date returnDate = getZeroTime();
        return format.format(returnDate);
    }

    /**
     * @author linxiunan
     * @date 10:31 2020/10/9
     * @description 获取今天的零点时间，根据传入的格式返回格式化后的日期
     */
    public static String getZeroTime(DateFormat dateFormat) {
        return formatLDT(getZeroTimeLDT(), dateFormat);
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:02
     * @description 获取某一天的零点时间，根据传入的格式返回格式化后的日期
     */
    @Deprecated
    public static String getZeroTime(Date date, SimpleDateFormat format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date returnDate = calendar.getTime();
        return format.format(returnDate);
    }

    /**
     * @author linxiunan
     * @date 10:34 2020/10/9
     * @description 获取某一天的零点时间，根据传入的格式返回格式化后的日期
     */
    public static String getZeroTime(Date date, DateFormat dateFormat) {
        return formatLDT(getZeroTimeLDT(date), dateFormat);
    }

/*****************************************************************************************************************************************************/

    public static LocalDateTime getYesterdayLDT() {
        return LocalDateTime.of(LocalDate.now().minusDays(1L), LocalTime.MIN);
    }

    public static LocalDateTime getYesterdayLDT(Date date) {
        return LocalDateTime.of(convertDateToLDT(date).toLocalDate(), LocalTime.MIN);
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:03
     * @description 获取今天的前一天的零点时间
     */
    public static Date getYesterdayZeroTime() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, -1);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        return calendar.getTime();
        return convertLDTToDate(getZeroTimeLDT(getYesterdayLDT()));
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:03
     * @description 获取某一天的前一天的零点时间
     */
    public static Date getYesterdayZeroTime(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DATE, -1);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        return calendar.getTime();
        return convertLDTToDate(getZeroTimeLDT(getYesterdayLDT(date)));
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:03
     * @description 获取今天的前一天的零点时间，根据传入的格式格式时间
     */
    @Deprecated
    public static String getYesterdayZeroTime(SimpleDateFormat format) {
        Date returnDate = getYesterdayZeroTime();
        return format.format(returnDate);
    }

    /**
     * @author linxiunan
     * @date 10:41 2020/10/9
     * @description 获取今天的前一天的零点时间，根据传入的格式格式时间
     */
    public static String getYesterdayZeroTime(DateFormat dateFormat) {
        return formatLDT(getZeroTimeLDT(getYesterdayLDT()), dateFormat);
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:03
     * @description 获取某一天的前一天的零点时间，根据传入的格式格式时间
     */
    @Deprecated
    public static String getYesterdayZeroTime(Date date, SimpleDateFormat format) {
        Date returnDate = getYesterdayZeroTime(date);
        return format.format(returnDate);
    }

    /**
     * @author linxiunan
     * @date 10:46 2020/10/9
     * @description 获取某一天的前一天的零点时间，根据传入的格式格式时间
     */
    public static String getYesterdayZeroTime(Date date, DateFormat dateFormat) {
        return formatLDT(getZeroTimeLDT(getYesterdayLDT(date)), dateFormat);
    }

/*****************************************************************************************************************************************************/

    public static LocalDateTime getTomorrowLDT() {
        return LocalDateTime.of(LocalDate.now().plusDays(1L), LocalTime.MIN);
    }

    public static LocalDateTime getTomorrowLDT(Date date) {
        return LocalDateTime.of(convertDateToLDT(date).toLocalDate().plusDays(1L), LocalTime.MIN);
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:57
     * @description 获取今天的后一天的零点时间
     */
    public static Date getTomorrowZeroTime() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, 1);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        return calendar.getTime();
        return convertLDTToDate(getZeroTimeLDT(getTomorrowLDT()));
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:57
     * @description 获取某一天的后一天的零点时间
     */
    public static Date getTomorrowZeroTime(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DATE, 1);
//        calendar.set(Calendar.HOUR, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        Date returnDate = calendar.getTime();
//        return returnDate;
        return convertLDTToDate(getZeroTimeLDT(getTomorrowLDT(date)));
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:57
     * @description 获取今天的后一天的零点时间
     */
    @Deprecated
    public static String getTomorrowZeroTime(SimpleDateFormat format) {
        Date returnDate = getTomorrowZeroTime();
        return format.format(returnDate);
    }

    /**
     * @author linxiunan
     * @date 10:56 2020/10/9
     * @description 获取今天的后一天的零点时间
     */
    public static String getTomorrowZeroTime(DateFormat dateFormat) {
        return formatLDT(getZeroTimeLDT(getTomorrowLDT()), dateFormat);
    }

    /**
     * @return java.util.Date
     * @author linxiunan
     * @date 2019/8/22 16:57
     * @description 获取某一天的后一天的零点时间
     */
    @Deprecated
    public static String getTomorrowZeroTime(Date date, SimpleDateFormat format) {
        Date returnDate = getTomorrowZeroTime(date);
        return format.format(returnDate);
    }

    /**
     * @author linxiunan
     * @date 10:59 2020/10/9
     * @description 获取某一天的后一天的零点时间
     */
    public static String getTomorrowZeroTime(Date date, DateFormat dateFormat) {
        return formatLDT(getZeroTimeLDT(getTomorrowLDT(date)), dateFormat);
    }

/*****************************************************************************************************************************************************/

    /**
     * 计算两次时间的间隔，单位为秒
     */
//    @Deprecated
//    public static long compareDateSecond(Date d1, Date d2) {
//        return Math.abs((d1.getTime() - d2.getTime()) / 1000);
//    }

    /**
     * @author linxiunan
     * @date 11:02 2020/10/9
     * @description 计算两次时间的间隔，单位为秒
     */
    public static long compareDateSecond(Date d1, Date d2) {
        return Duration.between(convertDateToLDT(d1), convertDateToLDT(d2)).toMillis();
    }

    /**
     * @author linxiunan
     * @date 11:50 2020/10/9
     * @description 计算两次时间的间隔，单位天
     */
    public static long compareDateDay(Date d1, Date d2) {
//        Calendar c1 = Calendar.getInstance();
//        c1.setTime(d1);
//        Calendar c2 = Calendar.getInstance();
//        c2.setTime(d2);
//        return Math.abs(c1.get(Calendar.DAY_OF_YEAR) - c2.get(Calendar.DAY_OF_YEAR));
        return Duration.between(convertDateToLDT(d1), convertDateToLDT(d2)).toDays();
    }

    /**
     * @return java.lang.Long
     * @author linxiunan
     * @date 2019/8/22 16:00
     * @description 计算现在到第二天零点的秒数
     */
    public static long getSecondsNextEarlyMorning() {
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, 1);
//        cal.set(Calendar.HOUR, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        return Duration.between(LocalDateTime.now(), getZeroTimeLDT(getTomorrowLDT())).toMillis();
    }

/*****************************************************************************************************************************************************/

    /**
     * @return java.util.List<java.util.Date>
     * @author linxiunan
     * @date 2019/8/20 20:10
     * @description 获取两个时间之间的所有时间(每天的零点)
     */
//    @Deprecated
//    public static List<Date> getBetweenDays(Date startTime, Date endTime) {
//        List<Date> betweenTime = new ArrayList<>();
//        Calendar tempStart = Calendar.getInstance();
//        tempStart.setTime(startTime);
//
//        Calendar tempEnd = Calendar.getInstance();
//        tempEnd.setTime(endTime);
//        tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
//        while (tempStart.before(tempEnd)) {
//            betweenTime.add(getZeroTime(tempStart.getTime()));
//            tempStart.add(Calendar.DAY_OF_YEAR, 1);
//        }
//        return betweenTime;
//    }

    /**
     * @author linxiunan
     * @date 11:55 2020/10/9
     * @description 获取两个时间之间的所有时间零点
     */
    public static List<Date> getBetweenDays(Date startTime, Date endTime) {
        // 转换Date为LocalDate类型
        LocalDate startDate = convertDateToLDT(startTime).toLocalDate();
        LocalDate endDate = convertDateToLDT(endTime).toLocalDate();

        List<Date> betweenTime = new ArrayList<>();
        long num = endDate.toEpochDay() - startDate.toEpochDay();
        for (int i = 0; i < num; i++) {
            betweenTime.add(convertLDTToDate(LocalDateTime.of(startDate.plusDays(i), LocalTime.MIN)));
        }
        return betweenTime;
    }

    /**
     * @return java.util.List<java.util.Date>
     * @author linxiunan
     * @date 2019/8/20 20:10
     * @description 获取两个时间之间的所有时间(yyyy - MM - dd格式的时间字符串)
     */
//    @Deprecated
//    public static List<String> getBetweenDays(String startTime, String endTime) {
//        List<String> days = new ArrayList<>();
//        try {
//            Date start = dayOfDateFormat.parse(startTime);
//            Date end = dayOfDateFormat.parse(endTime);
//
//            Calendar tempStart = Calendar.getInstance();
//            tempStart.setTime(start);
//
//            Calendar tempEnd = Calendar.getInstance();
//            tempEnd.setTime(end);
//            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
//            while (tempStart.before(tempEnd)) {
//                days.add(dayOfDateFormat.format(tempStart.getTime()));
//                tempStart.add(Calendar.DAY_OF_YEAR, 1);
//            }
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return days;
//    }

    /**
     * @author linxiunan
     * @date 13:26 2020/10/9
     * @description 获取两个时间之间的所有时间(yyyy - MM - dd格式的时间字符串)
     */
    public static List<String> getBetweenDays(String startTime, String endTime) {
        LocalDate startDate = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern(DateFormat.DASH_D.getFormat())).toLocalDate();
        LocalDate endDate = LocalDateTime.parse(endTime, DateTimeFormatter.ofPattern(DateFormat.DASH_D.getFormat())).toLocalDate();

        List<String> betweenTime = new ArrayList<>();
        long num = endDate.toEpochDay() - startDate.toEpochDay();
        for (int i = 0; i < num; i++) {
            betweenTime.add(formatLDT(LocalDateTime.of(startDate.plusDays(i), LocalTime.MIN), DateFormat.DASH_D));
        }
        return betweenTime;
    }

/*****************************************************************************************************************************************************/

    /**
     * @author linxiunan
     * @date 9:41 2020/10/9
     * @description Date转换为LocalDateTime
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * @author linxiunan
     * @date 9:40 2020/10/9
     * @description LocalDateTime转换为Date
     */
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @author linxiunan
     * @date 11:49 2020/10/9
     * @description 将传入的LocalDateTime格式化为传入的DateFormat格式
     */
    public static String formatLDT(LocalDateTime localDateTime, DateFormat dateFormat) {
        return localDateTime.format(DateTimeFormatter.ofPattern(dateFormat.getFormat()));
    }

    /**
     * @author linxiunan
     * @date 14:22 2020/10/9
     * @description 将传入的日期字符串根据传入的格式转换为LocalDateTime格式
     */
    public static LocalDateTime parseToLDT(String dateString, DateFormat dateFormat) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(dateFormat.getFormat()));
    }

    public static Long getNowMilli() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
