package com.weichuang.ecommerce.coupon.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author wgl
 * @ClassName: EncryptHelper
 * @Description: des加密优惠券和购物卡
 * @date 2017年10月24日 下午9:44:49
 */
public class EncryptHelper {

    public static final String ALGORITHM_DES = "DES/ECB/PKCS5Padding";

    public static final String cardKey = "j$8l0*kw";

    /**
     * 解密数据
     * @param message
     * @return
     * @throws Exception
     */
    public static String decrypt(String message) throws Exception {
        byte[] bytesrc = convertHexString(message);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(cardKey.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(cardKey.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }

    /**
     * 加密数据
     * @param message
     * @return
     * @throws Exception
     */
    public static byte[] encryptCoupon(String message) throws Exception {
        String jiami = java.net.URLEncoder.encode(message, "utf-8");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding"); // 生成对象,加密算法
        DESKeySpec desKeySpec = new DESKeySpec(cardKey.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(cardKey.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(jiami.getBytes("UTF-8"));
    }

    /**
     * 解密输出字符串
     *
     * @param ss
     * @return
     */
    public static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }

    /**
     * 解密输出字符串
     *
     * @param b
     * @return
     */
    public static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }
        return hexString.toString();
    }

    /**
     * 加密
     *
     * @param decrypt
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBase64(String key, String decrypt) {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.encodeBase64String(cipher.doFinal(decrypt.getBytes()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param decrypt
     * @param key
     * @return
     * @throws Exception
     */
    public static String decodeBase64(String key, String decrypt) {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.decodeBase64(decrypt.getBytes())));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String message = EncryptHelper.toHexString(EncryptHelper.encryptCoupon(CouponHelper.createUUID()));
        System.out.println(message);
        System.out.println(EncryptHelper.decrypt(message));
    }
}
