package com.cmbc.android.service_assistant.activity;

import java.util.HashMap;
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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.ListViewActivity;
import com.cmbc.android.service_assistant.api.MyHandler;
import com.cmbc.android.service_assistant.api.MyPageListAdapter;
import com.cmbc.android.service_assistant.api.NetService;
import com.cmbc.android.service_assistant.api.OtherService;
import com.cmbc.android.service_assistant.api.RefreshableView;
import com.cmbc.android.service_assistant.api.RefreshableView.PullToRefreshListener;
import com.cmbc.android.service_assistant.entity.PurchaseOrder;
import com.cmbc.android.service_assistant.entity.User;

@SuppressLint("HandlerLeak")
public class PurchaseSearchActivity extends ListViewActivity implements OnClickListener,OnScrollListener, OnItemClickListener{
	
	private static final int PURCHASE = 55;
	
	private TextView title;
	private ImageView backIcon,filterImg;
	private Map<String, Object> map;
	private List<PurchaseOrder> list;
	private Dialog progressDialog;
	private Bundle bundle;
	private User userInfo;
	private RefreshableView refreshableView;
	private int totalPage,totalLine,pageNo;
	private Map<String, Object> conditionMap;
	private MyPageListAdapter myPageListAdapter;
	private int visibleLastIndex = 0;   //���Ŀ���������  
	private int visibleItemCount;       // ��ǰ���ڿɼ�������  
	private String purchaseStatus = "";
	
	private MyHandler myHandler = new MyHandler(this){
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			refreshableView.finishRefreshing();  
			super.handleMessage(msg,progressDialog);
			if(passPort){
				map = (Map<String, Object>) msg.obj;
				totalLine = (Integer) map.get("totalLine"); 
				totalPage = (Integer) map.get("totalPages");
				pageNo = (Integer) map.get("pageNo");
				list = (List<PurchaseOrder>) map.get("list");
				if(pageNo == 1){
					myPageListAdapter = new MyPageListAdapter(PurchaseSearchActivity.this, list, PURCHASE );
					listView.setAdapter(myPageListAdapter);
				}else{
					list = (List<PurchaseOrder>) myPageListAdapter.addItem(list);
					myPageListAdapter.notifyDataSetChanged();
					listView.setSelection(visibleLastIndex - visibleItemCount + 1); //����ѡ����  
				}
			}else{
				
			}
			
		}
	};
	
	private Handler flushHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			loadMoreText.setText("���ظ���");
			NetService.getPurchaseOrderList(myHandler, userInfo.getLoginName(),userInfo.getToken() , userInfo.getId(), null);
			conditionMap = null;
		}
	};
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCustormLayout(R.layout.activity_test, R.layout.titlebar_back_title_action);
		initial();
		refreshableView = setRefreshListview(R.id.refreshable_view, R.id.listview);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				Message message = new Message();
				flushHandler.sendMessage(message);
			}
		}, 4);
		
		listView.setOnScrollListener(this);
		listView.setOnItemClickListener(this);
		loadMoreText.setOnClickListener(this);
		search_tv.setOnClickListener(this);
		
		//�ɹ�������ѯʱ����Ҫͳ��ʱ�����֣�gong���ص�
		when_count_tv.setVisibility(View.GONE);
		
		bundle = getIntent().getExtras();
		userInfo = bundle.getParcelable("userInfo");
		purchaseStatus = getIntent().getStringExtra("purchaseStatus");
		//�ж��Ƿ��ǴӲɹ���ͳ����ת������
		if(purchaseStatus != null){
			conditionMap = new HashMap<String, Object>();
			conditionMap.put("status", purchaseStatus);
		}
		
		progressDialog = OtherService.createTransparentProgressDialog(this);
		NetService.getPurchaseOrderList(myHandler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(), conditionMap);
		
	}
	
	private void initial(){
		title = (TextView) findViewById(R.id.titlebar_back_title_action_tv);
		backIcon = (ImageView) findViewById(R.id.titlebar_back_title_action_backimg);
		filterImg = (ImageView) findViewById(R.id.titlebar_back_title_action_actionimg);
		
		title.setText("�ɹ�����ѯ");
		backIcon.setOnClickListener(this);
		filterImg.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.titlebar_back_title_action_backimg:
			finish();
			break;
		case R.id.titlebar_back_title_action_actionimg:
			Intent intent = new Intent(this, PurchaseFilterActivity.class);
			intent.putExtras(bundle);
			startActivityForResult(intent,0);
			break;
		case R.id.refreshable_tv:
			String orderNo = search_edit.getText().toString().trim();
			if(TextUtils.isEmpty(orderNo)){
				Toast.makeText(this, "����������", Toast.LENGTH_SHORT).show();
			}else{
				conditionMap = new HashMap<String, Object>();
				conditionMap.put("poNo", orderNo);
				NetService.getPurchaseOrderList(myHandler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(), conditionMap);
			}
			break;
		case R.id.load_more_text_tv:
			 if(conditionMap == null)
				 conditionMap = new HashMap<String, Object>();
			 String loadText = loadMoreText.getText().toString();
			 if(loadText.equals(""))
				return; 
			 if(pageNo == totalPage )
				 loadMoreText.setText("�Ѿ���������");
			 else{
				 setCondition();
				 NetService.getPurchaseOrderList(myHandler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(), conditionMap);
			 }
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent intent = new Intent(this, PurchaseDetailInfoActivity.class);
		bundle.putString("batch", list.get(position).getBatch_no());
		bundle.putString("no",list.get(position).getPo_no());
		bundle.putString("supplier", list.get(position).getSupplier_names());
		bundle.putString("org", list.get(position).getOrganization_name());
		bundle.putString("tibaoren", list.get(position).getDirector_user_name());
		bundle.putString("status", list.get(position).getStatus());
		bundle.putString("type", list.get(position).getType());
		bundle.putString("tibaoTime", list.get(position).getCreate_date());
		intent.putExtras(bundle);
		startActivity(intent);
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		  this.visibleItemCount = visibleItemCount;  
	       visibleLastIndex = firstVisibleItem + visibleItemCount - 1;  
	       if(visibleItemCount != 0 && (totalItemCount-1) == totalLine && (listView.getLastVisiblePosition()+1) == totalItemCount  ){
	    	   loadMoreText.setText("");
	        }
	       //�ж�������ݻ��У�loadMoreӦ�ñ�ء����ظ��ࡱ
	        if(visibleItemCount != 0 && visibleItemCount == totalItemCount ){
	        	loadMoreText.setText("���ظ���");
	        }
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = myPageListAdapter.getCount() - 1;    //���ݼ����һ������� 
        System.out.println("��ǰ�����˹�:"+myPageListAdapter.getCount());
        int lastIndex = itemsLastIndex + 1;             //���ϵײ���loadMoreView��  
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {  
        	System.out.println("totalLine="+totalLine);
        	if(myPageListAdapter.getCount() < totalLine){
        		  //������Զ�����,��������������첽�������ݵĴ���  
        			progressDialog = null;
                  progressDialog = OtherService.createTransparentProgressDialog(this);
                  //�ٴη��ʶ�������conditionMap�������
                  if(conditionMap == null)
                	  conditionMap = new HashMap<String, Object>();
                  setCondition();
                  NetService.getPurchaseOrderList(myHandler, userInfo.getLoginName(), userInfo.getToken(),userInfo.getId(),conditionMap);         
                  loadMoreText.setText("���ڼ��ظ�������...");
        	}else{
        		loadMoreText.setText("�Ѿ���������");
        	}       
        }  
		
	}
	
	 //���������ķ���
    private void setCondition(){
    	if(pageNo < totalPage){
    		conditionMap.put("pageNo", String.valueOf(++pageNo));
    		System.out.println("mapɸѡ���ϵĴ�С="+conditionMap.size());
    	}
    }
    
    /**
     * �����startActivityForResult�򿪵�Activity��singleTaskģʽ����ôonActivityResult�ᱻ��������
     * �ҵ��Ǹ�activity���ر�ʱ���ᴥ��onActivityResult
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if(resultCode != RESULT_OK){
    		return;
    	}else{
    		if(conditionMap == null){
    			conditionMap = new HashMap<String, Object>();
    		}
    		conditionMap.put("pageNo", "1");
    		conditionMap.put("status", data.getStringExtra("status"));
    		conditionMap.put("type", data.getStringExtra("type"));
    		conditionMap.put("supplier_code", data.getStringExtra("supplier_code"));
    		conditionMap.put("orgCode", data.getStringExtra("orgCode"));
    		NetService.getPurchaseOrderList(myHandler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(), conditionMap);
    		progressDialog = OtherService.createTransparentProgressDialog(this);
    	}
    	
    	
    }
}
