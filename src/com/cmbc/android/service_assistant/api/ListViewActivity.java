package com.cmbc.android.service_assistant.api;


import com.cmbc.android.service_assistant.R;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author 民生电商android部 张超
 * @see 该类继承于TitleActivity，该类还有一个
 * setRefreshListview（...）方法，
 * layoutId：
 * 
 * 该方法返回一个可刷新的listview的控件
 * 
 */
public class ListViewActivity extends TitleActivity{
	
	 	public ListView listView;  
	    private View loadMoreView;  
	    public TextView loadMoreText;
	    public RefreshableView refreshableView;
	    //搜索栏，和搜索按钮
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
		
		//设置列表底部视图填充
		loadMoreView = getLayoutInflater().inflate(R.layout.load_more_text, null);  
	    loadMoreText = (TextView) loadMoreView.findViewById(R.id.load_more_text_tv);
	    listView.addFooterView(loadMoreView);   //设置列表底部视图      
		listView.setCacheColorHint(0);
		return refreshableView;
	}



	  
}
