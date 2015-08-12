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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.ListViewActivity;
import com.cmbc.android.service_assistant.api.MyPageListAdapter;
import com.cmbc.android.service_assistant.api.NetService;
import com.cmbc.android.service_assistant.api.OtherService;
import com.cmbc.android.service_assistant.api.ParseTools;
import com.cmbc.android.service_assistant.api.RefreshableView;
import com.cmbc.android.service_assistant.api.RefreshableView.PullToRefreshListener;
import com.cmbc.android.service_assistant.entity.Order;
import com.cmbc.android.service_assistant.entity.User;

@SuppressLint("HandlerLeak")
public class OrderSearchActivity extends ListViewActivity implements OnClickListener, OnScrollListener {
	//常量
	private static final int SUCCESS = 1;
	private static final int ERROR = 0;
	private static final int REFRESH_SUCCESS = 2;
	
	private static final int FILTERCODE = 100;

	private RefreshableView refreshableView;//下拉刷新控件
	private ImageView backIcon, actionBar;//标题栏上的
	private TextView titleText;//标题控件
	private Bundle bundle;//存放数据的对象
	private User userInfo;//bundle中存放的当前用户数据对象
	private String	jsonString, responseString;
	private Map<String, Object> map = null;
	private List<Order> orderList ;
	private Dialog progressDialog;
	private int visibleLastIndex = 0;   //最后的可视项索引  
	private int visibleItemCount;       // 当前窗口可见项总数  
	private MyPageListAdapter myPageListAdapter;
	private int totalPages,totalLine,pageNo;
	private Map<String, Object> conditionMap;//筛选条件集合
	
	
 	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				jsonString = (String) msg.obj;
				map = ParseTools.getOrderListResponse(jsonString);
				responseString = (String) map.get("responseString");
				if(responseString != null && responseString.equals("操作成功！")){
					progressDialog.dismiss();
					pageNo = (Integer) map.get("pageNo");
					totalLine = (Integer) map.get("totalLine");
					totalPages = (Integer) map.get("totalPages");
					orderList = (List<Order>) map.get("orderList");
					
					if(pageNo == 1){
						myPageListAdapter = new MyPageListAdapter(OrderSearchActivity.this, orderList);
						listView.setAdapter(myPageListAdapter);
					}else{
						myPageListAdapter.addItem(orderList);
						myPageListAdapter.notifyDataSetChanged(); //数据集变化后,通知adapter  
		                listView.setSelection(visibleLastIndex - visibleItemCount + 1); //设置选中项  
					}
					
					
				}else{
					progressDialog.dismiss();
					Toast.makeText(OrderSearchActivity.this, responseString, Toast.LENGTH_SHORT).show();
				}
				break;
			case ERROR:
				progressDialog.dismiss();
				break;
			case REFRESH_SUCCESS:
				//下拉访问网络，重置条件map
				progressDialog = OtherService.createTransparentProgressDialog(OrderSearchActivity.this);
				if(conditionMap == null || conditionMap.size() == 1){
					conditionMap = null;
					NetService.getOrderList(handler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(), conditionMap);
				}
				break;
			default:
				break;
			}
		
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCustormLayout(R.layout.activity_test, R.layout.titlebar_back_title_action);
		initial();//这里只能加载标题栏控件，因为可下拉刷新布局还没有加载进来，当然他的控件也不能操作
		
		//可下拉刷新控件
		refreshableView = setRefreshListview(R.id.refreshable_view, R.id.listview);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			
			@Override
			public void onRefresh() {
               Message message = new Message();
               message.what = REFRESH_SUCCESS;
               handler.sendMessage(message);
                refreshableView.finishRefreshing();  
			}
		}, 3);
		
		listView.setOnScrollListener(this);     //添加滑动监听  ,必须放在setRefreshListview之后，因为setRefreshListview之后listview才实例化
		loadMoreText.setOnClickListener(this);
		
		search_tv.setOnClickListener(this);
		
		bundle = getIntent().getExtras();
		userInfo = bundle.getParcelable("userInfo");
		progressDialog = OtherService.createTransparentProgressDialog(this);
		//加载界面时就访问网络订单数据
		NetService.getOrderList(handler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(),null);
	}
	
	
	 
	 public void  initial(){
		 titleText = (TextView) findViewById(R.id.titlebar_back_title_action_tv);
		 titleText.setText("订单查询");
		 
		 backIcon = (ImageView) findViewById(R.id.titlebar_back_title_action_backimg);
		 backIcon.setOnClickListener(this);
		 
		 actionBar = (ImageView) findViewById(R.id.titlebar_back_title_action_actionimg);
		 actionBar.setOnClickListener(this);
		 actionBar.setImageResource(R.drawable.saixuan);	
		
	 }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.titlebar_back_title_action_backimg:
			finish();
			break;
		case R.id.load_more_text_tv:
			 if(conditionMap == null)
				 conditionMap = new HashMap<String, Object>();
			 if(pageNo == totalPages)
				 loadMoreText.setText("已经到底啦！");
			 else{
				 setCondition();
				 NetService.getOrderList(handler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(), conditionMap);
			 }
			 break;
		case R.id.refreshable_tv:
			String orderNo = search_edit.getText().toString().trim();
			if(TextUtils.isEmpty(orderNo)){
				Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
			}else{
				conditionMap = new HashMap<String, Object>();
				conditionMap.put("orderNo", orderNo);
				NetService.getOrderList(handler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(), conditionMap);
			}
			break;
		case R.id.titlebar_back_title_action_actionimg:
			Intent intent = new Intent(this, OrderFilterActivity.class);
			startActivityForResult(intent, FILTERCODE);
			break;
		default:
			break;
		}
		
	}



	 /** 
	  * 滑动时被调用
     * firstVisibleItem 表示在当前屏幕显示的第一个listItem在整个listView里面的位置（下标从0开始） 
     * visibleItemCount表示在现时屏幕可以见到的ListItem(部分显示的ListItem也算)总数 
     * totalItemCount表示ListView的ListItem总数  
     * listView.getLastVisiblePosition()表示在现时屏幕最后一个ListItem 
     * (最后ListItem要完全显示出来才算)在整个ListView的位置（下标从0开始）  
     */  
    @Override  
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {  
        this.visibleItemCount = visibleItemCount;  
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;  
    }  



    /**
     * 滑动状态改变时被调用 
     *scrollState有三种状态，分别是SCROLL_STATE_IDLE、SCROLL_STATE_TOUCH_SCROLL、SCROLL_STATE_FLING
     *SCROLL_STATE_IDLE是当屏幕停止滚动时
     *SCROLL_STATE_TOUCH_SCROLL是当用户在以触屏方式滚动屏幕并且手指仍然还在屏幕上时（The user is scrolling using touch, and their finger is still on the screen）
     *SCROLL_STATE_FLING是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时（The user had previously been scrolling using touch and had performed a fling）
     */  
    @Override  
    public void onScrollStateChanged(AbsListView view, int scrollState) {  
        int itemsLastIndex = myPageListAdapter.getCount() - 1;    //数据集最后一项的索引 
        System.out.println("当前加载了共:"+myPageListAdapter.getCount());
        int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项  
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {  
        	System.out.println("haha");
        	System.out.println("totalLine="+totalLine);
        	if(myPageListAdapter.getCount() < totalLine){
        		  //如果是自动加载,可以在这里放置异步加载数据的代码  
                  progressDialog = OtherService.createTransparentProgressDialog(this);
                  //再次访问订单，给conditionMap添加条件
                  if(conditionMap == null)
                	  conditionMap = new HashMap<String, Object>();
                  setCondition();
                  NetService.getOrderList(handler, userInfo.getLoginName(), userInfo.getToken(),userInfo.getId(),conditionMap);         
                  loadMoreText.setText("正在加载更多数据...");
        	}else{
        		loadMoreText.setText("已经到底啦！");
        	}       
        }  
    }  
	
    
    //设置条件的方法
    public void setCondition(){
    	if(pageNo < totalPages){
    		conditionMap.put("pageNo", String.valueOf(++pageNo));
    		System.out.println("map集合的大小="+conditionMap.size());
    	}
    }
}

