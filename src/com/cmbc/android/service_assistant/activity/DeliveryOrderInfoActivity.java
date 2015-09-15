package com.cmbc.android.service_assistant.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.MyAdapter;
import com.cmbc.android.service_assistant.api.MyHandler;
import com.cmbc.android.service_assistant.api.NetService;
import com.cmbc.android.service_assistant.api.OtherService;
import com.cmbc.android.service_assistant.api.TitleActivity;
import com.cmbc.android.service_assistant.entity.DeliveryOrder;
import com.cmbc.android.service_assistant.entity.GoodsDetail;
import com.cmbc.android.service_assistant.entity.User;


@SuppressLint("HandlerLeak")
public class DeliveryOrderInfoActivity extends TitleActivity implements OnClickListener,OnItemClickListener{
		
	private ImageView backIcon, actionIcon;
	private TextView title_tv,tv_outNo,tv_shopName,tv_orderCnt,tv_goodsSum,tv_totalGoodsSum;
	private User userInfo;
	private DeliveryOrder deliveryOrder;
	private GoodsDetail deliveryOrderDetail;
	private Bundle bundle;
	private List<GoodsDetail> goodsList = new ArrayList<GoodsDetail>();
	private List<GoodsDetail> goodsDetailList;
	private ListView listView;
	private MyAdapter myAdapter;
	private Map<String, Object> map;
	
	private MyHandler handler = new MyHandler(this){
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			super.handleMessage(msg,null);
			if(passPort){
				map = (Map<String, Object>) msg.obj;
				goodsDetailList = (List<GoodsDetail>) map.get("goodsDetailList");
				deliveryOrderDetail = goodsDetailList.get(0);//一条item只对应一条信息，这里写成0
				System.out.println(deliveryOrderDetail.toString());
				OtherService.createViewDialog(DeliveryOrderInfoActivity.this,
						deliveryOrderDetail.getSkuCode(),		
						deliveryOrderDetail.getSkuName(),
						deliveryOrderDetail.getSpuCode(),
						deliveryOrderDetail.getOutQty(),
						deliveryOrderDetail.getUnit()
						);
			}else{
				
			}
				
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setCustormLayout(R.layout.activity_delivery_order_info, R.layout.titlebar_back_title_action);
		initial();
		title_tv.setText("出库单详情");
		actionIcon.setImageResource(R.drawable.rec);
		
		bundle = getIntent().getExtras();
		userInfo = bundle.getParcelable("userInfo");
		deliveryOrder = bundle.getParcelable("deliveryOrder");
		
		//从bundle中获取deliveryOrderDetail的对象，放入集合中
		for(int i = 1 ; i < deliveryOrder.getGoodsSum()+1 ; i++){
			goodsList.add((GoodsDetail) bundle.getParcelable(("deliveryOrderDetail"+i)));
		}
		
		tv_outNo.setText(deliveryOrder.getOutNo());
		tv_shopName.setText(deliveryOrder.getShopName());
		tv_orderCnt.setText(String.valueOf(deliveryOrder.getOrderCnt()));
		tv_goodsSum.setText(String.valueOf(deliveryOrder.getGoodsSum()));
		tv_totalGoodsSum.setText(String.valueOf(deliveryOrder.getTotalGoodsSum()));
		
		myAdapter = new MyAdapter(this, goodsList);
		listView.setAdapter(myAdapter);
		
		backIcon.setOnClickListener(this);
		actionIcon.setOnClickListener(this);
		
		listView.setOnItemClickListener(this);
	}
	
	public void initial(){
		backIcon = (ImageView) findViewById(R.id.titlebar_back_title_action_backimg);
		title_tv = (TextView) findViewById(R.id.titlebar_back_title_action_tv);
		actionIcon = (ImageView) findViewById(R.id.titlebar_back_title_action_actionimg);
		tv_outNo = (TextView) findViewById(R.id.activity_delivery_order_info_tv_outno);
		tv_shopName = (TextView) findViewById(R.id.activity_delivery_order_info_tv_shopname);
		tv_goodsSum = (TextView) findViewById(R.id.activity_delivery_order_info_tv_goodssum);
		tv_orderCnt = (TextView) findViewById(R.id.activity_delivery_order_info_tv_ordercnt);
		tv_totalGoodsSum = (TextView) findViewById(R.id.activity_delivery_order_info_tv_totalgoodssum);
		listView = (ListView) findViewById(R.id.activity_delivery_order_info_lv);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.titlebar_back_title_action_backimg:
			finish();
			break;
		case R.id.titlebar_back_title_action_actionimg:
			
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		deliveryOrderDetail = goodsList.get(position);
		//调用网络查询出库单详情服务
		NetService.getDeliveryOrderDetail(handler, userInfo.getLoginName(), userInfo.getToken(), 
				deliveryOrder.getOutNo(), deliveryOrderDetail.getSkuCode());
	}
}
