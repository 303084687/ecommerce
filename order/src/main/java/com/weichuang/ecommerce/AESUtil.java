package com.weichuang.ecommerce;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName: AESUtil.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.zhidianwuxian.cn</p>
 * <p>Description:aes加密算法 </p>
 * <p>author: wanggongliang</p>
 * <p>2016年11月23日 上午10:09:50</p>
 */
@Component
public class AESUtil {
    /** 
     * 密钥算法 
    */
    private static final String KEY_ALGORITHM = "AES";

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final String KEY = "671ce319dff061d4";

    // 加密 data 需要加密的字符串 返回结果已经base64加密
    public static String Encrypt(String data) throws Exception {
        byte[] raw = KEY.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// "算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return new String(Base64.encodeBase64(encrypted), "utf-8");
    }

    // 解密 data 需要解密的字符串
    public static String Decrypt(String data) throws Exception {
        try {
            byte[] raw = KEY.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted = Base64.decodeBase64(data);
            byte[] original = cipher.doFinal(encrypted);
            String originalString = new String(original);
            return originalString;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.toString());
            return null;
        }
    }

    // 测试
    public static void main(String[] args) throws Exception {
        String cSrc = "2f300aad34wgltest85058ec3cfdccc0d1f5ceb82d331d0";
        // 加密
        long lStart = System.currentTimeMillis();
        String enString = Encrypt(cSrc);
        System.out.println("加密后的字串是：" + enString);
        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("加密耗时：" + lUseTime + "毫秒");
        // 解密
        lStart = System.currentTimeMillis();
        String DeString = Decrypt(enString);
        System.out.println("解密后的字串是：" + DeString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("解密耗时：" + lUseTime + "毫秒");
    }
}
