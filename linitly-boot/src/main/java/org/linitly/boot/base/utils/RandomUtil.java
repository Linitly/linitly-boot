package org.linitly.boot.base.utils;

import java.util.Random;

public class RandomUtil {

    public static String randomInt(int length) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    public static String random6Int() {
        return randomInt(6);
    }
}
