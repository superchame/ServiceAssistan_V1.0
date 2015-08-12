package com.cmbc.android.service_assistant.activity;



import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.NetService;
import com.cmbc.android.service_assistant.api.OtherService;
import com.cmbc.android.service_assistant.api.ParseTools;
import com.cmbc.android.service_assistant.api.TitleActivity;
import com.cmbc.android.service_assistant.entity.DeliveryOrder;
import com.cmbc.android.service_assistant.entity.DeliveryOrderDetail;
import com.cmbc.android.service_assistant.entity.User;
import com.example.qr_codescan.MipcaActivityCapture;



@SuppressLint("HandlerLeak")
public class SearchDeliveryOrderActivity extends TitleActivity implements OnClickListener{
	
	//常量
	private static final int SUCCESS = 1;
	private static final int ERROR = 0;
	
	private TextView titleText;
	private ImageView backIcon,qrCodeIcon;
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private EditText deliveryOrder_et;
	private Button search_btn;
	private String jsonString,responseString;
	private Map<String, Object> map;
	private Bundle bundle;
	private User userInfo;
	private Dialog progressDialog;
	
	
	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				jsonString = (String)msg.obj;
				map = ParseTools.getDeliveryOrderResponse(jsonString);
				responseString = (String)map.get("responseString");
				if(responseString != null && responseString.equals("操作成功！")){
					DeliveryOrder deliveryOrder = new DeliveryOrder();
					deliveryOrder.setOutNo((String)map.get("outNo"));
					deliveryOrder.setShopName((String)map.get("shopName"));
					deliveryOrder.setGoodsSum(Integer.parseInt((String)map.get("goodsSum")));
					deliveryOrder.setTotalGoodsSum(Integer.parseInt((String)map.get("totalGoodsSum")));
					deliveryOrder.setOrderCnt(Integer.parseInt((String)map.get("orderCnt")));
		
					Intent intent = new Intent(SearchDeliveryOrderActivity.this, DeliveryOrderInfoActivity.class);
					List<DeliveryOrderDetail> list = (List<DeliveryOrderDetail>) map.get("goodsList");
					for(int i = 1; i < list.size()+1 ; i++){
						DeliveryOrderDetail deliveryOrderDetail = list.get(i-1);
						bundle.putParcelable("deliveryOrderDetail"+i, deliveryOrderDetail);
					}
					bundle.putParcelable("deliveryOrder", deliveryOrder);
					intent.putExtras(bundle);
					progressDialog.dismiss();
					startActivity(intent);
				}else{
					progressDialog.dismiss();
					Toast.makeText(SearchDeliveryOrderActivity.this, responseString, Toast.LENGTH_SHORT).show();
				}
				break;
			case ERROR:
				progressDialog.dismiss();
				Toast.makeText(SearchDeliveryOrderActivity.this, "亲，你没有网络哦", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCustormLayout(R.layout.activity_search_delivery_order, R.layout.titlebar_back_title);
		initialView();
		titleText.setText("出库单查询");
		
		bundle = getIntent().getExtras();
		userInfo = bundle.getParcelable("userInfo");
		
		backIcon.setOnClickListener(this);
		qrCodeIcon.setOnClickListener(this);
		search_btn.setOnClickListener(this);
	}
	
	//UI中所有需要用到的，且在初始界面时的控件的实例化
	public void initialView(){
		titleText = (TextView) findViewById(R.id.titlebar_back_title_tv);
		backIcon = (ImageView) findViewById(R.id.titlebar_back_title_img);
		qrCodeIcon = (ImageView) findViewById(R.id.activity_search_delivery_order_img);
		deliveryOrder_et = (EditText) findViewById(R.id.activity_search_delivery_order_et);
		search_btn = (Button) findViewById(R.id.activity_search_delivery_order_btn);
		
		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.titlebar_back_title_img:
			//关闭该Activity
			finish();
			break;
		case R.id.activity_search_delivery_order_img:
			Intent intent = new Intent(this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			/*
			 * 如果添加了FLAG_ACTIVITY_CLEAR_TOP标记，
			 * 并且目标Activity在栈中已经存在，
			 * 则将会把位于该目标activity之上的activity从栈中弹出销毁
			 */
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			break;
		case R.id.activity_search_delivery_order_btn:
			String deliveryOrderNum = deliveryOrder_et.getText().toString().trim();
			if(!TextUtils.isEmpty(deliveryOrderNum)){
				//弹出进度对话框
				progressDialog = OtherService.createTransparentProgressDialog(SearchDeliveryOrderActivity.this);
				//使用网络查询出库单服务
				NetService.searchDeliveryOrder(handler, userInfo.getLoginName(), userInfo.getToken() ,deliveryOrderNum);	
				
				
			}else{
				Toast.makeText(this, "请输入出库单号", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
		
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				//显示扫描到的内容
				deliveryOrder_et.setText(data.getStringExtra("result"));			
			}
			break;
		}
    }	
}
