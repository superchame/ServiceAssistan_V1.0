package com.cmbc.android.service_assistant.activity;

import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.TitleActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderFilterActivity extends TitleActivity  implements OnClickListener{
	private View storeView;
	private View startTimeView;
	private View endTimeView;
	private View orderStatusView;
	private View orderTypeView;
	
	private TextView title,clear,ok,storeName,startTime,endTime,status,type;
	private ImageView backIcon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCustormLayout(R.layout.activity_order_filter, R.layout.titlebar_back_title);
		initial();
		
	}
	
	public void initial(){
		storeView = findViewById(R.id.activity_order_filter_storeview);
		startTimeView = findViewById(R.id.activity_order_filter_starttime);
		endTimeView = findViewById(R.id.activity_order_filter_endtime);
		orderStatusView = findViewById(R.id.activity_order_filter_orderstatus);
		orderTypeView = findViewById(R.id.activity_order_filter_ordertype);
		
		storeName = (TextView) findViewById(R.id.order_filter_storename);
		startTime = (TextView) findViewById(R.id.order_filter_starttime);
		endTime = (TextView) findViewById(R.id.order_filter_endtime);
		status = (TextView) findViewById(R.id.order_filter_status);
		type = (TextView) findViewById(R.id.order_filter_type);
		clear = (TextView) findViewById(R.id.activity_order_filter_tv_clear);
		ok = (TextView) findViewById(R.id.activity_order_filter_tv_ok);
		title = (TextView) findViewById(R.id.titlebar_back_title_tv);
		
		backIcon = (ImageView) findViewById(R.id.titlebar_back_title_img);
		
		title.setText("ɸѡ");
			
		backIcon.setOnClickListener(this);
		storeView.setOnClickListener(this);
		startTimeView.setOnClickListener(this);
		endTimeView.setOnClickListener(this);
		orderStatusView.setOnClickListener(this);
		orderTypeView.setOnClickListener(this);
		clear.setOnClickListener(this);
		ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.activity_order_filter_storeview:
			System.out.println("haha");
			break;
		case R.id.activity_order_filter_starttime:
			break;
		case R.id.activity_order_filter_endtime:
			break;
		case R.id.activity_order_filter_orderstatus:
			break;
		case R.id.activity_order_filter_ordertype:
			break;
		case R.id.titlebar_back_title_img:
			finish();
			break;
		case R.id.activity_order_filter_tv_clear:
			break;
		case R.id.activity_order_filter_tv_ok:
			break;
		default:
			break;
		}
		
	}
	
	public void clearAllCondition(){
		storeName.setText("");
		startTime.setText("");
		endTime.setText("");
		status.setText("");
		type.setText("");
		
	}
}
