package com.cmbc.android.service_assistant.api;

import java.util.Map;
import com.cmbc.android.service_assistant.activity.LoginActivity;
import com.cmbc.android.service_assistant.enums.Parameters;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MyHandler extends Handler {
	private static final int SUCCESS = 1;
	private static final int ERROR = 0;
	private Activity activity;
	private Map<String, Object> map;
	private String responseString;
	
	public boolean passPort = false;//指示用户是否是通过网关（即访问了正常数据"操作成功！"），true为通过 ，false为不通过
	public boolean relogin = false;
	
	public MyHandler(Activity activity){
		this.activity = activity;
	}
	@SuppressWarnings("unchecked")
	public void handleMessage(Message msg,Dialog progressDialog) {
		//要做到将重复登录作为一个公共的判断
		//那么msg.obj的包含的内容不应该是一个jsonString了,
		//应该在网络服务的时候，就将jsonString转码成map集合，
		//发给handler后取出要的头部讯息，头部讯息是固定格式的，可以做公用的处理
		//因为要跳转，所以这里还需要在new 对象时传递一个activity实例
		//如果这里的msg.obj是jsonString的话，就得在这里解析 ，可以只解析头部信息，
		//这里要解析头部，就要再写一个只解析头部的方法，网络服务端也要解析，
		//两端都要解析，工作量比较繁琐
		//这里对某些网络情况做统一处理，只处理头部信息（头部信息类似），
		//因为每个界面对object数据的处理不同，
		if(progressDialog != null)
			progressDialog.dismiss();
		switch (msg.what) {
		case SUCCESS:
			map = (Map<String, Object>) msg.obj;
			if(map != null){
				responseString = (String) map.get("responseString");
				if(responseString != null){
					if(responseString.equals(Parameters.RELOGIN.toString())){//重复登录
						relogin = true;
						OtherService.relogin = true;
						//重复登录仍然要清除用户信息
						OtherService.clearInfo(activity);
						activity.startActivity(new Intent(activity, LoginActivity.class));
					}else if(responseString.equals(Parameters.OVERDUE.toString())){//凭证过期
						OtherService.overdue = true;
						activity.startActivity(new Intent(activity, LoginActivity.class));
					}else if (responseString.equals(Parameters.OPERATE_SUCCESS.toString())) {	
						passPort = true;
					}else{
						Toast.makeText(activity, (String)map.get("responseWords"), Toast.LENGTH_SHORT).show();
						passPort = false;//将通行证修改为false，否则在同一个界面时，即使网络信息不对，passport仍为true
					}
				}else{
					Toast.makeText(activity, "无响应码", Toast.LENGTH_SHORT).show();
					passPort = false;
				}
			}else{
				Toast.makeText(activity, "未获取到网络数据", Toast.LENGTH_SHORT).show();
				passPort = false;
			}
			break;
		case ERROR:
			Toast.makeText(activity, "亲，你没有网络", Toast.LENGTH_SHORT).show();
			passPort = false;
		default:
			passPort = false;
			break;
		}
	}
}
