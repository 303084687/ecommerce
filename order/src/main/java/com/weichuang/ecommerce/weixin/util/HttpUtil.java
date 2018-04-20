package com.weichuang.ecommerce.weixin.util;

import com.alibaba.fastjson.JSONObject;
import com.weichuang.commons.ServiceException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.weichuang.ecommerce.weixin.entity.*;
import org.apache.commons.lang3.StringUtils;

/**
* <p>ClassName:HttpUtil</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/19 10:38</p>
**/
public class HttpUtil {
    //发送微信消息请求url
    public static final String sendMsg="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";
    //获取token请求url
    public static final String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /**
     * 判断token有效性
     */
    private static String checkTokenUrl="https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=%s&lang=zh_CN";
    /**
     * 发送http请求
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) throws ServiceException {
        JSONObject jsonObject = null;
        try {


            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            //log.error("连接超时：{}", ce);
            String errormsg=String.format("连接超时：%s",ce);
            throw new ServiceException(2001,errormsg);
        } catch (Exception e) {
           // log.error("https请求异常：{}", e);
            String errormsg=String.format("https请求异常：%s",e);
            throw new ServiceException(2002,errormsg);
        }
        return jsonObject;
    }
    /**
     *<p>Description:post请求</p>
     *<p>param requestUrl:请求地址</p>
     *<p>param paramStr:请求参数</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/19 10:46</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public static JSONObject httpsPost(String requestUrl, String paramStr) throws ServiceException{

       return  httpsRequest( requestUrl, "POST", paramStr);
    }
    /**
     *<p>Description:get请求</p>
     *<p>param requestUrl:请求地址</p>
     *<p>param paramStr:请求参数</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/19 10:46</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public static JSONObject httpsGet(String requestUrl, String paramStr) throws ServiceException{
        return  httpsRequest( requestUrl, "GET", paramStr);
    }
    /**
     *<p>Description:获取微信token</p>
     *<p>param appid:微信appid</p>
     *<p>param appsecret:微信appsecret</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/19 11:14</p>
     *<p>return:Token</p>
     */
    public static Token getToken(String appid,String appsecret) throws ServiceException
    {
        String requestUrl =String.format(tokenUrl,appid,appsecret);
        JSONObject jsonObject=httpsGet(requestUrl,null);
        if(jsonObject==null)
        {
            throw new ServiceException(2003,"HttpUtil.getToken, 获取微信json结果为空");
        }
        Token token = new Token();
        token.setAccessToken(jsonObject.getString("access_token"));
        token.setExpiresIn(jsonObject.getInteger("expires_in"));
        return token;
    }
    /**
     *<p>Description:发送消息</p>
     *<p>param openId:</p>
     *<p>param message:</p>
     *<p>param messageType:</p>
     *<p>param token:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/19 13:09</p>
     */
    public static JSONObject sendMsg(String openId,String message,String messageType,String token)throws ServiceException{

        String sendMsgUrl=String.format(HttpUtil.sendMsg,token);
        Map paramMap=new HashMap();
        paramMap.put("touser",openId);
        paramMap.put("msgtype",messageType);
        Map contentMap=new HashMap();
        contentMap.put("content",message);
        paramMap.put("text",contentMap);
        String paramJson= JSONObject.toJSONString(paramMap);
        JSONObject obj=HttpUtil.httpsPost(sendMsgUrl,paramJson);
        return obj;
    }
    /**
     *<p>Description:检查token是否有效</p>
     *<p>param token:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/13 12:50</p>
     */
    public static boolean checkToken(String token)
    {
        String check_token_url=String.format(checkTokenUrl,token);
        JSONObject jsonObject=HttpUtil.httpsGet(check_token_url,null);
        boolean flag=false;
        if(jsonObject!=null) {

            String iplist=jsonObject.get("ip_list")==null?"":jsonObject.get("ip_list").toString();
            if(StringUtils.isNotEmpty(iplist)) {
                flag=true;
            }
        }
        return flag;
    }
    /**
     *<p>Description:发送文本消息</p>
     *<p>param openId:</p>
     *<p>param message:</p>
     *<p>param messageType:</p>
     *<p>param token:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/19 13:09</p>
     */
    public static JSONObject sendTextMsg(String openId,String message,String token) throws ServiceException{

        return sendMsg(openId,message,"text",token);
    }
    public static void main(String[] args)
    {
   /*     Token token=getToken("wx28ec953815b5c39f","99136d9e3b2d33ee61c1511f44531087");
        System.out.println("token="+token.getAccessToken());*/
        Token token=new Token();
        token.setAccessToken("5_aB495jxsq4OYeqjIsePd5_KqjT8WSBhYO42Sy3kg5eTz3ZxxH_bIzFnKixPg3cszdEg5HQY_kxJX7vbTeHLLDlNNPFcdMczTielxy81hQXA0OlaOffp3B4yUW4LF0ugoJcGVgyeG6O1ZVfpBZICcAFAJSM");
        JSONObject obj=sendTextMsg("oxp_M093Jj23VSdvn6orN60e6HOA","测试消息232323",token.getAccessToken());
        System.out.println(obj.toJSONString());
    }
}
