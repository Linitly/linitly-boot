package org.linitly.boot.base.utils.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

	private static final String RSA_ALGORITHM = "RSA";
	private static final String CHARSET = "UTF-8";

	private static final String RSA_TYPE_ENCRYPT = "encrypt";
	private static final String RSA_TYPE_DECRYPT = "decrypt";

	// 签名算法
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 得到公钥
	 * 
	 * @param publicKey
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 通过X509编码的Key指令获得公钥对象
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
		RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
		return key;
	}

	/**
	 * 得到私钥
	 * 
	 * @param privateKey
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static RSAPrivateKey getPrivateKey(String privateKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 通过PKCS#8编码的Key指令获得私钥对象
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
		RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
		return key;
	}

	/**
	 * @author linxiunan
	 * @date 2019/8/14 10:22
	 * @return java.lang.String
	 * @description 公钥加密
	 */
	public static String publicEncrypt(String data, String publicKeyString) throws Exception {
		RSAPublicKey publicKey = getPublicKey(publicKeyString);
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return new String(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
				publicKey.getModulus().bitLength(), RSA_TYPE_ENCRYPT));
	}

	/**
	 * @author linxiunan
	 * @date 2019/8/14 10:23
	 * @return java.lang.String
	 * @description 私钥解密
	 */
	public static String privateDecrypt(String data, String privateKeyString) throws Exception {
		RSAPrivateKey privateKey = getPrivateKey(privateKeyString);
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
				privateKey.getModulus().bitLength(), RSA_TYPE_DECRYPT), CHARSET);
	}

	private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize, String rsaType)
			throws IllegalBlockSizeException, BadPaddingException {
		int maxBlock = 0;
		if (opmode == Cipher.DECRYPT_MODE) {
			maxBlock = keySize / 8;
		} else {
			maxBlock = keySize / 8 - 11;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] buff;
		int i = 0;
		while (datas.length > offSet) {
			if (datas.length - offSet > maxBlock) {
				buff = cipher.doFinal(datas, offSet, maxBlock);
			} else {
				buff = cipher.doFinal(datas, offSet, datas.length - offSet);
			}
			out.write(buff, 0, buff.length);
			i++;
			offSet = i * maxBlock;
		}
		byte[] resultDatas = null;
		if (rsaType.equals(RSA_TYPE_ENCRYPT)) {
			resultDatas = Base64.encodeBase64(out.toByteArray());
		} else if (rsaType.equals(RSA_TYPE_DECRYPT)) {
			resultDatas = out.toByteArray();
		}
		IOUtils.closeQuietly(out);
		return resultDatas;
	}

	/**
	 * 签名
	 *
	 * @param content
	 *            签名内容，即请求报文中的data节点
	 * @param privateKeyString
	 *            私钥字符串
	 * @return 签名字符串
	 */
	public static String signWithPrivateKey(String content, String privateKeyString) {
		try {
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(getPrivateKey(privateKeyString));
			signature.update(content.getBytes(CHARSET));
			return new String(Base64.encodeBase64(signature.sign()));
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
