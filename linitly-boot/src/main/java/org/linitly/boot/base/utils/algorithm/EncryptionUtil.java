package org.linitly.boot.base.utils.algorithm;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

/**
 * @author linxiunan
 * @date 10:08 2020/11/23
 * @description 加密工具类
 */
public class EncryptionUtil {

    /**
     * 对字符串进行md5加密
     *
     * @param str
     * @return
     */
    public static String md5(String str, String salt) {
        if (StringUtils.isNotBlank(salt)) str = str + salt;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return byteToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对字符串进行sha256加密
     *
     * @param str
     * @return
     */
    public static String sha256(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            return byteToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对字符串进行sha1加密
     *
     * @param str
     * @return
     */
    public static String sha1(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            return byteToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字节数组转16进制字符串
     */
    public static String byteToHex(byte[] data) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : data) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    /**
     * hex字符串转字节数组
     */
    public static byte[] hexToByteArray(String data) {
        int hexlen = data.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            data = "0" + data;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(data.substring(i, i + 2));
            j++;
        }
        return result;
    }

    public static byte hexToByte(String data) {
        return (byte) Integer.parseInt(data, 16);
    }
}
