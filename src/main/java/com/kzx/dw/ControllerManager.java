/**
* @Title: ControllerManager.java
* @Description: TODO(用一句话描述该文件做什么)
* @author tovin/xutaota2003@163.com 
* @date 2014年8月11日 下午4:41:18 
 */
package com.kzx.dw;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.kzx.dw.controller.ActionController;
import com.kzx.dw.util.NetUtil;
import com.kzx.dw.util.ScanUtil;
import com.kzx.dw.util.StringUtil;

public class ControllerManager {
	private Map<String, Class> controllers = new ConcurrentHashMap<String, Class>();
	
	public void init()
	{
		Set<Class<?>> classSet = ScanUtil.getClasses("com.kzx.dw.controller", false, null);
		for(Class cl : classSet)
		{
			String name = cl.getSimpleName();
			String action = name.substring(0, name.length()-10).toLowerCase();
			
			controllers.put(action,  cl);
		}
	}
	
	public void process(ActionContext context) throws Exception
	{
		String action = NetUtil.getStringParameter(context.getRequest(),"action","");
		String key = StringUtil.trim(action).toLowerCase();
		if(controllers.containsKey(key))
		{
			Object o = controllers.get(key).newInstance();
			Method method = controllers.get(key).getMethod("handleRequest", ActionContext.class);
			method.invoke(o, context);
		}
		else
			throw new Exception("action不存在");
	}


}

