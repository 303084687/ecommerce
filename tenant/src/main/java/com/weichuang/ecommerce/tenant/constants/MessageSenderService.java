package com.weichuang.ecommerce.tenant.constants;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service("messageSenderService")
public class MessageSenderService {
	/**
	 * 调用短信发送接口地址
	 */
	public static String defaultUrl = "http://message.kokozu.net/message-platform";
	//短信内容
	public static final String content1 = "您在immerex的校验码是";
	public static final String content2 = "。验证码有效期1小时，工作人员不会以任何借口向您索要信息，感谢您使用immerex。";
	
	/**
	 * 短信发送接口
	 * @param 渠道ID,短信类型ID,接受方手机号，发送内容
	 * @return Results->status(短信接口提供的接口返回值状态),error(接口返回内容，成功显示正常)
	 */
	public String messageSender(String mobile, String code) {
		String content = content1 + code + content2;
		String responseText = null;
		Long channelId = Long.valueOf("28");
		Long msgTypeId = Long.valueOf("51");
		String channel = getChannlByID(channelId);
		channel = channel.substring(34, channel.length() - 1);
		JSONObject jsonObject = JSONObject.fromObject(channel);
		String channelUsername = jsonObject.getString("account");
		String channelPassword = jsonObject.getString("password");
		String passageIds = jsonObject.getString("passageIds");
		String[] arr = passageIds.split(",");
		int passageId = Integer.parseInt(arr[0]);
		channelUsername = "msgvr";
		channelPassword = "msgvr321";
		passageId = 59;
		List<NameValuePair> formparams = new ArrayList<>();
		formparams.add(new BasicNameValuePair("mobile", mobile));
		formparams.add(new BasicNameValuePair("channelId", String.valueOf(channelId)));
		formparams.add(new BasicNameValuePair("passageId", String.valueOf(passageId)));
		formparams.add(new BasicNameValuePair("channelUsername", channelUsername));
		formparams.add(new BasicNameValuePair("channelPassword", channelPassword));
		formparams.add(new BasicNameValuePair("msgType", String.valueOf(msgTypeId)));
		formparams.add(new BasicNameValuePair("msgContent", content));
		String enc = enc(formparams, channelPassword);
		String url = defaultUrl + "/" + "message/send?mobile=" + mobile + "&channelId=" + channelId + "&passageId="
				+ passageId + "&channelUsername=" + channelUsername + "&channelPassword=" + channelPassword
				+ "&msgType=" + msgTypeId + "&msgContent=" + content + "&enc=" + enc + "";
		System.out.println(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httppost = new HttpGet(url);
		try {
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseText = EntityUtils.toString(entity, "UTF-8");
				}
			}
			finally {
				response.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				httpclient.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		responseText = responseText.replaceAll("/", "");
		JSONObject jsonObject1 = JSONObject.fromObject(responseText);
		String status = jsonObject1.getString("status");
		String error = jsonObject1.getString("error");
		return status + "    " + error;
	}
	
	/**
	 * 根据渠道ID获取渠道信息
	 * @param 渠道ID
	 * @return responseText
	 */
	private String getChannlByID(Long channelId) {
		String responseText = null;
		String url = defaultUrl + "/channel/search" + channelId;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httppost = new HttpGet(url);
		try {
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseText = EntityUtils.toString(entity, "UTF-8");
				}
			}
			finally {
				response.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				httpclient.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseText;
	}
	
	/**
	 * MD5加密获取enc
	 * @param nvps
	 * @return enc
	 */
	@SuppressWarnings("deprecation")
	private String enc(List<NameValuePair> nvps, String md5Key) {
		StringBuilder builder = new StringBuilder();
		TreeMap<String, String> tree = new TreeMap<>();
		for (NameValuePair nvp : nvps) {
			tree.put(nvp.getName(), URLDecoder.decode(nvp.getValue()));
		}
		for (String value : tree.values()) {
			builder.append(value);
		}
		builder.append(md5Key);
		return MD5.getMD5(builder.toString()).toLowerCase();
	}
}
