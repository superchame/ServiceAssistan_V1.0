package com.cmbc.android.service_assistant.entity;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;


public class User implements Parcelable{
	private String token;
	private String loginName;
	private String orgId;
	private String orgName;
	private String realName;
	private String id;
	private List<Object> orgTypeList;
	private String type;
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Object> getOrgTypeList() {
		return orgTypeList;
	}
	public void setOrgTypeList(List<Object> orgTypeList) {
		this.orgTypeList = orgTypeList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User(String token, String loginName, String orgId, String orgName,
			String realName, String id, List<Object> orgTypeList, String type) {
		super();
		this.token = token;
		this.loginName = loginName;
		this.orgId = orgId;
		this.orgName = orgName;
		this.realName = realName;
		this.id = id;
		this.orgTypeList = orgTypeList;
		this.type = type;
	}
	
	
	public User() {
		
	}
	
	@Override
	public String toString() {
		return "User [token=" + token + ", loginName=" + loginName + ", orgId="
				+ orgId + ", orgName=" + orgName + ", realName=" + realName
				+ ", id=" + id + ", orgTypeList=" + orgTypeList + ", type="
				+ type + "]";
	}
	
	public User(Parcel in){
		//顺序要和writeToParcel写的顺序一样
		this.token = in.readString();
		this.loginName = in.readString();
		this.orgId = in.readString();
		this.orgName = in.readString();
		this.realName = in.readString();
		this.id = in.readString();
		
		//序列化集合
		this.orgTypeList = new ArrayList<Object>();
		in.readList(orgTypeList, ClassLoader.getSystemClassLoader() );
		
		this.type = in.readString();
		
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(token);
		dest.writeString(loginName);
		dest.writeString(orgId);
		dest.writeString(orgName);
		dest.writeString(realName);
		dest.writeString(id);
		dest.writeList(orgTypeList);
		dest.writeString(type);
		
	}
	
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
		
	};
	
	
	
	
}
