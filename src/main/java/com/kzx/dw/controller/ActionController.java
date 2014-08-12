/**
* @Title: ActionController.java
* @Description: TODO(Controller父类)
* @author tovin/xutaota2003@163.com 
* @date 2014年8月11日 下午3:08:36 
 */
package com.kzx.dw.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kzx.dw.ActionContext;
import com.kzx.dw.util.NetUtil;

public abstract class ActionController {

	public void handleRequest(ActionContext context) throws IOException, ServletException 
	{}

}

