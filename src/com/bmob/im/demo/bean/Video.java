package com.bmob.im.demo.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobObject;

/**
 *
 */
public class Video extends BmobObject implements Serializable{
	private String userAvatar;
	private String userName;
    private String videoUrl;
    private String coverUrl;
    private boolean isFirst;
    private int width;
    private int height;
    private String title;
    private int favoriteCount;
	private int  buryCount;
	private int commentCount;
	private String shareUrl;
	private int shareCount;
	private long userId;
	private boolean is_recomment;
    public Video() {
    }

    public Video(String videoUrl, String coverUrl) {
		super();
		this.videoUrl = videoUrl;
		this.coverUrl = coverUrl;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isIs_recomment() {
		return is_recomment;
	}

	public void setIs_recomment(boolean is_recomment) {
		this.is_recomment = is_recomment;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

	public Video(String userAvatar, String userName, String videoUrl,
			String coverUrl, int width, int height, String title,
			int favoriteCount, int buryCount, int commentCount,
			String shareUrl, int shareCount) {
		super();
		this.userAvatar = userAvatar;
		this.userName = userName;
		this.videoUrl = videoUrl;
		this.coverUrl = coverUrl;
		this.width = width;
		this.height = height;
		this.title = title;
		this.favoriteCount = favoriteCount;
		this.buryCount = buryCount;
		this.commentCount = commentCount;
		this.shareUrl = shareUrl;
		this.shareCount = shareCount;
		
	}
	public void parseJson(JSONObject group) throws JSONException{
		if(group!=null){
			videoUrl=group.getJSONObject("360p_video").getJSONArray("url_list").getJSONObject(0).getString("url");
			title=group.getString("title");
			height=group.getInt("video_height");
			favoriteCount=group.getInt("favorite_count");
			commentCount=group.getInt("comment_count");
			buryCount=group.getInt("bury_count");
			shareCount=group.getInt("share_count");
			shareUrl=group.getString("share_url");
			userId=group.getJSONObject("user").getLong("user_id");
			userName=group.getJSONObject("user").getString("name");
			userAvatar=group.getJSONObject("user").getString("avatar_url");
			coverUrl=group.getJSONObject("medium_cover").getJSONArray("url_list").getJSONObject(0).getString("url");
			width=group.getInt("video_width");
		}
	}

	
}
