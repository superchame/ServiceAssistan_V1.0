package com.cmbc.android.service_assistant.activity;

import android.os.Bundle;

import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.TitleActivity;

public class TimePickerActivity extends TitleActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCustormLayout(R.layout.activity_time_picker, R.layout.titlebar_back_title);
	}
	
	
}
