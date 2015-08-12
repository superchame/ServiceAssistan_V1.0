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
 * @author 民生电商android部 张超
 * @see 这是应用的启动界面，采用Handler对象的postDelayed
 * 加载线程的方法
 * 该方法传递一个线程和int型毫秒值
 * 
 */
public class LaunchActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //取消标题   
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,    
                WindowManager.LayoutParams.FLAG_FULLSCREEN);   
		setContentView(R.layout.activity_launch);
		//调用延迟的方法
		launchView();
	}
	
	public void launchView(){
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				/*
				 * 使用隐式意图，
				 * 便于识别其他Andriod应用的意图
				 * 假如别的应用想与该应用发生联系
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
