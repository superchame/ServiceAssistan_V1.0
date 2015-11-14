package com.cmbc.android.service_assistant.activity;


import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.cmbc.android.service_assistant.api.TitleActivity;
import com.cmbc.android.service_assistant.entity.User;

/**
 * @author 民生电商android部 张超（实习生）
 * @see 登陆界面
 */
@SuppressLint("HandlerLeak")
public class LoginActivity extends TitleActivity {
	
	//全局变量
	private TextView textView;
	private EditText name_et,pwd_et;
	private String name;
	private Dialog progressDialog;
	private Map<String, Object> map;
	
	private MyHandler handler = new MyHandler(this){
		
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			super.handleMessage(msg,progressDialog);
			if(passPort){
				map = (Map<String, Object>) msg.obj;
				//跳转时需要携带用户信息到选项卡界面
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						
				User userInfo = new User();
				userInfo.setId((String)map.get("id"));
				userInfo.setLoginName((String)map.get("loginName"));
				userInfo.setOrgId((String)map.get("orgId"));
				userInfo.setOrgName((String)map.get("orgName"));
				userInfo.setRealName((String)map.get("realName"));
				userInfo.setToken((String)map.get("token"));
						
				Bundle bundle = new Bundle();
				bundle.putParcelable("userInfo", userInfo);
				intent.putExtras(bundle);
				//保存登陆用户信息
				OtherService.saveUserInfo(LoginActivity.this, userInfo);
				startActivity(intent);
			}else{
				
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
	
	@Override
	protected void onResume() {
		super.onResume();
		if(OtherService.relogin){//如果为重复登陆的属性
			Toast.makeText(this, "您的账号已在其他地方登录，凭证失效，请重新登录", Toast.LENGTH_LONG).show();
		}
		if(OtherService.overdue){
			Toast.makeText(this, "用户凭证已过期，请重新登录", Toast.LENGTH_LONG).show();
		}
	}
	

	
	public void login(View view){
		final String pwd = pwd_et.getText().toString().trim();
		name = name_et.getText().toString().trim();
		
		if(TextUtils.isEmpty(pwd) || TextUtils.isEmpty(name)){
			Toast.makeText(this, "请输入完整登陆信息", Toast.LENGTH_SHORT).show();
			return;
		}	
		
		
		
		//弹出进度对话框
		progressDialog = OtherService.createTransparentProgressDialog(LoginActivity.this);
		
		//调用网络服务的登陆接口
		NetService.loginService(handler, name, pwd);
		
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
		//杀死自己的进程，如果用system.exit(0),那是将java虚拟机挂掉来关闭的，太暴力了
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
}
