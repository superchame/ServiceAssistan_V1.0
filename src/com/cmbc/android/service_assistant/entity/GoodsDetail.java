package com.cmbc.android.service_assistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class GoodsDetail implements Parcelable {
	private String unit;
	private String skuName;
	private String spuCode;
	private String outQty;
	private String skuCode;
	

	public GoodsDetail(){
		
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSpuCode() {
		return spuCode;
	}

	public void setSpuCode(String spuCode) {
		this.spuCode = spuCode;
	}

	public String getOutQty() {
		return outQty;
	}

	public void setOutQty(String outQty) {
		this.outQty = outQty;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public GoodsDetail(String unit, String skuName, String spuCode,
			String outQty, String skuCode) {
		super();
		this.unit = unit;
		this.skuName = skuName;
		this.spuCode = spuCode;
		this.outQty = outQty;
		this.skuCode = skuCode;
	}


	public GoodsDetail(Parcel in){
		unit = in.readString();
		skuName = in.readString();
		spuCode = in.readString();
		outQty = in.readString();
		skuCode = in.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(unit);
		dest.writeString(skuName);
		dest.writeString(spuCode);
		dest.writeString(outQty);
		dest.writeString(skuCode);
	}
	
	public static final Parcelable.Creator<GoodsDetail> CREATOR = new Parcelable.Creator<GoodsDetail>() {

		@Override
		public GoodsDetail createFromParcel(Parcel in) {
			return new GoodsDetail(in);
		}

		@Override
		public GoodsDetail[] newArray(int size) {
			// TODO Auto-generated method stub
			return new GoodsDetail[size];
		}
		
	};

	@Override
	public String toString() {
		return "DeliveryOrderDetail [unit=" + unit + ", skuName=" + skuName
				+ ", spuCode=" + spuCode + ", outQty=" + outQty + ", skuCode="
				+ skuCode + "]";
	}
	
	
	
	
	
}
