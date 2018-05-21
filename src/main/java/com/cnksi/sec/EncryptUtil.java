package com.cnksi.sec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * 加密
 * @author xl
 *
 */
public class EncryptUtil {
	private final static byte[] DESKey = "939847C35AFF0A671DA637E98DAD9A29".getBytes();
	private static enum EncrypType{
		DES
	}
	
	/**
	 * MD5 加密
	 * @param s
	 * @return
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[] hexStr2ByteArr(String strIn) 互为可逆的转换过程
	 * 
	 * @param arrB 需要转换的byte数组
	 * @return 转换后的字符串
	 * @throws Exception 本方法不处理任何异常，所有异常全部抛出
	 */
	public static String byteArr2HexStr(byte[] arrB) {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB) 互为可逆的转换过程
	 * 
	 * @param strIn 需要转换的字符串
	 * @return 转换后的byte数组
	 * @throws Exception 本方法不处理任何异常，所有异常全部抛出
	 */
	public static byte[] hexStr2ByteArr(String strIn) {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	
	public static class DES{
		 /**
	     * Description 根据键值进行加密
	     * @param data 
//	     * @param key  加密键byte数组
	     * @return
	     * @throws Exception
	     */
	    public static String encrypt(String data){
	        try {
				byte[] bt = encrypt(data.getBytes(), DESKey);
//				String strs = new BASE64Encoder().encode(bt);
				return byteArr2HexStr(bt);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	    }
	 
	    /**
	     * Description 根据键值进行解密
	     * @param data
//	     * @param key  加密键byte数组
	     * @return
	     * @throws IOException
	     * @throws Exception
	     */
	    public static String decrypt(String data){
	        if (data == null)
	            return null;
	        
	        try {
//				BASE64Decoder decoder = new BASE64Decoder();
//				byte[] buf = decoder.decodeBuffer(data);

                byte[] buf = hexStr2ByteArr(data);
                byte[] bt = decrypt(buf, DESKey);
				return new String(bt);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	    }
	 
	    /**
	     * Description 根据键值进行加密
	     * @param data
	     * @param key  加密键byte数组
	     * @return
	     * @throws Exception
	     */
	    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
	        // 生成一个可信任的随机数源
	        SecureRandom sr = new SecureRandom();
	 
	        // 从原始密钥数据创建DESKeySpec对象
	        DESKeySpec dks = new DESKeySpec(key);
	 
	        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(EncrypType.DES.toString());
	        SecretKey securekey = keyFactory.generateSecret(dks);
	 
	        // Cipher对象实际完成加密操作
	        Cipher cipher = Cipher.getInstance(EncrypType.DES.toString());
	 
	        // 用密钥初始化Cipher对象
	        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
	 
	        return cipher.doFinal(data);
	    }
	     
	     
	    /**
	     * Description 根据键值进行解密
	     * @param data
	     * @param key  加密键byte数组
	     * @return
	     * @throws Exception
	     */
	    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
	        // 生成一个可信任的随机数源
	        SecureRandom sr = new SecureRandom();
	 
	        // 从原始密钥数据创建DESKeySpec对象
	        DESKeySpec dks = new DESKeySpec(key);
	 
	        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(EncrypType.DES.toString());
	        SecretKey securekey = keyFactory.generateSecret(dks);
	 
	        // Cipher对象实际完成解密操作
	        Cipher cipher = Cipher.getInstance(EncrypType.DES.toString());
	 
	        // 用密钥初始化Cipher对象
	        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
	 
	        return cipher.doFinal(data);
	    }
	}

    public static void main(String[] args) {
//        String encrypt = DES.encrypt("root");
//        System.out.println("user: ".concat(DES.encrypt("root")));
//        System.out.println("pwd: ".concat(DES.encrypt("cnksi.com")));
        System.out.println(DES.encrypt("jdbc:mysql://172.16.101.254:3307/ess_sec_v4?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useInformationSchema=true&remarks=true"));
        System.out.println(DES.encrypt("ess_sec"));
        System.out.println(DES.encrypt("ess_sec@1234"));

//        System.out.println(MD5Util.getMd5("jnj2015.*").toUpperCase());
//        System.out.println(MD5Util.getMd5("1").toUpperCase());
//        System.out.println(DES.decrypt("16b347452351650621885130d180a068b3285bf3f5894f1a8af2770ac5ff0810c174d4ba2e38b90620b08b3b020f9abbb16babf5c4efd2fda3aef5557ed66b5d5aee500420aceaa55668b7414753d20148911529270e042b6205644117934569b5f91760d4b9d653b75808f39f5488f8a0cf91a88f2419a445ba74df4f032b4666a9b0499c0414b11921d10ff47d561d"));
    }
}
