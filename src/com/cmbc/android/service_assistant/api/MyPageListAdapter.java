package com.cmbc.android.service_assistant.api;

import java.util.List;
import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.entity.Order;
import com.cmbc.android.service_assistant.entity.PurchaseOrder;
import com.cmbc.android.service_assistant.entity.Supplier;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * @author Administrator
 * ���adapter�Ḻ�𷵻ض����б��itemView�Ͳɹ����б��itemView
 */
public class MyPageListAdapter extends BaseAdapter {
	

	//����ͼƬ��Ӧֵ����
	private static final String CANCEL = "99";
//	private static final String STATUS_WAIT_DELIVERY = "12";
	private static final String STATUS_DELIVERY = "40";
	private static final String STATUS_WAIT_SIGN = "50";
	
//	private static final String STATUS_JSD = "21";
//	private static final String STATUS_WAIT_PRODUCE = "20";
//	private static final String STATUS_CHECKED = "10";
//	private static final String STATUS_MOUNT = "11";
	
	//�ɹ���ͼƬ��Ӧֵ����
	private static final String ALLREC = "30";
	private static final String SOMEREC = "31";
	private static final String CHECKED = "20";
	private static final String NOCHECKED = "10";
	
	//��ʶ��������itemView
	private static final int PURCHASE = 55;
	private static final int ORDER = 44;
	
	
	private List<PurchaseOrder> purchaseList;
	private List<Order> orderList;  
 	private Activity activity;
	private int flag;
	
	
	//ʹ�þ�̬viewHolder�����item�пؼ�id�����ã�������ÿ�ζ�findViewById�������Ч�ʣ���̬�ģ���֤�������ڴ��е�Ψһ��
	static class ViewHolder{
		TextView headInfo,extraInfo;
		ImageView img;
	}
	
	//����Ϊ�����б�����Ĺ��췽��
	@SuppressWarnings("unchecked")
	public MyPageListAdapter(Activity activity , List<? extends Object> list, int flag){
		this.activity = activity;
		this.flag = flag;
		switch (flag) {
		case PURCHASE:
			this.purchaseList = (List<PurchaseOrder>) list;
			break;
		case ORDER:
			this.orderList = (List<Order>) list;
			break;
		default:
			break;
		}
		
	}
	
	
	
	@Override
	public int getCount() {
		switch (flag) {
		case PURCHASE:
			if(purchaseList != null){
				return purchaseList.size();
			}
			return  0;
		case ORDER:
			if(orderList != null){
				return orderList.size();
			}
			return  0;
		default:
			return 0 ;
		}
	}

	@Override
	public Object getItem(int position) {
		switch (flag) {
		case PURCHASE:
			return purchaseList.get(position);
		case ORDER:
			return orderList.get(position);
		default:
			return null;
		}
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		/*
		 * convertView�ǻ��� ����
		 * �����û�л�����󣬼���ʾ�ֻ�������ListView���棬
		 * ��û�п�ʼ���������½�����,����д��Ϊ�˼���
		 * һ�������ɳ�������Ļ��ʾ��Χ��item�� ��Ч�����ڴ���Դ
		 */
		if (convertView == null) {
			//LayoutInflater����xml������view����
			LayoutInflater inflater = activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.item_search, parent, false);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.item_search_img);
			holder.headInfo = (TextView) convertView.findViewById(R.id.item_search_tv_orderno);
			holder.extraInfo = (TextView) convertView.findViewById(R.id.item_search_tv_ordertime);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		switch (flag) {
		case ORDER:
			Order order = orderList.get(position);
			holder.headInfo.setText(order.getRtn_no());
			holder.extraInfo.setText(OtherService.timeStamp(String.valueOf(order.getOrder_create_time())));
			String status = order.getStatusValue();
			if(status.equals(CANCEL)){//��ȡ��99
				holder.img.setImageResource(R.drawable.item_order_imgcancel);
			}else if(status.equals(STATUS_DELIVERY)){//�ѳ���40
				holder.img.setImageResource(R.drawable.item_order_img_2);
			}else if(status.equals(STATUS_WAIT_SIGN)){//��ǩ��50
				holder.img.setImageResource(R.drawable.item_order_imgsign);
			}else {
				holder.img.setImageResource(R.drawable.item_order_img);
			}
			break;
		case PURCHASE:
			PurchaseOrder purchaseOrder = purchaseList.get(position);
			holder.headInfo.setText(purchaseOrder.getPo_no());
			holder.extraInfo.setText(purchaseOrder.getSupplier_names());
			String state = purchaseOrder.getStatus();
			if(state.equals(CHECKED)){
				holder.img.setImageResource(R.drawable.checked);
			}else if(state.equals(NOCHECKED)){
				holder.img.setImageResource(R.drawable.notchecked);
			}else if(state.equals(ALLREC)){
				holder.img.setImageResource(R.drawable.all);
			}else if(state.equals(CANCEL)){
				holder.img.setImageResource(R.drawable.item_order_imgcancel);
			}else{
				holder.img.setImageResource(R.drawable.some);
			}
			break;
		default:
			break;
		}
		
		
		return convertView;
		
	}
	
	/** 
     * ����б���,��Ӻ����µļ��Ϸ��ظ�UI�㣬����
     * UI��ĵ��item�����Ҳ�������ӵ� 
     * @param item 
     */  
    public List<? extends Object> addItem(List<? extends Object> list2) {  
    	switch (flag) {
		case ORDER:
			 orderList.addAll( (List<Order>) list2);  
			 return orderList;
		case PURCHASE:
			purchaseList.addAll((List<PurchaseOrder>)list2);
			return purchaseList;
		default:
			return null;
		}
        
    }  

}
