package com.cmbc.android.service_assistant.entity;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

public class DeliveryOrder implements Parcelable {
	
	private String outNo;
	private String shopName;
	private int goodsSum;
	private int totalGoodsSum;
	private int orderCnt;
	private List<GoodsDetail> goodsList;
	private String skuName;
	private String outQty;
	private int skuCode;
	
	

	public String getOutNo() {
		return outNo;
	}

	public void setOutNo(String outNo) {
		this.outNo = outNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getGoodsSum() {
		return goodsSum;
	}

	public void setGoodsSum(int goodsSum) {
		this.goodsSum = goodsSum;
	}

	public int getTotalGoodsSum() {
		return totalGoodsSum;
	}

	public void setTotalGoodsSum(int totalGoodsSum) {
		this.totalGoodsSum = totalGoodsSum;
	}

	public int getOrderCnt() {
		return orderCnt;
	}

	public void setOrderCnt(int orderCnt) {
		this.orderCnt = orderCnt;
	}

	public List<GoodsDetail> getGoodsList() {
		return goodsList;
	}
 
	public void setGoodsList(List<GoodsDetail> goodsList) {
		this.goodsList = goodsList;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getOutQty() {
		return outQty;
	}

	public void setOutQty(String outQty) {
		this.outQty = outQty;
	}

	public int getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(int skuCode) {
		this.skuCode = skuCode;
	}
	
	
	

	public DeliveryOrder() {
		
	}

	
	
	
	public DeliveryOrder(String outNo, String shopName, int goodsSum,
			int totalGoodsSum, int orderCnt, List<GoodsDetail> goodsList,
			String skuName, String outQty, int skuCode) {
		super();
		this.outNo = outNo;
		this.shopName = shopName;
		this.goodsSum = goodsSum;
		this.totalGoodsSum = totalGoodsSum;
		this.orderCnt = orderCnt;
		this.goodsList = goodsList;
		this.skuName = skuName;
		this.outQty = outQty;
		this.skuCode = skuCode;
	}
	
	public DeliveryOrder(Parcel in){
		//顺序要和writeToParcel写的顺序一样
		this.outNo = in.readString();
		this.shopName = in.readString();
		this.goodsSum = in.readInt();
		this.totalGoodsSum = in.readInt();
		this.orderCnt = in.readInt();
		
		this.goodsList = new ArrayList<GoodsDetail>();
		in.readList(goodsList, ClassLoader.getSystemClassLoader());
		
		this.skuName = in.readString();
		this.outQty = in.readString();
		this.skuCode = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(outNo);
		dest.writeString(shopName);
		dest.writeInt(goodsSum);
		dest.writeInt(totalGoodsSum);
		dest.writeInt(orderCnt);
		dest.writeList(goodsList);
		dest.writeString(skuName);
		dest.writeString(outQty);
		dest.writeInt(skuCode);
	}

	public static final Parcelable.Creator<DeliveryOrder> CREATOR = new Parcelable.Creator<DeliveryOrder>() {

		@Override
		public DeliveryOrder createFromParcel(Parcel in) {
			return new DeliveryOrder(in);
		}

		@Override
		public DeliveryOrder[] newArray(int size) {
			return new DeliveryOrder[size];
		}
		
	};



	@Override
	public String toString() {
		return "DeliveryOrder [outNo=" + outNo + ", shopName=" + shopName
				+ ", goodsSum=" + goodsSum + ", totalGoodsSum=" + totalGoodsSum
				+ ", orderCnt=" + orderCnt + ", goodsList=" + goodsList
				+ ", skuName=" + skuName + ", outQty=" + outQty + ", skuCode="
				+ skuCode + "]";
	}
	
	
}
