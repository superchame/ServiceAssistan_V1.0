package com.cmbc.android.service_assistant.api;



import com.cmbc.android.service_assistant.R;

import android.os.Bundle;
import android.widget.TextView;

/**
 * @author 民生电商android部 张超
 * @see 该类是个Demo,用于测试是否能成功继承并使用TitleActivity
 */
public class TestTitleActivity extends TitleActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCustormLayout(R.layout.activity_test, R.layout.title_test);
		TextView title = (TextView) findViewById(R.id.textView1);
		title.setText("我修改了标题");
	}
}
