package com.cmbc.android.service_assistant.api;

import java.util.List;
import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.entity.DeliveryOrderDetail;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author 民生电商android部 张超
 * @see 自定义listview适配器
 */

public class MyAdapter extends BaseAdapter {

	//这个adapter需要一个activity上下文
	private Activity activity;
	
	//这个adapter需要一个集合来存储商品信息对象
	private List<DeliveryOrderDetail> goodsList;
	
	
	//构造函数
	public MyAdapter(Activity activity , List<DeliveryOrderDetail> goodsList){
		this.activity = activity;
		this.goodsList = goodsList;
	}
	
	/**
	 * 定义ListView的数据的长度
	 * 如果为0，不显示信息
	 * 设置它是为了设置一次性显示多少个listItem，
	 * 读取出来的任务集合有多少条，就显示多少条
	 */
	@Override
	public int getCount() {
		if(goodsList != null){
			return goodsList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;//返回用户要修改的那个的ID，可以随便给个参数
	}

	/**
	 * 此方法返回的是ListView 的列表中某一行的view对象
	 * position 当前返回的view的索引位置
	 * convertView 缓存的view对象
	 * parent 就是ListView对象
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View  view= null;
		
		/*
		 * convertView是缓存 对象
		 * 如果还没有缓存对象，即表示手机刚启动ListView界面，
		 * 还没有开始操作，则新建对象,这样写是为了减少
		 * 一次性生成超出了屏幕显示范围的item，高效利用内存资源
		 */
		if (convertView == null) {
			//LayoutInflater，将xml代码变成view对象
			LayoutInflater inflater = activity.getLayoutInflater();
			
			//listview item的view,root置为null
			view = inflater.inflate(R.layout.item_delivery_order_info, null);
		
		}else{
			view = convertView;
		}
		
		//给item的控件实例化
		TextView goodsName = (TextView) view.findViewById(R.id.item_delivery_order_info_tv_goodsname);
		TextView goodsSum = (TextView) view.findViewById(R.id.item_delivery_order_info_tv_goodsSum);
		
		if(goodsList != null){
			//根据position对应集合中的对象位置，找到信息,设置到item中
			DeliveryOrderDetail deliveryOrderDetail = goodsList.get(position);
			goodsName.setText(deliveryOrderDetail.getSkuName());
			goodsSum.setText(deliveryOrderDetail.getOutQty());
		}
		
		return view;
	}

}
