package com.bmob.im.demo.bean;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.datatype.BmobRelation;

/** ����BmobChatUser����������������Ҫ���ӵ����Կ��ڴ����

  */
public class User extends BmobChatUser {

	
	
	/**
	 * //�Ա�-true-��
	 */
	private Boolean sex;
	
	
	/**
	 * ��������
	 */
	private BmobGeoPoint location;//
	private String userImage;

	
//	 /* ��˿ */
//    private BmobRelation fans ;
//    /* ����ע�� */
//    private BmobRelation focus ;
	//��˿����
	private int fansCount;
	//��ע����
	private int focusCount;
    //��ɫ
    private Boolean admin;
    
	//����Ƿ�ͨ��
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
