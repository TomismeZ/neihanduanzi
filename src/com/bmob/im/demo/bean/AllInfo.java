package com.bmob.im.demo.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class AllInfo extends BmobObject implements Serializable{
	private String hotBarId;
	private String userAvatar;
	private String userName;
	private String content;
	private String middleImageUri;
	private String largeImageUri;
	private String videoUrl;
    private String coverUrl;
	private int favoriteCount;
	private int  buryCount;
	private int commentCount;
	private int shareCount;
	private String userId;
	private int width;
    private int height;
    private boolean is_recomment;
    
	public boolean isIs_recomment() {
		return is_recomment;
	}
	public void setIs_recomment(boolean is_recomment) {
		this.is_recomment = is_recomment;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getLargeImageUri() {
		return largeImageUri;
	}
	public void setLargeImageUri(String largeImageUri) {
		this.largeImageUri = largeImageUri;
	}
	public String getHotBarId() {
		return hotBarId;
	}
	public void setHotBarId(String hotBarId) {
		this.hotBarId = hotBarId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
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
	
	public String getMiddleImageUri() {
		return middleImageUri;
	}
	public void setMiddleImageUri(String middleImageUri) {
		this.middleImageUri = middleImageUri;
	}
	
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
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

	
	

}
