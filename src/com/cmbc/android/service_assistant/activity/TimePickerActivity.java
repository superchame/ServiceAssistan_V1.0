package com.cmbc.android.service_assistant.activity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.cmbc.android.service_assistant.R;


public class TimePickerActivity extends Activity implements OnClickListener{
	private TextView ok;
	private ImageView backIcon;
	private DatePicker datePicker;
	private String time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_time_picker);
		initial();
	}
	
	private void initial(){
		ok = (TextView) findViewById(R.id.ok);
		backIcon = (ImageView) findViewById(R.id.backIcon);
		datePicker = (DatePicker) findViewById(R.id.datePicker);
		
		ok.setOnClickListener(this);
		backIcon.setOnClickListener(this);
		
		Calendar calendar = Calendar.getInstance();
		final int y = calendar.get(Calendar.YEAR);
		final int m = calendar.get(Calendar.MONTH);
		final int d = calendar.get(Calendar.DAY_OF_MONTH);
		 time = y+"-"+(m+1)+"-"+d;
		//获取当前日期
		datePicker.init(y, m, d, new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year, int month, int day) {
				time = year+"-"+(month+1)+"-"+day;
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.backIcon:
			finish();
			break;
		case R.id.ok:
			Intent intent = new Intent();
			intent.putExtra("result", time);
			setResult(RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}
		
	}

	
	
	
}
