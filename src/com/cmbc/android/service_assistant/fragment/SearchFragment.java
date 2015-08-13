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
	private View ordersearch,ordercount,purchasesearch,purchasecount;
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
		ordersearch = getActivity().findViewById(R.id.ordersearch_ll);
		ordercount = getActivity().findViewById(R.id.ordercount_ll);
		purchasecount = getActivity().findViewById(R.id.purchasecount_ll);
		purchasesearch = getActivity().findViewById(R.id.purchasesearch_ll);

		ordersearch.setOnClickListener(this);
		ordercount.setOnClickListener(this);
		purchasesearch.setOnClickListener(this);
		purchasecount.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ordersearch_ll:
			Intent intent = new Intent(getActivity() , OrderSearchActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.purchasesearch_ll:
			break;
		case R.id.ordercount_ll:
			break;
		case R.id.purchasecount_ll:
			break;
		default:
			break;
		}
	}
}
