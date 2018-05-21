
package com.cnksi.sec;

import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import org.apache.shiro.SecurityUtils;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class RSAUtils {

    public static class RSAException extends RuntimeException{
        public RSAException(String message) {
            super(message);
        }

        public RSAException(String message, Throwable e) {
            super(message, e);
        }

        public RSAException(Throwable e) {
            super(e);
        }
    }

    public static final String SESSION_RSA_PRIVATE_KEY = "rsa_private_key"; //session保存 rsa 私钥

    /**
     * 生成公钥(public)和私钥(private)数组<br/>
     * public : RSAPublicKey, private : RSAPrivateKey
     *
     * @throws NoSuchAlgorithmException
     */
    public static Object[] getKeys() {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            return new Object[]{publicKey, privateKey};
        } catch (NoSuchAlgorithmException e) {
            throw new RSAException(e);
        }
    }

    /**
     * 使用模和指数生成RSA公钥
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥
     * /None/NoPadding
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws RSAException
     */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 模长
            int key_len = publicKey.getModulus().bitLength() / 8;
            // 加密数据长度 <= 模长-11
            String[] datas = splitString(data, key_len - 11);
            String mi = "";
            //如果明文长度大于模长-11则要分组加密
            for (String s : datas) {
                mi += bcd2Str(cipher.doFinal(s.getBytes()));
            }
            return mi;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws RSAException
     */
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey) throws RSAException {
        String ming = "";
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            //模长
            int key_len = privateKey.getModulus().bitLength() / 8;
            byte[] bytes = data.getBytes();
            byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
            //System.err.println(bcd.length);
            //如果密文长度大于模长则要分组解密
            byte[][] arrays = splitArray(bcd, key_len);
            for (byte[] arr : arrays) {
                ming += new String(cipher.doFinal(arr));
            }
        } catch (Exception e) {
            throw new RSAException(e);
        }

        removeKeys(privateKey);

        return ming;
    }

    /**
     * ASCII码转BCD码
     */
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    public static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }

    /**
     * BCD转字符串
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /**
     * 拆分字符串
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /**
     * 拆分数组
     */
    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

    public static void main(String[] args) throws RSAException {
        Object[] keys = getKeys();
        //生成公钥和私钥
        RSAPublicKey publicKey = (RSAPublicKey) keys[0];
        RSAPrivateKey privateKey = (RSAPrivateKey) keys[1];

        //模
        String modulus = publicKey.getModulus().toString();
        System.out.println("pubkey modulus=" + modulus);
        //公钥指数  
        String public_exponent = publicKey.getPublicExponent().toString();
        System.out.println("pubkey exponent=" + public_exponent);
        //私钥指数  
        String private_exponent = privateKey.getPrivateExponent().toString();
        System.out.println("private exponent=" + private_exponent);

        //明文
        String ming = "111";
        //使用模和指数生成公钥和私钥  
        RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);
        RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, private_exponent);
        //加密后的密文  
        String mi = RSAUtils.encryptByPublicKey(ming, publicKey);
        System.err.println("mi=" + mi);
        //解密后的明文  
        String ming2 = RSAUtils.decryptByPrivateKey(mi, privateKey);
        System.err.println("ming2=" + ming2);
    }

    /**
     * 通过公钥获取缓存的私钥
     * @param keyName publicKeyExponent作为keyName
     * @return
     */
    public static RSAPrivateKey getPrivateKeyCached(String keyName) {
        RSAPrivateKey privateKey = null;
        if (StrKit.notBlank(keyName)) {
            KRSA rsa = CacheKit.get(SESSION_RSA_PRIVATE_KEY, keyName);
            if (rsa != null) privateKey = rsa.getPrivateKey();
        }
        if (privateKey == null) {
            privateKey = (RSAPrivateKey) SecurityUtils.getSubject().getSession().getAttribute(SESSION_RSA_PRIVATE_KEY);
        }
        return privateKey;
    }

    /**
     * 移除所有已使用的rsa缓存数据
     * @return
     */
    public static void removeKeys(RSAPrivateKey privateKey) {
        try {
            if (SecurityUtils.getSubject() != null) {
                SecurityUtils.getSubject().getSession().removeAttribute(SESSION_RSA_PRIVATE_KEY);
            }

            if (privateKey != null) {
                KRSA rsa = CacheKit.get(SESSION_RSA_PRIVATE_KEY, "private_" + privateKey.getPrivateExponent());
                String publicKeyStr = rsa.getPublicExponent();
                String privateKeyStr = "private_" + privateKey.getPrivateExponent();
                CacheKit.remove(SESSION_RSA_PRIVATE_KEY, publicKeyStr);
                CacheKit.remove(SESSION_RSA_PRIVATE_KEY, privateKeyStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成公钥和私钥，并放到request和session中
     */
    public static KRSA genRsa() {
        //移除之前的rsa
        if (SecurityUtils.getSubject() != null) {
            RSAPrivateKey privateKey = (RSAPrivateKey) SecurityUtils.getSubject().getSession().getAttribute(SESSION_RSA_PRIVATE_KEY);
            if (privateKey != null) {
                removeKeys(privateKey);
            }
        }

        //生成rsa 公钥和私钥
        try {
            Object[] rsaKeys = RSAUtils.getKeys();
            RSAPublicKey publicKey = (RSAPublicKey) rsaKeys[0];
            RSAPrivateKey privateKey = (RSAPrivateKey) rsaKeys[1];

            KRSA rsa = new KRSA(publicKey, privateKey);

            //以publicKey作为键
            CacheKit.put(SESSION_RSA_PRIVATE_KEY, rsa.getPublicExponent(), rsa);
            CacheKit.put(SESSION_RSA_PRIVATE_KEY, "private_" + privateKey.getPrivateExponent(), rsa);
            SecurityUtils.getSubject().getSession().setAttribute(SESSION_RSA_PRIVATE_KEY, privateKey);

            return rsa;
        } catch (Exception e) {
            LogKit.error("生成rsa key错误", e);
        }
        return null;
    }

    public static void genRsa(HttpServletRequest request) {
        KRSA rsa = genRsa();
        //将公钥返回页面
        request.setAttribute("publicKeyExponent", rsa.getPublicExponent());
        request.setAttribute("publicKeyModulus", rsa.getPublicModulus());
    }


    public static class KRSA implements Serializable{
        private RSAPublicKey publicKey;
        private RSAPrivateKey privateKey;

        public KRSA(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public RSAPublicKey getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(RSAPublicKey publicKey) {
            this.publicKey = publicKey;
        }

        public RSAPrivateKey getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(RSAPrivateKey privateKey) {
            this.privateKey = privateKey;
        }

        public String getPublicExponent() {
            return getPublicKey().getPublicExponent().toString(16);
        }

        public String getPublicModulus() {
            return getPublicKey().getModulus().toString(16);
        }

    }

}