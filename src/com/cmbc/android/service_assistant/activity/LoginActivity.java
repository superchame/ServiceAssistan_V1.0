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
 * @author ��������android�� �ų���ʵϰ����
 * @see ��½����
 */
@SuppressLint("HandlerLeak")
public class LoginActivity extends TitleActivity {
	
	//ȫ�ֱ���
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
				//��תʱ��ҪЯ���û���Ϣ��ѡ�����
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
				//�����½�û���Ϣ
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
		textView.setText("�˺ŵ�½");
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(OtherService.relogin){//���Ϊ�ظ���½������
			Toast.makeText(this, "�����˺����������ط���¼��ƾ֤ʧЧ�������µ�¼", Toast.LENGTH_LONG).show();
		}
		if(OtherService.overdue){
			Toast.makeText(this, "�û�ƾ֤�ѹ��ڣ������µ�¼", Toast.LENGTH_LONG).show();
		}
	}
	

	
	public void login(View view){
		final String pwd = pwd_et.getText().toString().trim();
		name = name_et.getText().toString().trim();
		
		if(TextUtils.isEmpty(pwd) || TextUtils.isEmpty(name)){
			Toast.makeText(this, "������������½��Ϣ", Toast.LENGTH_SHORT).show();
			return;
		}	
		
		
		
		//�������ȶԻ���
		progressDialog = OtherService.createTransparentProgressDialog(LoginActivity.this);
		
		//�����������ĵ�½�ӿ�
		NetService.loginService(handler, name, pwd);
		
	}
	
	//���ؿؼ�
	public void  initial(){
		textView = (TextView) findViewById(R.id.titlebar_title_tv);
		name_et = (EditText) findViewById(R.id.activity_login_name_et);
		pwd_et = (EditText) findViewById(R.id.activity_login_pwd_et);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//ɱ���Լ��Ľ��̣������system.exit(0),���ǽ�java������ҵ����رյģ�̫������
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
}
