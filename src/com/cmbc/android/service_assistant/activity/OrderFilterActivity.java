package com.cmbc.android.service_assistant.activity;

import java.nio.IntBuffer;

import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.TitleActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderFilterActivity extends TitleActivity  implements OnClickListener{
	//«Î«Û¬Î
	private static final int STORENAMECODE = 90;
	private static final int BEGINTIMECODE = 80;
	private static final int ENDTIMECODE = 70;
	private static final int ORDERSTATUCODE = 60;
	private static final int ORDERTYPECODE = 50;
	
	private View storeView;
	private View startTimeView;
	private View endTimeView;
	private View orderStatusView;
	private View orderTypeView;
	
	private TextView title,clear,ok,storeName,startTime,endTime,status,type;
	private ImageView backIcon;
	private Bundle bundle;
	
	private String[] typeArray = null;
	private String[] storeArray = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCustormLayout(R.layout.activity_order_filter, R.layout.titlebar_back_title);
		initial();
		bundle = getIntent().getExtras();
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
		
		title.setText("…∏—°");
			
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
		Intent timeIntent = new Intent(this, TimePickerActivity.class);
		switch (view.getId()) {
		case R.id.activity_order_filter_storeview:
			Intent storeIntent = new Intent(this, FilterActivity.class);
			bundle.putInt("requestCode", STORENAMECODE);
			storeIntent.putExtras(bundle);
			startActivityForResult(storeIntent, STORENAMECODE);
			break;
		case R.id.activity_order_filter_starttime:
			startActivityForResult(timeIntent, BEGINTIMECODE);
			break;
		case R.id.activity_order_filter_endtime:
			startActivityForResult(timeIntent, ENDTIMECODE);
			break;
		case R.id.activity_order_filter_orderstatus:
			Intent statusIntent = new Intent(this, FilterActivity.class);
			bundle.putInt("requestCode", ORDERSTATUCODE);
			statusIntent.putExtras(bundle);
			startActivityForResult(statusIntent, ORDERSTATUCODE);
			break;
		case R.id.activity_order_filter_ordertype:
			Intent typeIntent = new Intent(this, FilterActivity.class);
			bundle.putInt("requestCode", ORDERTYPECODE);
			typeIntent.putExtras(bundle);
			startActivityForResult(typeIntent,ORDERTYPECODE);
			break;
		case R.id.titlebar_back_title_img:
			finish();
			break;
		case R.id.activity_order_filter_tv_clear:
			clearAllCondition();
			break;
		case R.id.activity_order_filter_tv_ok:
			Intent filterResultIntent = new Intent();
			filterResultIntent.putExtra("store", (storeArray == null)?"" : storeArray[1]);
			filterResultIntent.putExtra("beginTime", startTime.getText().toString().trim());
			filterResultIntent.putExtra("endTime", endTime.getText().toString().trim());
			filterResultIntent.putExtra("orderType", (typeArray == null)?"":typeArray[1]);
			filterResultIntent.putExtra("orderStatus", status.getText().toString().trim());
			setResult(RESULT_OK, filterResultIntent);
			finish();
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case ORDERTYPECODE:
					typeArray = data.getStringArrayExtra("resultArray");
					type.setText(typeArray[0]);
				break;
			case STORENAMECODE:
					storeArray = data.getStringArrayExtra("resultArray");
					storeName.setText(storeArray[0]);
				break;
			case ORDERSTATUCODE:
					status.setText(data.getStringExtra("result"));
				break;
			case BEGINTIMECODE:
					startTime.setText(data.getStringExtra("result"));
				break;
			case ENDTIMECODE:
					endTime.setText(data.getStringExtra("result"));
				break;
			default:
				break;
			}
		}
		
	}
	
	
}
