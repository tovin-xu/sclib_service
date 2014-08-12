/**
* @Title: ActionContext.java
* @Description: TODO(上下文环境变量)
* @author tovin/xutaota2003@163.com 
* @date 2014年8月11日 下午3:13:19 
 */
package com.kzx.dw;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kzx.dw.util.JsonUtil;

public class ActionContext {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private boolean aborted = false;

	public ActionContext(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	public final void abort(){
		this.aborted = true;
	}

	public final boolean isAbort(){
		return this.aborted;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public void writeJson(Object jsonobj) throws IOException {
		try {
			response.getWriter().write(JsonUtil.toJson(jsonobj));
			response.getWriter().flush();
		} catch(Throwable e) {
			abort();
		} 
	}

	
}

