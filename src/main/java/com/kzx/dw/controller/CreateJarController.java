/**
* @Title: CreateJarController.java
* @Description: TODO(javabean自动编译打包)
* @author tovin/xutaota2003@163.com 
* @date 2014年8月11日 下午5:16:34 
 */
package com.kzx.dw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kzx.dw.ActionContext;
import com.kzx.dw.bean.Result;

public class CreateJarController extends ActionController{
	/* (non-Javadoc)
	 * @see com.kzx.dw.controller.ActionController#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, com.kzx.dw.ActionContext)
	 */
	@Override
	public void handleRequest(ActionContext context) throws IOException, ServletException {
		// TODO Auto-generated method stub
		Result result = new Result(Result.SUCCESS);
		result.setMsg("diyige");
		context.writeJson(result);
	}
}

