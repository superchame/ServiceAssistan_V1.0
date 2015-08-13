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
	
	//��Ƭfragment
	private SearchFragment searchFragment;
	private StoreFragment storeFragment;
	private MineFragment mineFragment;
	
	//Tabhost�е�ͼ��ؼ���Ա����
	private ImageView search_img;
	private ImageView store_img;
	private ImageView mine_img;
	
	//Tabhost�е����ֿؼ���Ա����
	private TextView search_tv;
	private TextView store_tv;
	private TextView mine_tv;
		
	//TabHost�Ĳ��ֳ�Ա����
	private View search_view;
	private View store_view;
	private View mine_view;
	
	//��Ƭ������
	private FragmentManager fragmentManager;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//��ͼ�������֣�����Ƭ�Ŀ��
		setContentView(R.layout.activity_main_fragment_container);
		//��ʼ������
		initViews();
		//ʵ������Ƭ������
		fragmentManager = getFragmentManager();
		//��һ������ʱѡ�е�0��
		setTabSelection(0);
	}


	/**
	 * ʵ�����ؼ�
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
		
		//����view�ĵ���¼�
		search_view.setOnClickListener(this);
		store_view.setOnClickListener(this);
		mine_view.setOnClickListener(this);
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.search_tab:
			//�������Ϣtabʱ��ѡ�е�һ��tab
			setTabSelection(0);
			break;
		case R.id.store_tab:
			//�������ϵ��tabʱ��ѡ�еڶ���tab
			setTabSelection(1);
			break;
		case R.id.mine_tab:
			//����˶�̬tabʱ��ѡ�е�����tab
			setTabSelection(2);
			break;
		default:
			break;
		}
		
	}
		
		/**
		 * ���ݴ����index����������ѡ�е�tabҳ
		 * @author Administrator
		 * @param index
		 *  0��ʾ����ִ�У�1��ʾ�༭����2��ʾ�Ѿ���ɵ���Ŀ
		 */
		private void setTabSelection(int index) {
			//ÿ��ѡ��֮ǰ����������ϴ�ѡ�е� ״̬
					clearSelection();
					//����һ��Fragment����
					FragmentTransaction transaction = fragmentManager.beginTransaction();
					
					//�����ص����е�Fragment,�Է�ֹ���Fragment��ʾ�ڽ����ϵ����
					hideFragments(transaction);
					switch(index){
						case 0:
							//���������ִ�е�tabʱ���ı�ؼ���ͼƬ����ɫ
							search_img.setImageResource(R.drawable.fada1);
							search_tv.setTextColor(Color.parseColor("#FEA64B"));
							if(searchFragment == null){
								//���progress_FragmentΪ�գ��򴴽�һ������ӵ�������
								searchFragment = new SearchFragment();
								transaction.add(R.id.fragment_container, searchFragment);
							}else{
								//���progress_Fragment��Ϊ�գ���ֱ�ӽ�����ʾ����
								transaction.show(searchFragment);
							}
							break;
						case 1:
							//������༭����tabʱ���ı�ؼ���ͼƬ����ɫ
							store_img.setImageResource(R.drawable.mendian4);
							store_tv.setTextColor(Color.parseColor("#FEA64B"));
							if(storeFragment == null){
								//���editTask_FragmentΪ�գ��򴴽�һ������ӵ�������
								storeFragment = new StoreFragment();
								transaction.add(R.id.fragment_container, storeFragment);
							}else{
								//���editTask_Fragment��Ϊ�գ���ֱ�ӽ�����ʾ����
								transaction.show(storeFragment);
							}
							break;
						case 2:
							//���������������tabʱ���ı�ؼ���ͼƬ����ɫ
							mine_img.setImageResource(R.drawable.mine2);
							mine_tv.setTextColor(Color.parseColor("#FEA64B"));
							if(mineFragment == null){
								//���finishTask_FragmentΪ�գ��򴴽�һ������ӵ�������
								mineFragment = new MineFragment();
								transaction.add(R.id.fragment_container, mineFragment);
							}else{
								//���finishTask_Fragment��Ϊ�գ���ֱ�ӽ�����ʾ����
								transaction.show(mineFragment);
							}
							break;
					}
					 transaction.commit();
		}
		
		/**
		 * ������Fragment����Ϊ����״̬
		 * 
		 * @param transaction
		 * ���ڶ�Fragmentִ�в���������
		 */
		private void hideFragments(FragmentTransaction transaction) {
			if(searchFragment != null)
				transaction.hide(searchFragment);
			if(storeFragment != null)
				transaction.hide(storeFragment);
			if(mineFragment != null)
				transaction.hide(mineFragment);
		}
		

		//���������tabʱ����Ӧ�ģ�֮ǰ�ĵ�ѡ��tab����ʽӦ�û�ԭ
		private void clearSelection() {
			search_img.setImageResource(R.drawable.fada2);
			search_tv.setTextColor(Color.parseColor("#000000"));
			store_img.setImageResource(R.drawable.mendian3);
			store_tv.setTextColor(Color.parseColor("#000000"));
			mine_img.setImageResource(R.drawable.mine);
			mine_tv.setTextColor(Color.parseColor("#000000"));	
			
		}
	
	
	

}
