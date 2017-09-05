package com.bmob.im.demo.bean;

import java.io.Serializable;
import cn.bmob.v3.BmobObject;
public class Comment extends BmobObject implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4766514780061558110L;
	private String text;
	private String createTime;
	private String userId;
	private String userProfileImageUrl;
	private String userName;
	private int diggCount;
	private int buryCount;	
	private String contentId;
	
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserProfileImageUrl() {
		return userProfileImageUrl;
	}

	public void setUserProfileImageUrl(String userProfileImageUrl) {
		this.userProfileImageUrl = userProfileImageUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getDiggCount() {
		return diggCount;
	}

	public void setDiggCount(int diggCount) {
		this.diggCount = diggCount;
	}

	
	public int getBuryCount() {
		return buryCount;
	}

	public void setBuryCount(int buryCount) {
		this.buryCount = buryCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Comment [text=" + text + ", createTime=" + createTime
				+ ", userId=" + userId + ", userProfileImageUrl="
				+ userProfileImageUrl + ", userName=" + userName
				+ ", diggCount=" + diggCount + ", buryCount=" + buryCount
				+ ", contentId=" + contentId + "]";
	}


}
