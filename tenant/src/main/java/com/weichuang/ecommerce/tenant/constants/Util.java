package com.weichuang.ecommerce.tenant.constants;

import java.util.ArrayList;
import java.util.List;

public class Util {
	//去掉list中的相同项
	public static List<Integer> removeSameItem(List<String> list) {
		List<Integer> difList = new ArrayList<Integer>();
		for (String t : list) {
			if (t != null && !difList.contains(t)) {
				difList.add(Integer.parseInt(t));
			}
		}
		return difList;
	}
}
