package com.test;

import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.weichuang.commons.Utils;

/**
 * Created by zhangjiming on 2016/10/25.
 */
public class JUnitTest {
	/**
	 * <p>Description: 请求api网关单元测试 </p>
	 * @return
	 * <p>Author:jmzhang/张际明, 16/09/20</p>
	 */
	@Test
	public void testRESTful() {
		try {
			//当前时间戳
			long timestamp = System.currentTimeMillis();
			//要发送的数据对象
			OpLogAddEntity entity = new OpLogAddEntity();
			entity.setUserId("userId");
			//把数据转换成map,增加时间戳,并借助treemap做排序
			TreeMap<String, Object> sortParamMap = Utils.beanToMap(entity);
			sortParamMap.put("timestamp", timestamp);
			//生成参数格式,如:timestamp=1477395605900&userId=userId&
			StringBuffer sb = new StringBuffer();
			for (String key : sortParamMap.keySet()) {
				String value = sortParamMap.get(key).toString();
				if (StringUtils.isNotEmpty(value)) {
					sb.append(key + "=" + value + "&");
				}
			}
			//参数转变为小写,并去掉最后的&
			String param = sb.toString();
			param = param.substring(0, param.length() - 1).toLowerCase();
			//通过私钥生成签名,算法:HmacSHA1
			String signature = Utils.calculateRFC2104HMAC(param, "72373df2eb4f44c7ab1ff7be4c476b29");
			String url = "http://localhost:10000/log/api/oplog";
			CloseableHttpClient httpClient = null;
			CloseableHttpResponse response = null;
			String result = null;
			httpClient = HttpClients.custom().build();
			HttpPut httpPut = new HttpPut(url);
			//设置Content-type参数值application/json; charset=utf-8
			httpPut.addHeader("Content-type", "application/json; charset=utf-8");
			//头信息,公钥/时间戳/签名
			httpPut.addHeader("publickey", "zdwx.vr.web");
			httpPut.addHeader("timestamp", String.valueOf(timestamp));
			httpPut.addHeader("signature", signature);
			StringEntity _entity = new StringEntity(JSON.toJSONString(entity), Consts.UTF_8);
			httpPut.setEntity(_entity);
			response = httpClient.execute(httpPut);
			System.out.println(response.getStatusLine().getStatusCode());
			HttpEntity __entity = response.getEntity();
			if (__entity != null) {
				result = EntityUtils.toString(__entity, Consts.UTF_8);
				EntityUtils.consume(__entity);
			}
			System.out.println("result:" + result);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
