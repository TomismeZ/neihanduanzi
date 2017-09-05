package com.bmob.im.demo.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Fans extends BmobObject implements Serializable{
	private String userId;
	private String username;
	private String userimage;
	private String contentId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserimage() {
		return userimage;
	}
	public void setUserimage(String userimage) {
		this.userimage = userimage;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	@Override
	public String toString() {
		return "Fans [userId=" + userId + ", username=" + username
				+ ", userimage=" + userimage + ", contentId=" + contentId + "]";
	}

	
}
