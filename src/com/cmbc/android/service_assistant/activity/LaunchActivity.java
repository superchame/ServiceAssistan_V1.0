package com.cmbc.android.service_assistant.activity;



import com.cmbc.android.service_assistant.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author ��������android�� �ų�
 * @see ����Ӧ�õ��������棬����Handler�����postDelayed
 * �����̵߳ķ���
 * �÷�������һ���̺߳�int�ͺ���ֵ
 * 
 */
public class LaunchActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //ȡ������   
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        //ȡ��״̬��
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,    
                WindowManager.LayoutParams.FLAG_FULLSCREEN);   
		setContentView(R.layout.activity_launch);
		//�����ӳٵķ���
		launchView();
	}
	
	public void launchView(){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				/*
				 * ʹ����ʽ��ͼ��
				 * ����ʶ������AndriodӦ�õ���ͼ
				 * ������Ӧ�������Ӧ�÷�����ϵ
				 */
				
				Intent intent = new Intent(LaunchActivity.this,  LoginActivity.class);
				intent.setAction("com.cmbc.android.develop.assistant_of_minsheng");
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setDataAndType(Uri.parse("minsheng:assistant"), "application/assistant");
				startActivity(intent);
				finish();
			}
		}, 2000);
	}
}
