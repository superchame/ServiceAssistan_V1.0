package com.cmbc.android.service_assistant.api;

import java.util.List;
import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.entity.Order;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyPageListAdapter extends BaseAdapter {
	private List<Order> orderList;  
	private Activity activity;
	
	//常量
	private static final String STATUS_CANCEL = "99";
//	private static final String STATUS_WAIT_DELIVERY = "12";
	private static final String STATUS_DELIVERY = "40";
	private static final String STATUS_WAIT_SIGN = "50";
	
//	private static final String STATUS_JSD = "21";
//	private static final String STATUS_WAIT_PRODUCE = "20";
//	private static final String STATUS_CHECKED = "10";
//	private static final String STATUS_MOUNT = "11";
	
	public MyPageListAdapter(Activity activity , List<Order> orderList){
		this.orderList = orderList;
		this.activity = activity;
	}
	
	@Override
	public int getCount() {
		if(orderList != null){
			return orderList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return orderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

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
			view = inflater.inflate(R.layout.item_search, null);
		
		}else{
			view = convertView;
		}
		
		//给item的控件实例化
		ImageView img = (ImageView) view.findViewById(R.id.item_search_img);
		TextView tv_orderno = (TextView) view.findViewById(R.id.item_search_tv_orderno);
		TextView tv_ordertime = (TextView) view.findViewById(R.id.item_search_tv_ordertime);
		
		
		if(orderList != null){
			//根据position对应集合中的对象位置，找到信息,设置到item中
			Order order = orderList.get(position);
			String status = order.getStatusValue();
			if(status.equals(STATUS_CANCEL)){//已取消99
				img.setImageResource(R.drawable.item_order_imgcancel);
			}else if(status.equals(STATUS_DELIVERY)){//已出库40
				img.setImageResource(R.drawable.item_order_img_2);
			}else if(status.equals(STATUS_WAIT_SIGN)){//待签收50
				img.setImageResource(R.drawable.item_order_imgsign);
			}
			tv_orderno.setText(order.getOrder_no());
			tv_ordertime.setText(String.valueOf(order.getOrder_create_time()));
		}
		
		return view;
		
	}
	
	/** 
     * 添加列表项 
     * @param item 
     */  
    public void addItem(List<Order> orderList2) {  
        orderList.addAll(orderList2);  
    }  

}
