package com.cmbc.android.service_assistant.activity;

import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.BaseActivity;

import com.cmbc.android.service_assistant.fragment.MineFragment;
import com.cmbc.android.service_assistant.fragment.SearchFragment;
import com.cmbc.android.service_assistant.fragment.StoreFragment;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;


@SuppressLint("NewApi")
public class MainActivity extends BaseActivity implements OnClickListener {
	
	//碎片fragment
	private SearchFragment searchFragment;
	private StoreFragment storeFragment;
	private MineFragment mineFragment;
	
	//Tabhost中的图标控件成员变量
	private ImageView search_img;
	private ImageView store_img;
	private ImageView mine_img;
	
	//Tabhost中的文字控件成员变量
	private TextView search_tv;
	private TextView store_tv;
	private TextView mine_tv;
		
	//TabHost的布局成员变量
	private View search_view;
	private View store_view;
	private View mine_view;
	
	//碎片管理器
	private FragmentManager fragmentManager;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//视图的主布局，即碎片的框架
		setContentView(R.layout.activity_main_fragment_container);
		//初始化布局
		initViews();
		//实例化碎片管理者
		fragmentManager = getFragmentManager();
		//第一次启动时选中第0个
		setTabSelection(0);
	}


	/**
	 * 实例化控件
	 */
	private void initViews() {
		search_img = (ImageView) findViewById(R.id.img_search);
		search_tv = (TextView) findViewById(R.id.tv_search);
		search_view = findViewById(R.id.search_tab);
		
		store_img = (ImageView) findViewById(R.id.img_store);
		store_tv = (TextView) findViewById(R.id.tv_store);
		store_view = findViewById(R.id.store_tab);
		
		mine_img = (ImageView) findViewById(R.id.img_my);
		mine_tv = (TextView) findViewById(R.id.tv_my);
		mine_view = findViewById(R.id.mine_tab);
		
		//监听view的点击事件
		search_view.setOnClickListener(this);
		store_view.setOnClickListener(this);
		mine_view.setOnClickListener(this);
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.search_tab:
			//点击了消息tab时，选中第一个tab
			setTabSelection(0);
			break;
		case R.id.store_tab:
			//点击了联系人tab时，选中第二个tab
			setTabSelection(1);
			break;
		case R.id.mine_tab:
			//点击了动态tab时，选中第三个tab
			setTabSelection(2);
			break;
		default:
			break;
		}
		
	}
		
		/**
		 * 根据传入的index参数来设置选中的tab页
		 * @author Administrator
		 * @param index
		 *  0表示正在执行，1表示编辑任务，2表示已经完成的项目
		 */
		private void setTabSelection(int index) {
			//每次选中之前，先清除掉上次选中的 状态
					clearSelection();
					//开启一个Fragment事务
					FragmentTransaction transaction = fragmentManager.beginTransaction();
					
					//先隐藏掉所有的Fragment,以防止多个Fragment显示在界面上的情况
					hideFragments(transaction);
					switch(index){
						case 0:
							//当点击正在执行的tab时，改变控件的图片和颜色
							search_img.setImageResource(R.drawable.fada1);
							search_tv.setTextColor(Color.parseColor("#FEA64B"));
							if(searchFragment == null){
								//如果progress_Fragment为空，则创建一个并添加到界面上
								searchFragment = new SearchFragment();
								transaction.add(R.id.fragment_container, searchFragment);
							}else{
								//如果progress_Fragment不为空，则直接将它显示出来
								transaction.show(searchFragment);
							}
							break;
						case 1:
							//当点击编辑任务tab时，改变控件的图片和颜色
							store_img.setImageResource(R.drawable.mendian4);
							store_tv.setTextColor(Color.parseColor("#FEA64B"));
							if(storeFragment == null){
								//如果editTask_Fragment为空，则创建一个并添加到界面上
								storeFragment = new StoreFragment();
								transaction.add(R.id.fragment_container, storeFragment);
							}else{
								//如果editTask_Fragment不为空，则直接将它显示出来
								transaction.show(storeFragment);
							}
							break;
						case 2:
							//当点击已完成任务的tab时，改变控件的图片和颜色
							mine_img.setImageResource(R.drawable.mine2);
							mine_tv.setTextColor(Color.parseColor("#FEA64B"));
							if(mineFragment == null){
								//如果finishTask_Fragment为空，则创建一个并添加到界面上
								mineFragment = new MineFragment();
								transaction.add(R.id.fragment_container, mineFragment);
							}else{
								//如果finishTask_Fragment不为空，则直接将它显示出来
								transaction.show(mineFragment);
							}
							break;
					}
					 transaction.commit();
		}
		
		/**
		 * 将所有Fragment都置为隐藏状态
		 * 
		 * @param transaction
		 * 用于对Fragment执行操作的事务
		 */
		private void hideFragments(FragmentTransaction transaction) {
			if(searchFragment != null)
				transaction.hide(searchFragment);
			if(storeFragment != null)
				transaction.hide(storeFragment);
			if(mineFragment != null)
				transaction.hide(mineFragment);
		}
		

		//点击其他的tab时，相应的，之前的点选的tab的样式应该还原
		private void clearSelection() {
			search_img.setImageResource(R.drawable.fada2);
			search_tv.setTextColor(Color.parseColor("#000000"));
			store_img.setImageResource(R.drawable.mendian3);
			store_tv.setTextColor(Color.parseColor("#000000"));
			mine_img.setImageResource(R.drawable.mine);
			mine_tv.setTextColor(Color.parseColor("#000000"));	
			
		}
	
	
	

}
