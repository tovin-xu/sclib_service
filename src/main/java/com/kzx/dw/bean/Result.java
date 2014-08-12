/**
* @Title: Result.java
* @Description: TODO(请求处理结果类)
* @author tovin/xutaota2003@163.com 
* @date 2014年8月11日 下午12:02:31 
 */
package com.kzx.dw.bean;

public class Result {
	public static final int SUCCESS = 0;
	
	public static final int FAILED = -1;

	private int status;
	private String msg;
	private String data;
	
	public Result(int status) {
		this.status = status;
	}
	
	public Result() {
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		if( msg!=null && !msg.trim().isEmpty())
			this.msg = (status >= 0 ? "成功（" : "失败（") + msg +")";
		else
			this.msg = (status >= 0 ? "成功" : "失败");
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
