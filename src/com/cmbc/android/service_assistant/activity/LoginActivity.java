package com.cmbc.android.service_assistant.activity;

import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.MyHandler;
import com.cmbc.android.service_assistant.api.NetService;
import com.cmbc.android.service_assistant.api.OtherService;
import com.cmbc.android.service_assistant.api.ParseTools;
import com.cmbc.android.service_assistant.api.TitleActivity;
import com.cmbc.android.service_assistant.entity.User;




/**
 * @author 民生电商android部 张超（实习生）
 * @see 登陆界面
 */
@SuppressLint("HandlerLeak")
public class LoginActivity extends TitleActivity {
	//常量
	private static final int SUCCESS = 1;
	private static final int ERROR = 0;
	
	//全局变量
	private TextView textView;
	private EditText name_et,pwd_et;
	private String jsonString, responseString, name;
	private Dialog progressDialog;
	private Map<String, String> map;
	

	
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == SUCCESS){
				jsonString = (String)msg.obj;
				map = ParseTools.getLoginResponse(jsonString);
				responseString = map.get("responseString");
				if(responseString != null && responseString.equals("操作成功！")){
					progressDialog.dismiss();
					//获取版本信息并传递
					
					//跳转时需要携带用户信息到选项卡界面
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					
					User userInfo = new User();
					userInfo.setId(map.get("id"));
					userInfo.setLoginName(map.get("loginName"));
					userInfo.setOrgId(map.get("orgId"));
					userInfo.setOrgName(map.get("orgName"));
					userInfo.setRealName(map.get("realName"));
					userInfo.setToken(map.get("token"));
					
					Bundle bundle = new Bundle();
					bundle.putParcelable("userInfo", userInfo);
					intent.putExtras(bundle);
					
					startActivity(intent);
				}else{
					progressDialog.dismiss();
					Toast.makeText(LoginActivity.this, responseString, Toast.LENGTH_SHORT).show();
				}
			}else if(msg.what == ERROR){
					progressDialog.dismiss();
					Toast.makeText(LoginActivity.this, "亲，你没有网络哦", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCustormLayout(R.layout.activity_login, R.layout.titlebar_title);
		initial();
		textView.setText("账号登陆");
		
	}
	
	public void login(View view){
		final String pwd = pwd_et.getText().toString().trim();
		name = name_et.getText().toString().trim();
		
		if(TextUtils.isEmpty(pwd) || TextUtils.isEmpty(name)){
			Toast.makeText(this, "请输入完整登陆信息", Toast.LENGTH_SHORT).show();
			return;
		}	
		
		//给网络服务设置发起方域代码和应用版本号,需要放在网络访问之前，以免网络访问时为空
		NetService.initialParameter(OtherService.DeviceType(this), OtherService.getVersion(this));
		
		//弹出进度对话框
		progressDialog = OtherService.createTransparentProgressDialog(LoginActivity.this);
		
		//调用网络服务的登陆接口
		NetService.loginService2(handler, name, pwd);
		
	}
	
	//加载控件
	public void  initial(){
		textView = (TextView) findViewById(R.id.titlebar_title_tv);
		name_et = (EditText) findViewById(R.id.activity_login_name_et);
		pwd_et = (EditText) findViewById(R.id.activity_login_pwd_et);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}
	
}
