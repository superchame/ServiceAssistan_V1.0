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
	//������ת����Ӧֵ��Ҫ�ı�ʶ����
	private static final int ORDERSTATUS = 1;
	//��ʶ���õ�ɸѡʱ������͵ı�ʶ����
	private static final int BEGINTIME = 3;
	private static final int ENDTIME = 4;
	//��ʶ����MyPageAdapter����order��itemView
	private static final int ORDER = 44;
	
	private RefreshableView refreshableView;//����ˢ�¿ؼ�
	private ImageView backIcon, actionBar;//�������ϵ�
	private TextView titleText;//����ؼ�
	private Bundle bundle;//������ݵĶ���
	private User userInfo;//bundle�д�ŵĵ�ǰ�û����ݶ���
	private Map<String, Object> map = null;
	private List<Order> orderList ;
	private Dialog progressDialog;
	private int visibleLastIndex = 0;   //���Ŀ���������  
	private int visibleItemCount;       // ��ǰ���ڿɼ�������  
	private MyPageListAdapter myPageListAdapter;
	private int totalPages,totalLine,pageNo;
	private Map<String, Object> conditionMap = null;//ɸѡ��������
	private String orderStatus = "";//����״̬
	
 	private MyHandler handler = new MyHandler(this){
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			refreshableView.finishRefreshing();  
			super.handleMessage(msg,progressDialog);
			//�鿴ͨ��֤��Ϊtrue���Խ��к����������������
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
					orderList = (List<Order>) myPageListAdapter.addItem(orderList);//�����ȡ���µļ��ϣ�������item�Ҳ�����Ϣ
					myPageListAdapter.notifyDataSetChanged(); //���ݼ��仯��,֪ͨadapter  
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
			//�����������磬��������map
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
		initial();//����ֻ�ܼ��ر������ؼ�����Ϊ������ˢ�²��ֻ�û�м��ؽ�������Ȼ���Ŀؼ�Ҳ���ܲ���
		
		//������ˢ�¿ؼ�
		refreshableView = setRefreshListview(R.id.refreshable_view, R.id.listview);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			
			@Override
			public void onRefresh() {
               Message message = new Message();
               flushHandler.sendMessage(message);
              //  refreshableView.finishRefreshing();  
			}
		}, 3);
		
		listView.setOnScrollListener(this);     //��ӻ�������  ,�������setRefreshListview֮����ΪsetRefreshListview֮��listview��ʵ����
		listView.setOnItemClickListener(this);//���item���������
		loadMoreText.setOnClickListener(this);
		
		search_tv.setOnClickListener(this);
		
		//������ѯʱ����Ҫͳ��ʱ�����֣�gong���ص�
		when_count_tv.setVisibility(View.GONE);
		
		bundle = getIntent().getExtras();
		userInfo = bundle.getParcelable("userInfo");
		
		//��ѯҳ������Ǵ�ͳ��ҳ����ת��
		orderStatus = getIntent().getStringExtra("orderStatus");
		if(orderStatus != null){
			conditionMap = new HashMap<String, Object>();
			conditionMap.put("orderState", orderStatus);
		}
		
		progressDialog = OtherService.createTransparentProgressDialog(this);
		//���ؽ���ʱ�ͷ������綩������
		NetService.getOrderList(handler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(), conditionMap);
	}
	
	
	 
	 public void  initial(){
		 titleText = (TextView) findViewById(R.id.titlebar_back_title_action_tv);
		 titleText.setText("������ѯ");
		 
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
				 loadMoreText.setText("�Ѿ���������");
			 else{
				 setCondition();
				 NetService.getOrderList(handler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(), conditionMap);
			 }
			 break;
		case R.id.refreshable_tv:
			String orderNo = search_edit.getText().toString().trim();
			if(TextUtils.isEmpty(orderNo)){
				Toast.makeText(this, "����������", Toast.LENGTH_SHORT).show();
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
	  * ����ʱ������
     * firstVisibleItem ��ʾ�ڵ�ǰ��Ļ��ʾ�ĵ�һ��listItem������listView�����λ�ã��±��0��ʼ�� 
     * visibleItemCount��ʾ����ʱ��Ļ���Լ�����ListItem(������ʾ��ListItemҲ��)���� 
     * totalItemCount��ʾListView��ListItem����  
     * listView.getLastVisiblePosition()��ʾ����ʱ��Ļ���һ��ListItem 
     * (���ListItemҪ��ȫ��ʾ��������)������ListView��λ�ã��±��0��ʼ��  
     */  
    @Override  
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {  
        this.visibleItemCount = visibleItemCount;  
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;  
        
        

      //��ʹ���ڳ�ʼ��listview��item��ʱ���û�û���϶�listview��Ҳ����ø÷���
        /*
         * �ж�listview��item�����ͷ������Ǳߵ�totalLine�Ƿ���ͬ��
         *  ����ʾ�����һ�������+1��ֵ�ǲ��ǵ���listview��item�ܵ�������
         *  ��ô�����ظ��ࡱ�ĵײ����ֿؼ��Ͳ�����ʾ��
         */
       
        if(visibleItemCount != 0 && (totalItemCount-1) == totalLine && (listView.getLastVisiblePosition()+1) == totalItemCount  ){
        	loadMoreText.setText("");
        }
        
        //�ж�������ݻ��У�loadMoreӦ�ñ�ء����ظ��ࡱ
        if(visibleItemCount != 0 && visibleItemCount == totalItemCount ){
        	loadMoreText.setText("���ظ���");
        }
    }  



    /**
     * ����״̬�ı�ʱ������ 
     *scrollState������״̬���ֱ���SCROLL_STATE_IDLE��SCROLL_STATE_TOUCH_SCROLL��SCROLL_STATE_FLING
     *SCROLL_STATE_IDLE�ǵ���Ļֹͣ����ʱ
     *SCROLL_STATE_TOUCH_SCROLL�ǵ��û����Դ�����ʽ������Ļ������ָ��Ȼ������Ļ��ʱ��The user is scrolling using touch, and their finger is still on the screen��
     *SCROLL_STATE_FLING�ǵ��û�����֮ǰ������Ļ��̧����ָ����Ļ�������Ի���ʱ��The user had previously been scrolling using touch and had performed a fling��
     */  
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
                  NetService.getOrderList(handler, userInfo.getLoginName(), userInfo.getToken(),userInfo.getId(),conditionMap);         
                  loadMoreText.setText("���ڼ��ظ�������...");
        	}else{
        		loadMoreText.setText("�Ѿ���������");
        	}       
        }  
    }  
	
    
    //���������ķ���
    public void setCondition(){
    	if(pageNo < totalPages){
    		conditionMap.put("pageNo", String.valueOf(++pageNo));
    		System.out.println("mapɸѡ���ϵĴ�С="+conditionMap.size());
    	}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(resultCode == RESULT_OK){
    		if(conditionMap == null){
    			conditionMap = new HashMap<String, Object>();
    		}
    		//�Ƴ�pageNo���߸���pageNoΪ1
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
		bundle.putString("orderNo", orderList.get(position).getRtn_no());//ʵ���Ϸ������Ǹ���tn_no��ѯ�ģ����ﵱ��orderNoȡ��
		intent.putExtras(bundle);
		startActivity(intent);
	}
    
}

