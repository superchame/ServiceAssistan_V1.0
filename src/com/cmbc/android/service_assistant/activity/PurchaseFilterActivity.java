package com.cmbc.android.service_assistant.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.MyHandler;
import com.cmbc.android.service_assistant.api.NetService;
import com.cmbc.android.service_assistant.api.OtherService;
import com.cmbc.android.service_assistant.api.TitleActivity;
import com.cmbc.android.service_assistant.entity.Supplier;
import com.cmbc.android.service_assistant.entity.User;

public class PurchaseFilterActivity extends TitleActivity implements OnClickListener{
	private static final int SUPNAMECODE = 190;
	private static final int RECBASECODE = 180;
	private static final int PUCSTATUSCODE = 170;
	private static final int PUCTYPECODE = 160;
	
	//处理不同筛选的dataList添加数据的标识
	private static final int ORGDATA = 44;
	private static final int STATUSDATA = 55;
	private static final int TYPEDATA = 66;
	
	private TextView title,clear,ok,supplierName,receiveBase,purchaseStatus,purchaseType;
	private ImageView backIcon;
	private View supplierNameView,recBaseView,purchaseStatusView,purchaseTypeView;
	private Bundle bundle;
	private Dialog progressDialog;
	private User userInfo;
	private Map<String, Object> map;
	private ArrayList<Supplier> supplierList;
	private List<Map<String, String>> orgList, statusList, typeList;
	private String statusValue = null,typeValue = null,orgValue = null,supplierValue = null;
	private List<String> dataList;
	
	private MyHandler myHandler = new MyHandler(this){
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			super.handleMessage(msg, progressDialog);
			if(passPort){
				map = (Map<String, Object>) msg.obj;
				supplierList = (ArrayList<Supplier>) map.get("supplierList");
				orgList = (List<Map<String, String>>) map.get("orgList");
				statusList = (List<Map<String, String>>) map.get("statusList");
				typeList = (List<Map<String, String>>) map.get("typeList");			
			}else{
				
			}
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCustormLayout(R.layout.activity_purchase_filter, R.layout.titlebar_back_title);
		initial();
		bundle = getIntent().getExtras();
		userInfo = bundle.getParcelable("userInfo");
		NetService.getPurchaseOrderConditon(myHandler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId());
		progressDialog = OtherService.createTransparentProgressDialog(this);
	}
	
	private void initial(){
		title = (TextView) findViewById(R.id.titlebar_back_title_tv);
		backIcon = (ImageView) findViewById(R.id.titlebar_back_title_img);
		title.setText("筛选");
		clear  = (TextView) findViewById(R.id.activity_purchase_filter_tv_clear);
		ok = (TextView) findViewById(R.id.activity_purchase_filter_tv_ok);
		
		supplierName = (TextView) findViewById(R.id.supplier_name_tv);
		receiveBase = (TextView) findViewById(R.id.receive_base_tv);
		purchaseStatus = (TextView) findViewById(R.id.purchaseorder_status_tv);
		purchaseType = (TextView) findViewById(R.id.purchaseorder_type_tv);
		
		supplierNameView = findViewById(R.id.activity_purchase_filter_sourcestorename);
		recBaseView = findViewById(R.id.activity_purchase_filter_recbase);
		purchaseStatusView = findViewById(R.id.activity_purchase_filter_purchasestatus);
		purchaseTypeView = findViewById(R.id.activity_purchase_filter_purchasetype);
		
		backIcon.setOnClickListener(this);
		clear.setOnClickListener(this);
		ok.setOnClickListener(this);
		supplierNameView.setOnClickListener(this);
		recBaseView.setOnClickListener(this);
		purchaseStatusView.setOnClickListener(this);
		purchaseTypeView.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.titlebar_back_title_img:
			finish();
			break;
		case R.id.activity_purchase_filter_sourcestorename:
			Intent supNameIntent = new Intent(this, FilterActivity.class);
			bundle.putInt("requestCode", SUPNAMECODE);
			bundle.putParcelableArrayList("supplierList", supplierList);
			supNameIntent.putExtras(bundle);
			startActivityForResult(supNameIntent, SUPNAMECODE);
			break;
		case R.id.activity_purchase_filter_recbase:
			initialDataList();
			addDataForDataList(ORGDATA);
			Intent recbaseIntent = new Intent(this, FilterActivity.class);
			bundle.putInt("requestCode", RECBASECODE);
			recbaseIntent.putExtras(bundle);
			startActivityForResult(recbaseIntent, RECBASECODE);
			break;
		case R.id.activity_purchase_filter_purchasestatus:
			initialDataList();
			addDataForDataList(STATUSDATA);
			Intent psIntent = new Intent(this, FilterActivity.class);
			bundle.putInt("requestCode", PUCSTATUSCODE);
			psIntent.putExtras(bundle);
			startActivityForResult(psIntent, PUCSTATUSCODE);
			break;
		case R.id.activity_purchase_filter_purchasetype:
			initialDataList();
			addDataForDataList(TYPEDATA);
			Intent ptIntent = new Intent(this, FilterActivity.class);
			bundle.putInt("requestCode", PUCTYPECODE);
			ptIntent.putExtras(bundle);
			startActivityForResult(ptIntent, PUCTYPECODE);
			break;
		case R.id.activity_purchase_filter_tv_clear:
			clear();
			break;
		case R.id.activity_purchase_filter_tv_ok:
			Intent resultIntent = new Intent();
			resultIntent.putExtra("supplier_code", supplierValue);
			resultIntent.putExtra("orgCode", orgValue);
			resultIntent.putExtra("status", statusValue);
			resultIntent.putExtra("type", typeValue);
			setResult(RESULT_OK, resultIntent);
			finish();
		default:
			break;
		}
		
	}
	
	//清空筛选条件文字
	private void clear(){
		supplierName.setText("");
		receiveBase.setText("");
		purchaseStatus.setText("");
		purchaseType.setText("");
		supplierValue = null;
		orgValue = null;
		statusValue = null;
		typeValue = null;
	}
	
	//判断数据集是否为空，做出相应操作
	private void initialDataList(){
		if(dataList != null){
			dataList.clear();
		}else{
			dataList = new ArrayList<String>();
		}
	}
	
	//根据用户点击筛选需求，将网络中返回的集合中的数据放到dataList中
	private void addDataForDataList(int index){
		List<Map<String, String>> ll = null;
		switch (index) {
		case ORGDATA:
			ll = orgList;
			break;
		case STATUSDATA:
			ll = statusList;
			break;
		case TYPEDATA:
			ll = typeList;
			break;
		default:
			break;
		}
		if(ll != null){
			for(int i = 0 ; i< ll.size() ; i++){
				dataList.add(ll.get(i).get("words"));
			}
			bundle.putStringArrayList("dataList", (ArrayList<String>) dataList);
		}	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		int index = -1;
		if(resultCode == RESULT_OK){//有可能用户点击进入却没有选择item直接退出了，这时data等于null，容易空指针
			index = data.getIntExtra("resultIndex", -1);
		}else {
			return;
		}
		
		if(index != -1 && index != -2){//判断，如果有数据不为-1，才能进行索引，否则报错
			switch (requestCode) {
			case SUPNAMECODE:
				break;
			case RECBASECODE:
					receiveBase.setText(orgList.get(index).get("words"));
					orgValue = orgList.get(index).get("value");
				break;
			case PUCSTATUSCODE:
					purchaseStatus.setText(statusList.get(index).get("words"));
					statusValue = statusList.get(index).get("value");
				break;
			case PUCTYPECODE:
					purchaseType.setText(typeList.get(index).get("words"));
					typeValue = typeList.get(index).get("value");
				break;
			default:
				break;
			}
		}else if(index == -2){
			switch (requestCode) {
			case RECBASECODE:
					receiveBase.setText("全部");
				break;
			case PUCSTATUSCODE:
					purchaseStatus.setText("全部");
				break;
			case PUCTYPECODE:
					purchaseType.setText("全部");
				break;
			default:
				break;
			}
		}else{
			switch (requestCode) {
			case SUPNAMECODE:
				Bundle resultBundle = data.getExtras();
				Supplier supplier = resultBundle.getParcelable("supplier");
				supplierName.setText(supplier.getSupplierName());
				supplierValue = supplier.getSupplierCode();
				break;
			default:
				return;
			}
		}
	}
	

}
