package com.boot.metis.common.util;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.util.Base64;

public class DESCryptUtil {
	/**
	 * 偏移变量，固定占8位字节
	 */

	private final static byte[] IV = {0x12, 0x34, 0x56, 0x78, (byte)0x90, (byte)0xAB, (byte)0xCD, (byte)0xEF};
	/**
	 * 密钥算法
	 */
	private static final String ALGORITHM = "DES";
	/**
	 * 加密/解密算法-工作模式-填充模式
	 */
	private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";
	/**
	 * 默认编码
	 */
	private static final String CHARSET = "utf-8";

	private DESCryptUtil(){}

	/**
	 * DES加密字符串
	 * @param content 待加密字符串
	 * @return 加密后内容
	 */
	public static String encrypt(String content,String key) {
		String data = "";
		try {
			Key secretKey = generateKey(key);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(IV);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			byte[] bytes = cipher.doFinal(content.getBytes(CHARSET));

			//JDK1.8及以上可直接使用Base64，JDK1.7及以下可以使用BASE64Encoder
			//Android平台可以使用android.util.Base64
			return new String(Base64.getEncoder().encode(bytes));
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}

	/**
	 * DES解密字符串
	 * @param password 待解密字符串
	 * @return 解密后内容
	 */
	public static String decrypt(String password,String key) {
		String data = "";
		try {
			Key secretKey = generateKey(key);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			IvParameterSpec iv = new IvParameterSpec(IV);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			return new String(cipher.doFinal(Base64.getDecoder().decode(password.getBytes(CHARSET))), CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
	}


	/**
	 * 生成key
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	private static Key generateKey(String key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key.getBytes(CHARSET));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		return keyFactory.generateSecret(dks);
	}

	public static void main(String[] args) {
		System.out.println(" = " + DESCryptUtil.decrypt("ROb0JSCHlm2evkQvLodfSBRy4qjICTrkpbGaxBBzghMl+q2MhFIvgav4H5jwL+yXs5Yp7FlwY0+4KNHn4ImXl1rOmc4bgjrzSEynJh8FnmiO3n5nc0JvrIBzgD5HDo/W/W8tXEMWVxhaY51WEbR5/NcCSmlHB+RO","MFwwDQYJKoZIhvm6") + "," + "当前类=DESCryptUtil.main()");
	}
}
