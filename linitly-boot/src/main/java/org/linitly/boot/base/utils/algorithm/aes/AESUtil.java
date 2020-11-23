package org.linitly.boot.base.utils.algorithm.aes;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.linitly.boot.base.exception.DecryptException;
import org.linitly.boot.base.exception.EncryptException;
import org.linitly.boot.base.utils.algorithm.AlgorithmEnum;
import org.linitly.boot.base.utils.algorithm.EncryptionUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.text.MessageFormat;

public class AESUtil {

    public static String encrypt(AESParameter parameter, String content) {
        String encryptModel = parameter.getAlgorithm().getValue() + "/" + parameter.getModel().getValue() + "/" + parameter.getPadding().getValue();

        String encryptedContent = null;
        try {
            switch (parameter.getPadding()) {
                case NO_PADDING:
                    int len = content.getBytes(StandardCharsets.UTF_8).length;
                    int m = len % 16;
                    if (m != 0) {
                        for (int i = 0; i < 16 - m; i++) {
                            content += " ";
                        }
                    }
                    break;
                case ZERO_PADDING:
                    throw new IllegalArgumentException("Java目前不支持" + parameter.getPadding().getValue());
                case PKCS7_PADDING:
                    Security.addProvider(new BouncyCastleProvider());
                    break;
                default:
                    break;
            }
            SecretKeySpec keySpec = new SecretKeySpec(parameter.getKey().getBytes(), parameter.getAlgorithm().getValue());
            Cipher cipher = Cipher.getInstance(encryptModel);
            switch (parameter.getModel()) {
                case ECB:
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
                    break;
                default:
                    IvParameterSpec iv = new IvParameterSpec(parameter.getIvKey().getBytes(StandardCharsets.UTF_8));
                    cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
                    break;
            }
            byte[] byteEncode = content.getBytes(StandardCharsets.UTF_8);
            byteEncode = cipher.doFinal(byteEncode);
            switch (parameter.getOutputType()) {
                case HEX:
                    encryptedContent = EncryptionUtil.byteToHex(byteEncode);
                    break;
                case BASE64:
                    encryptedContent = new String(Base64.encodeBase64(byteEncode));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EncryptException(MessageFormat.format("AES加密失败：key = {0}, iv = {1}, content = {2}", parameter.getKey(), parameter.getIvKey(), content));
        }
        return encryptedContent;
    }

    public static String decrypt(AESParameter parameter, String content) {
        String encryptModel = parameter.getAlgorithm().getValue() + "/" + parameter.getModel().getValue() + "/" + parameter.getPadding().getValue();

        String decryptContent = null;
        try {
            switch (parameter.getPadding()) {
                case NO_PADDING:
                    int len = content.getBytes(StandardCharsets.UTF_8).length;
                    int m = len % 16;
                    if (m != 0) {
                        for (int i = 0; i < 16 - m; i++) {
                            content += " ";
                        }
                    }
                    break;
                case ZERO_PADDING:
                    throw new DecryptException("Java目前不支持" + parameter.getPadding().getValue());
                case PKCS7_PADDING:
                    Security.addProvider(new BouncyCastleProvider());
                    break;
                default:
                    break;
            }
            SecretKeySpec keySpec = new SecretKeySpec(parameter.getKey().getBytes(StandardCharsets.UTF_8), parameter.getAlgorithm().getValue());
            Cipher cipher = Cipher.getInstance(encryptModel);
            switch (parameter.getModel()) {
                case ECB:
                    cipher.init(Cipher.DECRYPT_MODE, keySpec);
                    break;
                default:
                    IvParameterSpec iv = new IvParameterSpec(parameter.getIvKey().getBytes(StandardCharsets.UTF_8));
                    cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
                    break;
            }
            byte[] byteEncode = null;
            switch (parameter.getOutputType()) {
                case HEX:
                    byteEncode = EncryptionUtil.hexToByteArray(content);
                    break;
                case BASE64:
                    byteEncode = Base64.decodeBase64(content);
                    break;
                default:
                    break;
            }
            byteEncode = cipher.doFinal(byteEncode);
            decryptContent = new String(byteEncode, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DecryptException(MessageFormat.format("AES解密失败：key = {0}, iv = {1}, content = {2}", parameter.getKey(), parameter.getIvKey(), content));
        }
        return decryptContent;
    }

    private static String[] getKeyAndIVKey(int keyLength) {
        String key = RandomStringUtils.random(keyLength, true, true);
        String ivKey = RandomStringUtils.random(16, true, true);
        return new String[]{key, ivKey};
    }

    /**
     * 测试AES加密算法
     */
    private static void testAESEncrypt(AESParameter parameter) {
        String originalStr = "这是一个没有固定长度的测试的字符串";

        String encryptContent = encrypt(parameter, originalStr);
        String decryptContent = decrypt(parameter, encryptContent);
        System.out.println("原字符串: " + originalStr);
        System.out.println("加密后字符串: " + encryptContent);
        System.out.println("解密后字符串: " + decryptContent);
    }

    public static void main(String[] args) {
        String[] keys = getKeyAndIVKey(16);

        AESParameter parameter = AESParameter.builder().algorithm(AlgorithmEnum.AES).key(keys[0]).ivKey(keys[1]).outputType(OutputType.BASE64).build();

        // AES/CBC/*
        parameter.setModel(Model.CBC);
        // AES/CBC/NO_PADDING
        parameter.setPadding(Padding.NO_PADDING);
        testAESEncrypt(parameter);
        // AES/CBC/PKCS5Padding
        parameter.setPadding(Padding.PKCS5_PADDING);
        testAESEncrypt(parameter);
        // AES/CBC/PKCS7Padding
        parameter.setPadding(Padding.PKCS7_PADDING);
        testAESEncrypt(parameter);
        // AES/CBC/ISO10126Padding
        parameter.setPadding(Padding.ISO10126_PADDING);
        testAESEncrypt(parameter);

        // ********************************************************************

        // AES/ECB/*
        parameter.setModel(Model.ECB);
        // AES/ECB/NO_PADDING
        parameter.setPadding(Padding.NO_PADDING);
        testAESEncrypt(parameter);
        // AES/ECB/PKCS5_PADDING
        parameter.setPadding(Padding.PKCS5_PADDING);
        testAESEncrypt(parameter);
        // AES/ECB/PKCS7_PADDING
        parameter.setPadding(Padding.PKCS7_PADDING);
        testAESEncrypt(parameter);
        // AES/ECB/ISO10126Padding
        parameter.setPadding(Padding.ISO10126_PADDING);
        testAESEncrypt(parameter);

        // ********************************************************************

        // AES/CFB/*
        parameter.setModel(Model.CFB);
        // AES/CFB/NO_PADDING
        parameter.setPadding(Padding.NO_PADDING);
        testAESEncrypt(parameter);
        // AES/CFB/PKCS5_PADDING
        parameter.setPadding(Padding.PKCS5_PADDING);
        testAESEncrypt(parameter);
        // AES/CFB/PKCS7_PADDING
        parameter.setPadding(Padding.PKCS7_PADDING);
        testAESEncrypt(parameter);
        // AES/CFB/ISO10126Padding
        parameter.setPadding(Padding.ISO10126_PADDING);
        testAESEncrypt(parameter);

        // ********************************************************************

        // AES/CTR/*
        parameter.setModel(Model.CTR);
        // AES/CTR/NO_PADDING
        parameter.setPadding(Padding.NO_PADDING);
        testAESEncrypt(parameter);
        // AES/CTR/PKCS5_PADDING
        parameter.setPadding(Padding.PKCS5_PADDING);
        testAESEncrypt(parameter);
        // AES/CTR/PKCS7_PADDING
        parameter.setPadding(Padding.PKCS7_PADDING);
        testAESEncrypt(parameter);
        // AES/CTR/ISO10126Padding
        parameter.setPadding(Padding.ISO10126_PADDING);
        testAESEncrypt(parameter);

        // ********************************************************************

        // AES/OFB/*
        parameter.setModel(Model.OFB);
        // AES/OFB/NO_PADDING
        parameter.setPadding(Padding.NO_PADDING);
        testAESEncrypt(parameter);
        // AES/OFB/PKCS5_PADDING
        parameter.setPadding(Padding.PKCS5_PADDING);
        testAESEncrypt(parameter);
        // AES/OFB/PKCS7_PADDING
        parameter.setPadding(Padding.PKCS7_PADDING);
        testAESEncrypt(parameter);
        // AES/OFB/ISO10126Padding
        parameter.setPadding(Padding.ISO10126_PADDING);
        testAESEncrypt(parameter);

    }
}
