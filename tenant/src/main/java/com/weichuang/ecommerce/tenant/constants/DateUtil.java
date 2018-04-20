package com.weichuang.ecommerce.tenant.constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *@Title: MyUtil.java

 *@Description TODO
 *@author towards
 *@date  2017年11月7日 上午9:41:55
 */
public class DateUtil {

	/**
	 * 当前时间的字符串
	 * */
	 public static String getDate_str(){
		 
		 /**
		  * 时间格式化工具类
		  * */
			SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");
			/**
			 * 获取当前时间
			 * */
			  Date date= new  Date(  System.currentTimeMillis());
			  /**
				 * 根据格式返回当前时间的字符串
				 * */
			  return dateFormat.format(date);
		 
	 }
	 
	 /**
	  * 你给我什么时间 我就给你什么时间的 字符串
	  * */
	 public static String getDate_str(Date date){
		 /**
		  * 时间格式化工具类
		  * */
			SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");
			/**
			 * 根据格式返回当前时间的字符串
			 * */
			
			  return dateFormat.format(date);
		 
	 }
	 
	 /**
	  * 获得当前时间
	  * */
	 public static Date getDate(){
		 
		 /**
			 * 获取当前时间
			 * */
			  Date date= new  Date(  System.currentTimeMillis());
			  /**
			   * 返回当前时间
			   * */
			  return date;
		 
	 }
	 
	 public static Date getDate(String date_str){
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		 try{
			 return dateFormat.parse(date_str);
		 } catch(ParseException e){
			 System.out.println("出错");
			 
		 }
		 return null;
		 
		 
		 
	 }
	 
//	 public static Date getDate(String date_str) {
//
//			SimpleDateFormat dateFormat = new SimpleDateFormat(
//					"yyyy-MM-dd HH:mm:SS");
//			/**
//			 * 返回当前时间
//			 * */
//			try {
//				return dateFormat.parse(date_str);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				 System.out.println("报错了");
//			}
//			
//			return null;
//		}

	

	
}
