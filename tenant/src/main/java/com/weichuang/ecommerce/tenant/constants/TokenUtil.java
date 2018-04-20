package com.weichuang.ecommerce.tenant.constants;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class TokenUtil {
	//设备token
	public static String getToken(String str) {
		byte[] signatureSHA = DigestUtils.sha1(str);
		byte[] base64 = new Base64().encode(signatureSHA);
		return new String(base64);
	}
}
