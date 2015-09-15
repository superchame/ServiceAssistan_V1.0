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
import com.cmbc.android.service_assistant.entity.Order;
import com.cmbc.android.service_assistant.entity.User;

@SuppressLint("HandlerLeak")
public class OrderSearchActivity extends ListViewActivity implements OnClickListener, OnScrollListener ,OnItemClickListener{
	//将中文转换对应值需要的标识常量
	private static final int ORDERSTATUS = 1;
	//标识设置的筛选时间的类型的标识常量
	private static final int BEGINTIME = 3;
	private static final int ENDTIME = 4;
	//标识，让MyPageAdapter返回order的itemView
	private static final int ORDER = 44;
	
	private RefreshableView refreshableView;//下拉刷新控件
	private ImageView backIcon, actionBar;//标题栏上的
	private TextView titleText;//标题控件
	private Bundle bundle;//存放数据的对象
	private User userInfo;//bundle中存放的当前用户数据对象
	private Map<String, Object> map = null;
	private List<Order> orderList ;
	private Dialog progressDialog;
	private int visibleLastIndex = 0;   //最后的可视项索引  
	private int visibleItemCount;       // 当前窗口可见项总数  
	private MyPageListAdapter myPageListAdapter;
	private int totalPages,totalLine,pageNo;
	private Map<String, Object> conditionMap = null;//筛选条件集合
	private String orderStatus = "";//订单状态
	
 	private MyHandler handler = new MyHandler(this){
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			refreshableView.finishRefreshing();  
			super.handleMessage(msg,progressDialog);
			//查看通行证，为true可以进行后续操作，避免崩溃
			if(passPort){
				map = (Map<String, Object>) msg.obj;
				pageNo = (Integer) map.get("pageNo");
				totalLine = (Integer) map.get("totalLine");
				totalPages = (Integer) map.get("totalPages");
				orderList = (List<Order>) map.get("orderList");
				
				if(pageNo == 1){
					myPageListAdapter = new MyPageListAdapter(OrderSearchActivity.this, orderList, ORDER);
					listView.setAdapter(myPageListAdapter);
				}else if(pageNo >1){
					orderList = (List<Order>) myPageListAdapter.addItem(orderList);//必须获取最新的集合，否则点击item找不到信息
					myPageListAdapter.notifyDataSetChanged(); //数据集变化后,通知adapter  
	                listView.setSelection(visibleLastIndex - visibleItemCount + 1); //设置选中项  
				}
			}else{
			}
				
		}
		
	};

	private Handler flushHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			loadMoreText.setText("加载更多");
			//下拉访问网络，重置条件map
			if(conditionMap == null || conditionMap.size() == 1){
				conditionMap = null;
				NetService.getOrderList(handler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(), conditionMap);
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
               flushHandler.sendMessage(message);
              //  refreshableView.finishRefreshing();  
			}
		}, 3);
		
		listView.setOnScrollListener(this);     //添加滑动监听  ,必须放在setRefreshListview之后，因为setRefreshListview之后listview才实例化
		listView.setOnItemClickListener(this);//添加item被点击监听
		loadMoreText.setOnClickListener(this);
		
		search_tv.setOnClickListener(this);
		
		//订单查询时不需要统计时的文字，gong隐藏掉
		when_count_tv.setVisibility(View.GONE);
		
		bundle = getIntent().getExtras();
		userInfo = bundle.getParcelable("userInfo");
		
		//查询页面可能是从统计页面跳转的
		orderStatus = getIntent().getStringExtra("orderStatus");
		if(orderStatus != null){
			conditionMap = new HashMap<String, Object>();
			conditionMap.put("orderState", orderStatus);
		}
		
		progressDialog = OtherService.createTransparentProgressDialog(this);
		//加载界面时就访问网络订单数据
		NetService.getOrderList(handler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(), conditionMap);
	}
	
	
	 
	 public void  initial(){
		 titleText = (TextView) findViewById(R.id.titlebar_back_title_action_tv);
		 titleText.setText("订单查询");
		 
		 backIcon = (ImageView) findViewById(R.id.titlebar_back_title_action_backimg);
		 backIcon.setOnClickListener(this);
		 
		 actionBar = (ImageView) findViewById(R.id.titlebar_back_title_action_actionimg);
		 actionBar.setOnClickListener(this);
		
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
			 String loadText = loadMoreText.getText().toString();
			 if(loadText.equals(""))
				return; 
			 if(pageNo == totalPages )
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
			intent.putExtras(bundle);
			startActivityForResult(intent, 0);
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
        
        

      //即使是在初始化listview的item的时候，用户没有拖动listview，也会调用该方法
        /*
         * 判断listview的item总数和服务器那边的totalLine是否相同，
         *  且显示的最后一项的索引+1的值是不是等于listview的item总的条数，
         *  那么“加载更多”的底部文字控件就不用显示了
         */
       
        if(visibleItemCount != 0 && (totalItemCount-1) == totalLine && (listView.getLastVisiblePosition()+1) == totalItemCount  ){
        	loadMoreText.setText("");
        }
        
        //判断如果数据还有，loadMore应该变回“加载更多”
        if(visibleItemCount != 0 && visibleItemCount == totalItemCount ){
        	loadMoreText.setText("加载更多");
        }
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
        	System.out.println("totalLine="+totalLine);
        	if(myPageListAdapter.getCount() < totalLine){
        		  //如果是自动加载,可以在这里放置异步加载数据的代码  
        			progressDialog = null;
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
    		System.out.println("map筛选集合的大小="+conditionMap.size());
    	}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(resultCode == RESULT_OK){
    		if(conditionMap == null){
    			conditionMap = new HashMap<String, Object>();
    		}
    		//移除pageNo或者覆盖pageNo为1
    		conditionMap.put("pageNo", "1");
    		conditionMap.put("createOrderTimeFrom", OtherService.timeFactory(data.getStringExtra("beginTime"),BEGINTIME));
    		conditionMap.put("createOrderTimeTo",OtherService.timeFactory(data.getStringExtra("endTime"),ENDTIME));
    		conditionMap.put("orderType", data.getStringExtra("orderType"));
    		conditionMap.put("orderState", OtherService.getWordsValue(data.getStringExtra("orderStatus"),ORDERSTATUS));
    		conditionMap.put("distributeOrg", data.getStringExtra("store"));
    		NetService.getOrderList(handler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(), conditionMap);
    		progressDialog = OtherService.createTransparentProgressDialog(this);
    	}
    }



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, OrderDetailActivity.class);
		bundle.putString("orderNo", orderList.get(position).getRtn_no());//实际上服务器是根据tn_no查询的，这里当做orderNo取名
		intent.putExtras(bundle);
		startActivity(intent);
	}
    
}

