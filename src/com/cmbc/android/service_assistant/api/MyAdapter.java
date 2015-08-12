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
 * @author ��������android�� �ų�
 * @see �Զ���listview������
 */

public class MyAdapter extends BaseAdapter {

	//���adapter��Ҫһ��activity������
	private Activity activity;
	
	//���adapter��Ҫһ���������洢��Ʒ��Ϣ����
	private List<DeliveryOrderDetail> goodsList;
	
	
	//���캯��
	public MyAdapter(Activity activity , List<DeliveryOrderDetail> goodsList){
		this.activity = activity;
		this.goodsList = goodsList;
	}
	
	/**
	 * ����ListView�����ݵĳ���
	 * ���Ϊ0������ʾ��Ϣ
	 * ��������Ϊ������һ������ʾ���ٸ�listItem��
	 * ��ȡ���������񼯺��ж�����������ʾ������
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
		return 0;//�����û�Ҫ�޸ĵ��Ǹ���ID����������������
	}

	/**
	 * �˷������ص���ListView ���б���ĳһ�е�view����
	 * position ��ǰ���ص�view������λ��
	 * convertView �����view����
	 * parent ����ListView����
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View  view= null;
		
		/*
		 * convertView�ǻ��� ����
		 * �����û�л�����󣬼���ʾ�ֻ�������ListView���棬
		 * ��û�п�ʼ���������½�����,����д��Ϊ�˼���
		 * һ�������ɳ�������Ļ��ʾ��Χ��item����Ч�����ڴ���Դ
		 */
		if (convertView == null) {
			//LayoutInflater����xml������view����
			LayoutInflater inflater = activity.getLayoutInflater();
			
			//listview item��view,root��Ϊnull
			view = inflater.inflate(R.layout.item_delivery_order_info, null);
		
		}else{
			view = convertView;
		}
		
		//��item�Ŀؼ�ʵ����
		TextView goodsName = (TextView) view.findViewById(R.id.item_delivery_order_info_tv_goodsname);
		TextView goodsSum = (TextView) view.findViewById(R.id.item_delivery_order_info_tv_goodsSum);
		
		if(goodsList != null){
			//����position��Ӧ�����еĶ���λ�ã��ҵ���Ϣ,���õ�item��
			DeliveryOrderDetail deliveryOrderDetail = goodsList.get(position);
			goodsName.setText(deliveryOrderDetail.getSkuName());
			goodsSum.setText(deliveryOrderDetail.getOutQty());
		}
		
		return view;
	}

}
