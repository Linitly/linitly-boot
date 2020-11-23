package org.linitly.boot.base.utils.algorithm.rsa;

import org.apache.commons.codec.binary.Base64;
import org.linitly.boot.base.exception.DecryptException;
import org.linitly.boot.base.exception.EncryptException;
import org.linitly.boot.base.utils.algorithm.AlgorithmEnum;
import org.linitly.boot.base.utils.algorithm.EncryptionUtil;
import org.linitly.boot.base.utils.algorithm.aes.OutputType;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.MessageFormat;

/**
 * @author: linxiunan
 * @date: 2020/11/23 13:55
 * @descrption:
 */
public class RSAUtil {

    // RSA/ECB/PKCS1Padding

    private static KeyPair generateKeyPair(int keySize) {
        KeyPair keyPair = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(AlgorithmEnum.RSA.getValue());
            keyPairGenerator.initialize(keySize);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyPair;
    }

    public static String[] generateKeys(RSAParameter parameter) {
        KeyPair keyPair = generateKeyPair(parameter.getKeySize().getValue());
        String publicKey = null;
        String privateKey = null;
        switch (parameter.getOutputType()) {
            case BASE64:
                publicKey = Base64.encodeBase64String(keyPair.getPublic().getEncoded());
                privateKey = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
                break;
            case HEX:
                publicKey = EncryptionUtil.byteToHex(keyPair.getPublic().getEncoded());
                privateKey = EncryptionUtil.byteToHex(keyPair.getPrivate().getEncoded());
                break;
            default:
                break;
        }
        return new String[]{publicKey, privateKey};
    }

    public static String encrypt(RSAParameter parameter, String content) {
        String result = null;
        try {
            byte[] buffer = null;
            switch (parameter.getOutputType()) {
                case BASE64:
                    buffer = Base64.decodeBase64(parameter.getPublicKey());
                    break;
                case HEX:
                    buffer = EncryptionUtil.hexToByteArray(parameter.getPublicKey());
                    break;
                default:
                    break;
            }
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(AlgorithmEnum.RSA.getValue());
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance(AlgorithmEnum.RSA.getValue());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptBytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            switch (parameter.getOutputType()) {
                case BASE64:
                    result = new String(Base64.encodeBase64(encryptBytes), StandardCharsets.UTF_8);
                    break;
                case HEX:
                    result = EncryptionUtil.byteToHex(encryptBytes);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EncryptException(MessageFormat.format("RSA解密失败：publicKey = {0}, privateKey = {1}, content = {2}", parameter.getPublicKey(), parameter.getPrivateKey(), content));
        }
        return result;
    }

    public static String decrypt(RSAParameter parameter, String content) {
        String result = null;
        try {
            byte[] buffer = null;
            switch (parameter.getOutputType()) {
                case BASE64:
                    buffer = Base64.decodeBase64(parameter.getPrivateKey());
                    break;
                case HEX:
                    buffer = EncryptionUtil.hexToByteArray(parameter.getPrivateKey());
                    break;
                default:
                    break;
            }
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(AlgorithmEnum.RSA.getValue());
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance(AlgorithmEnum.RSA.getValue());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] encryptBytes = null;
            switch (parameter.getOutputType()) {
                case BASE64:
                    encryptBytes = cipher.doFinal(Base64.decodeBase64(content.getBytes(StandardCharsets.UTF_8)));
                    break;
                case HEX:
                    encryptBytes = cipher.doFinal(EncryptionUtil.hexToByteArray(content));
                    break;
                default:
                    break;
            }
            result = new String(encryptBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DecryptException(MessageFormat.format("RSA解密失败：publicKey = {0}, privateKey = {1}, content = {2}", parameter.getPublicKey(), parameter.getPrivateKey(), content));
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        RSAParameter parameter = RSAParameter.builder().keySize(KeySize.L512).outputType(OutputType.BASE64).build();
        String[] keys = generateKeys(parameter);
        parameter.setPublicKey(keys[0]);
        parameter.setPrivateKey(keys[1]);

        System.out.println("publicKey:" + keys[0]);
        System.out.println("privateKey:" + keys[1]);

        // 原文本
        String content = "测试RSA加密解密";
        // RSA加密
        String encryptedContent = encrypt(parameter, content);
        System.out.println("加密后的数据：" + encryptedContent);
        // RSA解密
        String decryptContent = decrypt(parameter, encryptedContent);
        System.out.println("解密后的数据：" + decryptContent);
    }
}
