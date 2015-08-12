package com.cmbc.android.service_assistant.fragment;




import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.activity.OrderSearchActivity;
import com.cmbc.android.service_assistant.entity.User;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


@SuppressLint("NewApi")
public class SearchFragment extends Fragment implements OnClickListener{
	
	private User userInfo;
	private ImageView img_orderSearch, img_buySearch, img_orderCount, img_buyCount;
	private Bundle bundle;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contact = inflater.inflate(R.layout.fragment_search, container , false);
		bundle = getActivity().getIntent().getExtras();
		return contact;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		initial();
	}
	
	public void initial(){
		img_orderSearch = (ImageView) getActivity().findViewById(R.id.img_ddcx);
		img_buySearch = (ImageView) getActivity().findViewById(R.id.img_cgcx);
		img_orderCount = (ImageView) getActivity().findViewById(R.id.img_ddtj);
		img_buyCount = (ImageView) getActivity().findViewById(R.id.img_cgdtj);
		
		img_orderSearch.setOnClickListener(this);
		img_orderSearch.setOnClickListener(this);
		img_orderCount.setOnClickListener(this);
		img_buyCount.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.img_ddcx:
			Intent intent = new Intent(getActivity() , OrderSearchActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.img_cgcx:
			break;
		case R.id.img_ddtj:
			break;
		case R.id.img_cgdtj:
			break;
		default:
			break;
		}
	}
}
