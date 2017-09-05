package com.bmob.im.demo.bean;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.datatype.BmobRelation;

/** 重载BmobChatUser对象：若还有其他需要增加的属性可在此添加

  */
public class User extends BmobChatUser {

	
	
	/**
	 * //性别-true-男
	 */
	private Boolean sex;
	
	
	/**
	 * 地理坐标
	 */
	private BmobGeoPoint location;//
	private String userImage;

	
//	 /* 粉丝 */
//    private BmobRelation fans ;
//    /* 所关注人 */
//    private BmobRelation focus ;
	//粉丝数量
	private int fansCount;
	//关注数量
	private int focusCount;
    //角色
    private Boolean admin;
    
	//审核是否通过
    private Boolean pass;
    
	public Boolean getPass() {
		return pass;
	}
	public void setPass(Boolean pass) {
		this.pass = pass;
	}
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
	public BmobGeoPoint getLocation() {
		return location;
	}
	public void setLocation(BmobGeoPoint location) {
		this.location = location;
	}
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public String getUserImage() {
		return userImage;
	}
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public int getFansCount() {
		return fansCount;
	}
	public void setFansCount(int fansCount) {
		this.fansCount = fansCount;
	}
	public int getFocusCount() {
		return focusCount;
	}
	public void setFocusCount(int focusCount) {
		this.focusCount = focusCount;
	}
	@Override
	public String toString() {
		return "User [sex=" + sex + ", location=" + location + ", userImage="
				+ userImage + ", fansCount=" + fansCount + ", focusCount="
				+ focusCount + ", admin=" + admin + ", pass=" + pass + "]";
	}
	
	
	
	
}
