package com.weichuang.ecommerce;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName: EncryptUtil.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.wuxian.cn</p>
 * <p>Description: sha加密算法</p>
 * <p>author: wanggongliang</p>
 * <p>2016年11月23日 上午10:09:21</p>
 */
@Component
public class EncryptUtil {
	//构造函数 
	public EncryptUtil() {
	}

	private final String ENCRYPT_CONFIG_KEY = "weiyechuangtou";

	public String getEncryptConfigKey(){
		return ENCRYPT_CONFIG_KEY;
	}
	
	//定义加密方式 
	private final static String KEY_SHA = "SHA";
	//全局数组
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };
	//配置参数
	@Autowired
	public EncryptSourceProperties properties;
	private final static String globalKey = "661ee319dff061d4fb6532787f7b6927";
	private final static String passSalt = "w1fsd2DurO0";
	
	/** 
	 * SHA 加密 
	 * @param data 需要加密的字符串 
	 * @return 加密之后的字符串 
	 * @throws Exception 
	 */
	public static String encryptSHA(String data) throws Exception {
		// 验证传入的字符串  
		if (null == data || 0 == data.length()) {
			return null;
		}
		// 创建具有指定算法名称的信息摘要  
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		// 使用指定的字节数组对摘要进行最后更新  
		sha.update(data.getBytes());
		// 完成摘要计算  
		byte[] bytes = sha.digest();
		// 将得到的字节数组变成字符串返回  
		return byteArrayToHexString(bytes);
	}
	
	/** 
	 * 将一个字节转化成十六进制形式的字符串 
	 * @param b 字节数组 
	 * @return 字符串 
	 */
	private static String byteToHexString(byte b) {
		int ret = b;
		if (ret < 0) {
			ret += 256;
		}
		int m = ret / 16;
		int n = ret % 16;
		return hexDigits[m] + hexDigits[n];
	}
	
	/** 
	 * 转换字节数组为十六进制字符串 
	 * @param bytes 字节数组 
	 * @return 十六进制字符串 
	 */
	private static String byteArrayToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(byteToHexString(bytes[i]));
		}
		return sb.toString();
	}


	/**
	 * <p>Description:获取明文数据的加密串,公式:registerKey=SHA（appKey）;base64(aes(registerKey.subString(0,10)+用户密码(明文)+ registerKey.subString(10))) </p>
	 * <p>@return 加密串</p>
	 * <p>@throws Exception</p>
	 * <p>author: jiangkesen</p>
	 * <p>2017年10月19日 16:29</p>
	 */
	public String getEncryptString(String password) throws Exception {
		String result = "";

		String registerKey = encryptSHA(ENCRYPT_CONFIG_KEY);
		String data = registerKey.substring(0, 10) + password + registerKey.substring(10);
		result = AESUtil.Encrypt(data);// toBase64
		return  result;
	}
	
	/**
	    * <p>Description:根据加密参数和用户主键生成单项加密密码存到数据库用于用户注册 </p>
	   * <p>@param appKey 设备appkey
	   * <p>@param userId 用户主键
	   * <p>@param encryptStr 加密参数 公式:registerKey=SHA（appKey）;base64(aes(registerKey.subString(0,10)+用户密码(明文)+ registerKey.subString(10)))
	   * <p>@return 返回单向加密参数</p>
	   * <p>@throws Exception</p>
	   * <p>author: wanggongliang</p>
	   * <p>2016年11月23日 上午10:29:21</p>
	 */
	public String registerEncrypt(String appKey, Long userId, String encryptStr) throws Exception {
		//根据appkey解密用户明文密码 需要截取前10位然后比较算出用户密码
		String shaStr = encryptSHA(appKey).substring(10);
		//解密用户机密参数得到用户密码
		String aesStr = AESUtil.Decrypt(encryptStr).substring(10);//解密参数
		String passWord = aesStr.replace(shaStr, "");
		//用户密码生成公式:base64(sha(base64(sha (明文密码+Passsalt))+secureKey))
		//Globalkey全局加密的公共参数，从配置文件中进行读取;UserSequence用户序列号
		//用户secureKey，每个用户独有的安全密钥，secureKey=SHA（Globalkey+UserSequence），secureKey针对已经存在用户。
		//Passsalt用户加密salt每个用户的加密salt可以相同也可以不同，存储在数据中，salt为11位长计字母与数字随机字符串，区分大小写
		String secureKey = encryptSHA(globalKey + userId);
		String registerPass = encryptSHA(passWord + passSalt);
		String base64Pass = getBase64(registerPass);
		return getBase64(encryptSHA(base64Pass + secureKey));
	}
	
	/**
	   * <p>Description: 算出用户登录密码用户比较密码是否正确</p>
	   * <p>@param encryptStr 加密字符串 公式：base64(sha (明文密码+Passsalt))
	   * <p>@return 返回加密字符串
	   * <p>@throws Exception</p>
	   * <p>author: wanggongliang</p>
	   * <p>2016年11月23日 上午11:18:05</p>
	 */
	public String validateLoginPass(Long userId, String encryptStr) throws Exception {
		//1:计算 clientPassportSecureKey=base64(sha (明文密码+Passsalt))传给服务器端，
		//2:服务器端计算base64(sha(clientPassportSecureKey+secureKey))
		String secureKey = encryptSHA(globalKey + userId);
		return getBase64(encryptSHA(encryptStr + secureKey));
	}
	
	// base64加密  
	public static String getBase64(String str) throws Exception {
		byte[] b = null;
		String data = null;
		b = str.getBytes("utf-8");
		if (b != null) {
			data = new String(Base64.encodeBase64(b), "utf-8");
		}
		return data;
	}
	
	// base64解密  
	public static String getFromBase64(String str) throws Exception {
		byte[] b = null;
		String result = null;
		if (str != null) {
			b = Base64.decodeBase64(str);
			result = new String(b, "utf-8");
		}
		return result;
	}
	
	// 测试方法 
	public static void main(String[] args) throws Exception {
		EncryptUtil encryptUtil = new EncryptUtil();
		String key = "weiyechuangtou";
		Long userId = new Long(118);
		String pass = "666666";
		String passsalt = "w1fsd2DurO0";
		String registerKey = encryptSHA(key);
		String data = registerKey.substring(0, 10) + pass + registerKey.substring(10);
		System.out.println("前端传过来的密码加密字符串：" + AESUtil.Encrypt(data));

		//解密字符串
		String data1 = AESUtil.Decrypt(AESUtil.Encrypt(data));
		System.out.println("解密加密数据的值：" + data1);
		String s1 = data1.substring(10);
		String s2 = registerKey.substring(10);
		System.out.println("服务端解密明文密码的值：" + s1.replace(s2, ""));
		System.out.println("数据库生成的用户密码：" + encryptUtil.registerEncrypt(key, userId, AESUtil.Encrypt(data)));
		String strs = getBase64(encryptSHA(pass + passsalt));
		System.out.println("生成登录密码str：" + strs);
		System.out.println("返回登录密码进行比较：" + encryptUtil.validateLoginPass(userId, strs));

	}
}
