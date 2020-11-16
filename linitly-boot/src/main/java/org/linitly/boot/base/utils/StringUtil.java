package org.linitly.boot.base.utils;

import org.apache.commons.lang3.StringUtils;
import org.linitly.boot.base.constant.global.GlobalConstant;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 字符串工具类
 * @author siwei
 * @date 2018年9月14日
 */
public class StringUtil {

	/**
	 * 计算字符串的字节数
	 * @param value
	 * @return
	 */
	public static int getLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}
	
	/**
     * 将字符（保留前3位，保留后四位，中间替换为 *）用于身份证或者电话、银行卡号等敏感信息显示
     * @param str
     * @return
     */
	public static String replaceSensitiveStr(String str) {
        if (StringUtils.isBlank(str) || str.length() < 11){
            return str;
        }
        //这里*只要一个，因为会替代多次，每次一个。
        str = str.replaceAll("(?<=[\\d]{3})\\d(?=([\\d]|[a-z]|[A-Z]){4})", "*");
        return str;
    }
	
	/**
     * 验证是否为正确的邮箱号
     *
     * @param email 需要验证的邮箱地址
     * @return
     */
    public static boolean isValidEmail(String email) {
        // 1、\\w+表示@之前至少要输入一个匹配字母或数字或下划线 \\w 单词字符：[a-zA-Z_0-9]
        // 2、(\\w+\\.)表示域名. 如新浪邮箱域名是sina.com.cn
        // {1,3}表示可以出现一次或两次或者三次.
        String reg = "\\w+@(\\w+\\.){1,3}\\w+";
        Pattern pattern = Pattern.compile(reg);
        boolean flag = false;
        if (email != null) {
            Matcher matcher = pattern.matcher(email);
            flag = matcher.matches();
        }
        return flag;
    }
    
    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     * @author ：shijing
     * 2016年12月5日下午4:34:46
     */
    public static boolean isMobile(final String str) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile(GlobalConstant.MOBILE_NUMBER_REG); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    
    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     * @author ：shijing
     * 2016年12月5日下午4:34:21
     */
    public static boolean isPhone(final String str) {
        Pattern p1, p2;
        Matcher m;
        boolean b;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }
    
    /**
     * 获取文件名称后缀
     *
     * @param fileName 文件名称
     * @return 后缀
     */
    public static String getFileSuffix(String fileName) {
        if (StringUtils.isBlank(fileName) || !fileName.contains(".")) {
            return null;
        }
        return fileName.substring(fileName.indexOf(".") + 1, fileName.length());
    }
    
    /**
     * 因为系统内部存在字符串拼接SQL语句，并未使用占位符，所以需手动过滤特殊字符防止SQL注入
     *
     * @param str
     * @return
     */
    public static String transactSQLInjection(String str) {
        return str.replaceAll("([';])+|(--)+", "");
    }
    
    /**
     * 替换空格、换行符等不可见字符
     *
     * @param str
     * @return
     */
    public static String replaceSpace(String str) {
        String result = str;
        if (str != null && str.length() > 0) { // 替换空格、换行符等不可见字符
            String regEx = "\\s|　{1,}";
            Pattern pattern = Pattern.compile(regEx);
            Matcher m = pattern.matcher(str);

            result = m.replaceAll("").trim();
        }
        return result;
    }
    
    /**
     * 验证某个字符是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        // 该正则表达式可以匹配所有的数字 包括负数
        String regex = "-?[0-9]+\\.?[0-9]*";
        Pattern pattern = Pattern.compile(regex);
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    
    /**
     * 多个逗号转换为单个逗号 并去掉首尾逗号
     *
     * @param s
     * @return
     */
    public static String dealWithComma(String s) {
        if (s.length() > 0) {
            s = s.replaceAll("[',']+", ",");// 多个,,,,替换为,
            // 去掉首尾,
            if (s.startsWith(",")) {
                s = s.substring(1);
            }
            if (s.endsWith(",")) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }

    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    public static String removeHtmlTags(String s) {
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        String regEx_space = "[|\\s*|\t|\r|\n|\\\\n]+"; // 定义tab、空格、空行

        if (s == null) {
            return null;
        } else {
            return s.replaceAll(regEx_html, "")
                    .replaceAll(regEx_space, "").replaceAll("", "");
        }
    }

    /**
     * 判断是否包含中文字
     * @param s
     * @return
     */
    public static boolean isHaseChinese(String s){
        if (StringUtils.isBlank(s)){
            return false;
        }
        String reg = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(reg);
        Matcher mat = pat.matcher(s);
        if (mat.find()) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断是否纯中文字
     * @param s
     * @return
     */
    public static boolean isChinese(String s){
        if (StringUtils.isBlank(s)){
            return false;
        }
        int n = 0;
        for(int i = 0; i < s.length(); i++) {
            n = (int)s.charAt(i);
            if(!(19968 <= n && n <40869)) {
                return false;
            }
        }
        return true;
    }
}
