package com.bmob.im.demo.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Joke extends BmobObject implements Serializable{
	
	private String userAvatar;
	private String userName;
	private String content;
	private int favoriteCount;
	private int  buryCount;
	private int commentCount;
	private String shareUrl;
	private int shareCount;
	private long userId;
	private long groupId;
	private boolean is_recomment;
	
	public boolean isIs_recomment() {
		return is_recomment;
	}
	public void setIs_recomment(boolean is_recomment) {
		this.is_recomment = is_recomment;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	public String getUserAvatar() {
		return userAvatar;
	}
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getFavoriteCount() {
		return favoriteCount;
	}
	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	public int getBuryCount() {
		return buryCount;
	}
	public void setBuryCount(int buryCount) {
		this.buryCount = buryCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	@Override
	public String toString() {
		return "Joke [userAvatar=" + userAvatar + ", userName=" + userName
				+ ", content=" + content + ", favoriteCount=" + favoriteCount
				+ ", buryCount=" + buryCount + ", commentCount=" + commentCount
				+ ", shareUrl=" + shareUrl + ", shareCount=" + shareCount + "]";
	}
	
	
	

}
