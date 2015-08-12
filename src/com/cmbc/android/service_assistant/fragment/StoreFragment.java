package com.cmbc.android.service_assistant.fragment;



import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.activity.SearchDeliveryOrderActivity;
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

@SuppressLint("NewApi")
public class StoreFragment extends Fragment implements OnClickListener {
	private User userInfo;
	private ImageView imageView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contact = inflater.inflate(R.layout.fragment_store, container, false);
		return contact;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		imageView = (ImageView) getActivity().findViewById(R.id.img_mdsh);
		imageView.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.img_mdsh:
			Intent intent  = new Intent(getActivity(), SearchDeliveryOrderActivity.class);
			intent.putExtras(getActivity().getIntent().getExtras());
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
