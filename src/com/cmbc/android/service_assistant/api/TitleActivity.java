package com.cmbc.android.service_assistant.api;


import android.os.Bundle;
import android.view.Window;

/**
 * 
 * @author 民生电商android部 张超
 * @see 这个类继承于BaseActivity,
 * 该类具有父类的底色，
 * 并且你可以使用该类的setCustormLayout(int layoutId, int titleStyleId)方法，
 * 这个方法会在你需要传递一个普通Activity布局和自定义标题布局时帮助你，
 * 即你继承这个类后，这个方法代替了你设置布局时的setContentView，也无需再写其他
 * 设置自定义标题的代码，如果你仍然不理解，请看Demo:TestTitleActivity,
 * 这个Demo演示了如何继承后如何使用
 * 
 *
 */
public class TitleActivity extends BaseActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置标题栏
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); 
		
		
	}
	
	public void setCustormLayout(int layoutId, int titleStyleId) {
		setContentView(layoutId);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, titleStyleId);  
	}
	
}
