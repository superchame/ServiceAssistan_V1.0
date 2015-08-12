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
	//����
	private static final int SUCCESS = 1;
	private static final int ERROR = 0;
	private static final int REFRESH_SUCCESS = 2;
	
	private static final int FILTERCODE = 100;

	private RefreshableView refreshableView;//����ˢ�¿ؼ�
	private ImageView backIcon, actionBar;//�������ϵ�
	private TextView titleText;//����ؼ�
	private Bundle bundle;//������ݵĶ���
	private User userInfo;//bundle�д�ŵĵ�ǰ�û����ݶ���
	private String	jsonString, responseString;
	private Map<String, Object> map = null;
	private List<Order> orderList ;
	private Dialog progressDialog;
	private int visibleLastIndex = 0;   //���Ŀ���������  
	private int visibleItemCount;       // ��ǰ���ڿɼ�������  
	private MyPageListAdapter myPageListAdapter;
	private int totalPages,totalLine,pageNo;
	private Map<String, Object> conditionMap;//ɸѡ��������
	
	
 	private Handler handler = new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				jsonString = (String) msg.obj;
				map = ParseTools.getOrderListResponse(jsonString);
				responseString = (String) map.get("responseString");
				if(responseString != null && responseString.equals("�����ɹ���")){
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
						myPageListAdapter.notifyDataSetChanged(); //���ݼ��仯��,֪ͨadapter  
		                listView.setSelection(visibleLastIndex - visibleItemCount + 1); //����ѡ����  
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
				//�����������磬��������map
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
		initial();//����ֻ�ܼ��ر������ؼ�����Ϊ������ˢ�²��ֻ�û�м��ؽ�������Ȼ���Ŀؼ�Ҳ���ܲ���
		
		//������ˢ�¿ؼ�
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
		
		listView.setOnScrollListener(this);     //��ӻ�������  ,�������setRefreshListview֮����ΪsetRefreshListview֮��listview��ʵ����
		loadMoreText.setOnClickListener(this);
		
		search_tv.setOnClickListener(this);
		
		bundle = getIntent().getExtras();
		userInfo = bundle.getParcelable("userInfo");
		progressDialog = OtherService.createTransparentProgressDialog(this);
		//���ؽ���ʱ�ͷ������綩������
		NetService.getOrderList(handler, userInfo.getLoginName(), userInfo.getToken(), userInfo.getId(),null);
	}
	
	
	 
	 public void  initial(){
		 titleText = (TextView) findViewById(R.id.titlebar_back_title_action_tv);
		 titleText.setText("������ѯ");
		 
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
			startActivityForResult(intent, FILTERCODE);
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
        	System.out.println("haha");
        	System.out.println("totalLine="+totalLine);
        	if(myPageListAdapter.getCount() < totalLine){
        		  //������Զ�����,��������������첽�������ݵĴ���  
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
    		System.out.println("map���ϵĴ�С="+conditionMap.size());
    	}
    }
}

