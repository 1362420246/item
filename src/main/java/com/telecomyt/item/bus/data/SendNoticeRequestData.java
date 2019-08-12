package com.telecomyt.item.bus.data;
/** 
* @author zpb
* @date 2018年1月16日 下午1:49:23 
* 类说明 
*/
public class SendNoticeRequestData {
	
	//方法名
	private String method;
	//请求参数
	private SendNoticeRequestParamData params;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public SendNoticeRequestParamData getParams() {
		return params;
	}
	public void setParams(SendNoticeRequestParamData params) {
		this.params = params;
	}
	
}
