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
		//Ҫ�������ظ���¼��Ϊһ���������ж�
		//��ômsg.obj�İ��������ݲ�Ӧ����һ��jsonString��,
		//Ӧ������������ʱ�򣬾ͽ�jsonStringת���map���ϣ�
		//����handler��ȡ��Ҫ��ͷ��ѶϢ��ͷ��ѶϢ�ǹ̶���ʽ�ģ����������õĴ���
		//��ΪҪ��ת���������ﻹ��Ҫ��new ����ʱ����һ��activityʵ��
		//��������msg.obj��jsonString�Ļ����͵���������� ������ֻ����ͷ����Ϣ��
		//����Ҫ����ͷ������Ҫ��дһ��ֻ����ͷ���ķ�������������ҲҪ������
		//���˶�Ҫ������
		map = (Map<String, Object>) msg.obj;
//		if(){
//			
//		}
		super.handleMessage(msg);
		
	}
}
