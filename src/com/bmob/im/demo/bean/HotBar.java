package com.bmob.im.demo.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class HotBar extends BmobObject implements Serializable{
	private String avatar;
	private String name;
	private String describe;
	private int subscribeCount;
	private int noteCount;
	private boolean isSubscribe;
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public int getSubscribeCount() {
		return subscribeCount;
	}
	public void setSubscribeCount(int subscribeCount) {
		this.subscribeCount = subscribeCount;
	}
	public int getNoteCount() {
		return noteCount;
	}
	public void setNoteCount(int noteCount) {
		this.noteCount = noteCount;
	}
	public boolean isSubscribe() {
		return isSubscribe;
	}
	public void setSubscribe(boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	@Override
	public String toString() {
		return "HotBar [avatar=" + avatar + ", name=" + name + ", describe="
				+ describe + ", subscribeCount=" + subscribeCount
				+ ", noteCount=" + noteCount + ", isSubscribe=" + isSubscribe
				+ "]";
	}
	
}
