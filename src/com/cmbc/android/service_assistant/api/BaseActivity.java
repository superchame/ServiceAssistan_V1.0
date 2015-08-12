/**
 * 
 */
package com.cmbc.android.service_assistant.api;

import com.cmbc.android.service_assistant.R;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;


/**
 * @author ��������android�� �ų�
 * @see ���������б���Ŀ������Activity�Ļ��࣬��������Ӧ��Activity�ĵ�ɫ
 */

public class BaseActivity extends Activity {
	
	public Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  Resources res = getResources();
	      Drawable drawable = res.getDrawable(R.drawable.base_activity_color);
	      this.getWindow().setBackgroundDrawable(drawable);
	      
	}
}
