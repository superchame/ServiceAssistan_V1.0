package com.cmbc.android.service_assistant.api;




import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.api.RefreshableView.PullToRefreshListener;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.Toast;



@SuppressLint("HandlerLeak")
public class TestListviewActivity extends ListViewActivity {
	
	private RefreshableView refreshableView;
	private static final int SUCCESS = 1;

	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			
			if(msg.what == SUCCESS){
				Toast.makeText(TestListviewActivity.this, "成功", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setCustormLayout(R.layout.activity_test, R.layout.title_test);
		refreshableView = setRefreshListview(R.id.refreshable_view, R.id.listview);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			
			@Override
			public void onRefresh() {
				try {  
                	/*
                	 * 它的作用是当listview中的数据发生变化时，刷新listview。
                	 */
                	Message message = new Message();
					message.what = SUCCESS;
					handler.sendMessage(message);
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
				SystemClock.sleep(3000);
                refreshableView.finishRefreshing();  
			}
		}, 3);
	}
	
	 
	    
}
