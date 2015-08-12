package com.cmbc.android.service_assistant.api;


import com.cmbc.android.service_assistant.R;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author ��������android�� �ų�
 * @see ����̳���TitleActivity�����໹��һ��
 * setRefreshListview��...��������
 * layoutId��
 * 
 * �÷�������һ����ˢ�µ�listview�Ŀؼ�
 * 
 */
public class ListViewActivity extends TitleActivity{
	
	 	public ListView listView;  
	    private View loadMoreView;  
	    public TextView loadMoreText;
	    public RefreshableView refreshableView;
	    //����������������ť
	    public TextView search_tv;
	    public EditText search_edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}  
	
	public  RefreshableView setRefreshListview(int refreshableViewId , int listViewId){
		setContentView(R.layout.refreshableview);
		search_tv = (TextView) findViewById(R.id.refreshable_tv);
		search_edit = (EditText) findViewById(R.id.refreshable_edit);
		
		refreshableView = (RefreshableView)findViewById(refreshableViewId); 
		listView = (ListView)refreshableView.findViewById(listViewId); 
		
		//�����б�ײ���ͼ���
		loadMoreView = getLayoutInflater().inflate(R.layout.load_more_text, null);  
	    loadMoreText = (TextView) loadMoreView.findViewById(R.id.load_more_text_tv);
	    listView.addFooterView(loadMoreView);   //�����б�ײ���ͼ      
		listView.setCacheColorHint(0);
		return refreshableView;
	}



	  
}
