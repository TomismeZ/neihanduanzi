package com.bmob.im.demo.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class ImageInfo extends BmobObject implements Serializable{
	private String userAvatar;
	private String userName;
	private String content;
	private String largeImageUri;
	private String middleImageUri;
	private boolean is_recomment;
	public int is_gif;
	private int favoriteCount;
	private int  buryCount;
	private int commentCount;
	private String shareUrl;
	private int shareCount;
	private long userId;
	private long groupId;
	
	public void parseImage(Image imageData,int position){
		userAvatar=imageData.data.data.get(position).group.user.avatar_url;
		userName=imageData.data.data.get(position).group.user.name;
		content=imageData.data.data.get(position).group.text;		
		largeImageUri=imageData.data.data.get(position).group.large_image.uri;
		middleImageUri=imageData.data.data.get(position).group.middle_image.uri;
		is_gif=imageData.data.data.get(position).group.is_gif;
		favoriteCount=imageData.data.data.get(position).group.favorite_count;
		buryCount=imageData.data.data.get(position).group.bury_count;
		commentCount=imageData.data.data.get(position).group.comment_count;
		shareUrl=imageData.data.data.get(position).group.share_url;
		userId=imageData.data.data.get(position).group.user.user_id;
		groupId=imageData.data.data.get(position).group.group_id;
	}

	public boolean isIs_recomment() {
		return is_recomment;
	}

	public void setIs_recomment(boolean is_recomment) {
		this.is_recomment = is_recomment;
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

	public String getLargeImageUri() {
		return largeImageUri;
	}

	public void setLargeImageUri(String largeImageUri) {
		this.largeImageUri = largeImageUri;
	}

	public String getMiddleImageUri() {
		return middleImageUri;
	}

	public void setMiddleImageUri(String middleImageUri) {
		this.middleImageUri = middleImageUri;
	}

	public int getIs_gif() {
		return is_gif;
	}

	public void setIs_gif(int is_gif) {
		this.is_gif = is_gif;
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

	public int getShareCount() {
		return shareCount;
	}

	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	
	

}