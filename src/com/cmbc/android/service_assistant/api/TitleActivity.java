package com.cmbc.android.service_assistant.api;


import android.os.Bundle;
import android.view.Window;

/**
 * 
 * @author ��������android�� �ų�
 * @see �����̳���BaseActivity,
 * ������и���ĵ�ɫ��
 * ���������ʹ�ø����setCustormLayout(int layoutId, int titleStyleId)������
 * ���������������Ҫ����һ����ͨActivity���ֺ��Զ�����Ⲽ��ʱ�����㣬
 * ����̳�������������������������ò���ʱ��setContentView��Ҳ������д����
 * �����Զ������Ĵ��룬�������Ȼ����⣬�뿴Demo:TestTitleActivity,
 * ���Demo��ʾ����μ̳к����ʹ��
 * 
 *
 */
public class TitleActivity extends BaseActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//���ñ�����
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
		
		
	}
	
	public void setCustormLayout(int layoutId, int titleStyleId) {
		setContentView(layoutId);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, titleStyleId);  
	}
	
}
