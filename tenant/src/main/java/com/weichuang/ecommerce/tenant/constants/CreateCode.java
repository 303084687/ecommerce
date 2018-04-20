package com.weichuang.ecommerce.tenant.constants;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>ClassName: CreateCode</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 生成各种码</p>
 * <p>author: licheng</p>
 * <p>date: 2016/11/11 16:17 </p>
 */
public class CreateCode {
	private static final Logger log = LoggerFactory.getLogger(CreateCode.class);
	private int length = 6;
	
	/**
	 * <p>method: 验证码 </p>
	 * <p>Description: 生成随机数字和字母组合</p>
	 * <p>author licheng </p>
	 * <p>date 2016/11/11 16:19 </p>
	 * <p>param  </p>
	 * <p>return </p>
	 */
	public static String verificationCode(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "num" : "num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 默认大写字母
				int choice = 65;
				sb.append((char) (choice + random.nextInt(26)));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				sb.append(String.valueOf(random.nextInt(10)));
			}
		}
		log.info("Verification Code={}", sb.toString());
		return sb.toString();
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public static void main(String[] args) {
		System.out.println(verificationCode(6));
	}
}
