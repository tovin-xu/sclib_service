/**
* @Title: JsonUtil.java
* @Description: TODO(Json处理类)
* @author tovin/xutaota2003@163.com 
* @date 2014年8月11日 下午2:30:28 
 */
package com.kzx.dw.util;

import com.google.gson.Gson;
import com.kzx.dw.bean.Result;

public class JsonUtil {
	private static final Gson gson = new Gson();

	public static <T> String toJson(T t) {
		return gson.toJson(t);
	}
		
		
	public static <T> T fromJson(String json, Class<T> c) {
		return gson.fromJson(json, c);
	}
		
	public static void main(String[] args) {
		
		System.out.println(toJson(Result.SUCCESS));			
	}
}

