package com.cmbc.android.service_assistant.fragment;

import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.NetService;
import com.cmbc.android.service_assistant.entity.User;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


@SuppressLint("NewApi")
public class MineFragment extends Fragment {
	private Button btn_logout;
	private TextView name_tv, org_tv;
	private Bundle bundle;
	private User userInfo;
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contact = inflater.inflate(R.layout.fragment_mine, container, false);
		return contact;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		bundle = getActivity().getIntent().getExtras();
		userInfo = bundle.getParcelable("userInfo");
		
		System.out.println(userInfo.toString());
		
		initial();
		
		name_tv.setText(userInfo.getRealName());
		org_tv.setText(userInfo.getOrgName());
		
		//添加监听
		btn_logout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
					NetService.logoutService(userInfo.getLoginName(), userInfo.getToken());
					getActivity().finish();
			}
		});
		
	}
	
	//初始化控件
	public void initial(){
		btn_logout = (Button) getActivity().findViewById(R.id.fragment_mine_btn);
		name_tv = (TextView) getActivity().findViewById(R.id.fragment_mine_name_tv);
		org_tv = (TextView) getActivity().findViewById(R.id.fragment_mine_org_tv);
	}
}
