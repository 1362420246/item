package com.telecomyt.item.bus.data;

import java.util.List;

/**
* @author zpb
* @date 2018年1月16日 下午1:54:07 
* 类说明 
*/
public class SendNoticeRequestParamData {
	
	private String packageName;
	private SendNoticeRequestMessageData message;
	private List<String> usernames;

	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public SendNoticeRequestMessageData getMessage() {
		return message;
	}

	public void setMessage(SendNoticeRequestMessageData message) {
		this.message = message;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}
}
