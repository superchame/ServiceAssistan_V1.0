package com.cmbc.android.service_assistant.api;

import java.util.Map;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

public class MyHandler extends Handler {
	private Activity activity;
	private Map<String, Object> map;
	private String responseString;
	public MyHandler(Activity activity){
		this.activity = activity;
	}
	@Override
	public void handleMessage(Message msg) {
		//要做到将重复登录作为一个公共的判断
		//那么msg.obj的包含的内容不应该是一个jsonString了,
		//应该在网络服务的时候，就将jsonString转码成map集合，
		//发给handler后取出要的头部讯息，头部讯息是固定格式的，可以做公用的处理
		//因为要跳转，所以这里还需要在new 对象时传递一个activity实例
		//如果这里的msg.obj是jsonString的话，就得在这里解析 ，可以只解析头部信息，
		//这里要解析头部，就要再写一个只解析头部的方法，网络服务端也要解析，
		//两端都要解析，
		map = (Map<String, Object>) msg.obj;
//		if(){
//			
//		}
		super.handleMessage(msg);
		
	}
}
