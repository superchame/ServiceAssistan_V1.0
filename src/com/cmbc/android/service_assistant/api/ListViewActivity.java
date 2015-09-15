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
	    private View loadMoreView;  //�б�ײ��ļ�����ʾ����
	    public TextView loadMoreText;
	    public RefreshableView refreshableView;
	    //����������������ť������ʱ����Ҫ��ʾ��ռ��λ�ÿռ䣬ֱ��gone�������磬��ͳ��ҳ����
		public View searchView;
	    public TextView search_tv;
	    public EditText search_edit;
	    //������ͳ�ƺͲɹ���ͳ��ʱ����Ҫ�õ�ɸѡ������ʾ������ʱ����Ҫ��ʾ��ռ��λ�ÿռ䣬ֱ��gone��
	    public TextView when_count_tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}  
	
	/**
	 * ������Ϊlist������һ���������Ŀؼ��в���������
	 * �����refreshableView��������ؼ�
	 * @param refreshableViewId
	 * @param listViewId
	 * @return
	 */
	public  RefreshableView setRefreshListview(int refreshableViewId , int listViewId){
		setContentView(R.layout.new_refreshableview);
		search_tv = (TextView) findViewById(R.id.refreshable_tv);
		search_edit = (EditText) findViewById(R.id.refreshable_edit);
		searchView = findViewById(R.id.refreshable_ll);
		
		//ͳ��ʱ������,����Ҫʱ���ڶ�Ӧ��ҳ��gone��
		when_count_tv = (TextView) findViewById(R.id.refreshable_when_count_tv);
		
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
