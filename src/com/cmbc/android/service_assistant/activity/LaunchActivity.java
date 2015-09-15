package com.cmbc.android.service_assistant.activity;



import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.NetService;
import com.cmbc.android.service_assistant.api.OtherService;
import com.cmbc.android.service_assistant.entity.User;

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
				//给网络服务设置发起方域代码和应用版本号,需要放在网络访问之前，以免网络访问时为空
				NetService.initialParameter(OtherService.DeviceType(LaunchActivity.this), OtherService.getVersion(LaunchActivity.this));
				//判断是否能获取用户信息，如果为空，重新登录，不为空，直接跳转到主页面
				User userInfo = OtherService.getUserInfo(LaunchActivity.this);
				if(userInfo != null){
					Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
					Bundle bundle = new Bundle();
					bundle.putParcelable("userInfo", userInfo);
					intent.putExtras(bundle);
					startActivity(intent);
					finish();
				}else{
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
				
			}
		}, 3000);
	}
}
