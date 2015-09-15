package com.cmbc.android.service_assistant.entity;


public class Order {
	private long order_create_time;
	private String sendName;
	private String status;
	private String recvName;
	private String rtn_no;
	private String org_abc;
	private String statusValue;
	private String order_no;
	private String order_type;
	
	private String sku_name;
	private String sale_price;
	private String total_price;
	private String qty;
	
	
	
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getSku_name() {
		return sku_name;
	}
	public void setSku_name(String sku_name) {
		this.sku_name = sku_name;
	}
	public String getSale_price() {
		return sale_price;
	}
	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}
	public String getTotal_price() {
		return total_price;
	}
	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}
	public long getOrder_create_time() {
		return order_create_time;
	}
	public void setOrder_create_time(long order_create_time) {
		this.order_create_time = order_create_time;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRecvName() {
		return recvName;
	}
	public void setRecvName(String recvName) {
		this.recvName = recvName;
	}
	public String getRtn_no() {
		return rtn_no;
	}
	public void setRtn_no(String rtn_no) {
		this.rtn_no = rtn_no;
	}
	public String getOrg_abc() {
		return org_abc;
	}
	public void setOrg_abc(String org_abc) {
		this.org_abc = org_abc;
	}
	public String getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public Order(long order_create_time, String sendName, String status,
			String recvName, String rtn_no, String org_abc, String statusValue,
			String order_no, String order_type) {
		super();
		this.order_create_time = order_create_time;
		this.sendName = sendName;
		this.status = status;
		this.recvName = recvName;
		this.rtn_no = rtn_no;
		this.org_abc = org_abc;
		this.statusValue = statusValue;
		this.order_no = order_no;
		this.order_type = order_type;
	}
	public Order() {
	}
	@Override
	public String toString() {
		return "Order [order_create_time=" + order_create_time + ", sendName="
				+ sendName + ", status=" + status + ", recvName=" + recvName
				+ ", rtn_no=" + rtn_no + ", org_abc=" + org_abc
				+ ", statusValue=" + statusValue + ", order_no=" + order_no
				+ ", order_type=" + order_type + ", sku_name=" + sku_name
				+ ", sale_price=" + sale_price + ", total_price=" + total_price
				+ ", qty=" + qty + "]";
	}
	
	
	
	
}
