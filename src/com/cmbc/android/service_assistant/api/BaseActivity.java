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
 * @author 民生电商android部 张超
 * @see 该类是所有本项目中其他Activity的基类，负责所有应用Activity的底色
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
