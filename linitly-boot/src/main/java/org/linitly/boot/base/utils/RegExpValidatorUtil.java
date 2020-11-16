package org.linitly.boot.base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpValidatorUtil {

    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
