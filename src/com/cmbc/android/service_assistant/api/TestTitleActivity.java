package com.cmbc.android.service_assistant.api;



import com.cmbc.android.service_assistant.R;

import android.os.Bundle;
import android.widget.TextView;

/**
 * @author ��������android�� �ų�
 * @see �����Ǹ�Demo,���ڲ����Ƿ��ܳɹ��̳в�ʹ��TitleActivity
 */
public class TestTitleActivity extends TitleActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCustormLayout(R.layout.activity_test, R.layout.title_test);
		TextView title = (TextView) findViewById(R.id.textView1);
		title.setText("���޸��˱���");
	}
}
