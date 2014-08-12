/**
* @Title: DispatchFilter.java
* @Description: TODO(处理请求分发的filter)
* @author tovin/xutaota2003@163.com 
* @date 2014年8月11日 上午10:29:47 
 */
package com.kzx.dw.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kzx.dw.ActionContext;
import com.kzx.dw.ControllerManager;
import com.kzx.dw.bean.Result;
import com.kzx.dw.controller.ActionController;
import com.kzx.dw.util.JsonUtil;
import com.kzx.dw.util.NetUtil;
import com.kzx.dw.util.StringUtil;


public class DispatchFilter  implements Filter{
	private static final String XML="xml";
	private static final String JSON="json";
	
	private ControllerManager manager;
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		servletRequest.setCharacterEncoding("UTF-8");
		servletResponse.setCharacterEncoding("UTF-8");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        String type = NetUtil.getStringParameter(request, "type", JSON);
        Result result = new Result();

        if(!type.equalsIgnoreCase(JSON) && !type.equalsIgnoreCase(XML))
        {
             result.setStatus(Result.FAILED);
             result.setMsg("use json or xml for type");     
             response.getWriter().write(JsonUtil.toJson(result));
             response.getWriter().flush();
        }
             
        else
        {

        	try {
            	ActionContext context = new ActionContext(request, response);
                 manager.process(context);
                 if(context.isAbort())
                 {
                 	result.setStatus(Result.FAILED);
                     result.setMsg("context abort");     
                     response.getWriter().write(JsonUtil.toJson(result));
                     response.getWriter().flush();
                 }
			} catch (Exception e) {
				result.setStatus(Result.FAILED);
	             result.setMsg(e.getMessage());     
	             response.getWriter().write(JsonUtil.toJson(result));
	             response.getWriter().flush();
			}    	
        }  

	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		manager = new ControllerManager();
		manager.init();
	}
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}

