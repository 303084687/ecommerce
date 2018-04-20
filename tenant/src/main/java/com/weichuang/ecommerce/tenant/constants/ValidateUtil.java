package com.weichuang.ecommerce.tenant.constants;

import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2017/2/8.
 */
public class ValidateUtil {
	public static final String SPECIAL_CHARACTER = "specialCharacter";
	public static final String LENGTH = "length";
	public static final String NUMBER = "number";
	public static final String EMPTY = "empty";
	
	public static String getValResult(String message, String valType, String str) {
		//        if();
		return null;
	}
	
	public static boolean valSpecialCharacter(String... strArray) {
		boolean flag = true;
		for (String str : strArray) {
			if (str != null && !"".equals(str.trim())) {
				if (!valSpecialCharacter(str)) {
					flag = false;
					continue;
				}
			}
		}
		return flag;
	}
	
	public static boolean valEmpty(String str) {
		//验证是否为空
		if (str == null)
			return false;
		return !StringUtils.isEmpty(str);
	}
	
	public static boolean valSpecialCharacter(String str) {
		//特殊字符验证
		boolean flag = true;
		if (str != null) {
			String regEx = "[`~!\\-#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			if (m.find()) {
				flag = false;
			}
		}
		return flag;
	}
	
	public static boolean valSpecialCharacterForVersion(String str) {
		boolean flag = true;
		//特殊字符验证
		if (str != null) {
			String regEx = "[`~!\\-#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			if (m.find()) {
				flag = false;
			}
		}
		return flag;
	}
	
	public static boolean valNumber(String str) {
		if (str == null || "".equals(str))
			return false;
		//判断非负整数
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		return isNum.matches();
	}
	
	public static boolean valNumber(String str, int maxNum) {
		if (str == null || "".equals(str))
			return false;
		//判断非负整数
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (maxNum <= 0) {
			return isNum.matches();
		} else {
			if (Integer.valueOf(str) > maxNum) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	public static boolean valLength(String str, String length) {
		//判断长度
		boolean flag = false;
		int strLength = str.length();
		if (strLength <= Integer.valueOf(length)) {
			flag = true;
		}
		return flag;
	}
	
	public static void main(String args[]) {
		try {
			String s[] = getMethodParamNames(ValidateUtil.class, "valSpecialCharacter");
			for (String a : s) {
				System.out.println(a);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String[] getMethodParamNames(Class clazz, String method) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get(clazz.getName());
		CtMethod cm = cc.getDeclaredMethod(method);
		return getMethodParamNames(cm);
	}
	
	protected static String[] getMethodParamNames(CtMethod cm) throws Exception {
		CtClass cc = cm.getDeclaringClass();
		MethodInfo methodInfo = cm.getMethodInfo();
		CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
		LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
		String[] paramNames = new String[cm.getParameterTypes().length];
		int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
		for (int i = 0; i < paramNames.length; i++)
			paramNames[i] = attr.variableName(i + pos);
		return paramNames;
	}
}
