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
	
	public boolean passPort = false;//ָʾ�û��Ƿ���ͨ�����أ�����������������"�����ɹ���"����trueΪͨ�� ��falseΪ��ͨ��
	public boolean relogin = false;
	
	public MyHandler(Activity activity){
		this.activity = activity;
	}
	@SuppressWarnings("unchecked")
	public void handleMessage(Message msg,Dialog progressDialog) {
		//Ҫ�������ظ���¼��Ϊһ���������ж�
		//��ômsg.obj�İ��������ݲ�Ӧ����һ��jsonString��,
		//Ӧ������������ʱ�򣬾ͽ�jsonStringת���map���ϣ�
		//����handler��ȡ��Ҫ��ͷ��ѶϢ��ͷ��ѶϢ�ǹ̶���ʽ�ģ����������õĴ���
		//��ΪҪ��ת���������ﻹ��Ҫ��new ����ʱ����һ��activityʵ��
		//��������msg.obj��jsonString�Ļ����͵���������� ������ֻ����ͷ����Ϣ��
		//����Ҫ����ͷ������Ҫ��дһ��ֻ����ͷ���ķ�������������ҲҪ������
		//���˶�Ҫ�������������ȽϷ���
		//�����ĳЩ���������ͳһ����ֻ����ͷ����Ϣ��ͷ����Ϣ���ƣ���
		//��Ϊÿ�������object���ݵĴ���ͬ��
		if(progressDialog != null)
			progressDialog.dismiss();
		switch (msg.what) {
		case SUCCESS:
			map = (Map<String, Object>) msg.obj;
			if(map != null){
				responseString = (String) map.get("responseString");
				if(responseString != null){
					if(responseString.equals(Parameters.RELOGIN.toString())){//�ظ���¼
						relogin = true;
						OtherService.relogin = true;
						//�ظ���¼��ȻҪ����û���Ϣ
						OtherService.clearInfo(activity);
						activity.startActivity(new Intent(activity, LoginActivity.class));
					}else if(responseString.equals(Parameters.OVERDUE.toString())){//ƾ֤����
						OtherService.overdue = true;
						activity.startActivity(new Intent(activity, LoginActivity.class));
					}else if (responseString.equals(Parameters.OPERATE_SUCCESS.toString())) {	
						passPort = true;
					}else{
						Toast.makeText(activity, (String)map.get("responseWords"), Toast.LENGTH_SHORT).show();
						passPort = false;//��ͨ��֤�޸�Ϊfalse��������ͬһ������ʱ����ʹ������Ϣ���ԣ�passport��Ϊtrue
					}
				}else{
					Toast.makeText(activity, "����Ӧ��", Toast.LENGTH_SHORT).show();
					passPort = false;
				}
			}else{
				Toast.makeText(activity, "δ��ȡ����������", Toast.LENGTH_SHORT).show();
				passPort = false;
			}
			break;
		case ERROR:
			Toast.makeText(activity, "�ף���û������", Toast.LENGTH_SHORT).show();
			passPort = false;
		default:
			passPort = false;
			break;
		}
	}
}
