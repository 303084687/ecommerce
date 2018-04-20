package com.weichuang.ecommerce.tenant.wechat.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @describe
 * @author towards.liu@163.com
 * @version 1.0
 * @date 2017年9月26日
 */
public class MessageUtil {
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws DocumentException, IOException {

		Map<String, String> map = new HashMap<>();
		SAXReader reader = new SAXReader();
		InputStream in = request.getInputStream();
		Document document = reader.read(in);
		Element root = document.getRootElement();
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		in.close();
		return map;

	}
}
