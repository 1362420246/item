package com.telecomyt.item.bus.data;

import com.telecomyt.item.entity.TaskGroup;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
* @author zpb
* @date 2018年1月17日 下午2:48:23 
* 类说明 
*/
public class SendNoticeRequestMessageData implements Serializable {

	private static final long serialVersionUID = -3605467186540862389L;
	
	/**
	 * 通知主题
	 */
	private String noticeTitle;

	/**
	 * 通知内容
	 */
	private String notification;

	/**
	 * 通知时间
	 */
	private Date createTime;
	
	/**
	 * 通知类型 1 论坛通知 2 任务
	 */
	private int noticeType = 2;

	/**
	 * 任务通知内容-默认为空
	 */
	private Item item ;

	/**
	 * 通知点击跳转地址
	 */
	private String url;


	/**
	 *
	 * @param taskGroup 任务相关
	 * @param creator 创建的用户信息
	 * @param notification 通知的主题
	 * @param url  跳转页面的url
	 * @param role 区分角色----我接收的1，我创建的2
	 */
	public SendNoticeRequestMessageData(TaskGroup taskGroup, Creator creator, String notification , String url ,Integer role ) {
		this.noticeTitle = taskGroup.getSheetTitle();
		this.notification = notification;
		this.createTime = Date.from(taskGroup.getTaskCreatTime().atZone(ZoneId.systemDefault()).toInstant());
		this.item = Item.builder().creator(creator).title( taskGroup.getSheetTitle()).
				description(taskGroup.getSheetDescribe()).endTime(taskGroup.getTaskEndTime()).
				startTime(taskGroup.getTaskCreatTime()).build();
		//TODO 从配置里面读取  http://39.106.33.10:8083/taskDetail?valueType=%s&groupId=%s&groupStatus=%s&type=notice&current=false
		this.url = String.format(url ,role ,taskGroup.getGroupId() ,taskGroup.getGroupStatus());
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(int noticeType) {
		this.noticeType = noticeType;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
}
