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
	    private View loadMoreView;  //列表底部的加载提示文字
	    public TextView loadMoreText;
	    public RefreshableView refreshableView;
	    //搜索栏，和搜索按钮，其他时候不需要显示和占用位置空间，直接gone掉，例如，在统计页面里
		public View searchView;
	    public TextView search_tv;
	    public EditText search_edit;
	    //做订单统计和采购单统计时，需要用到筛选文字显示；其他时候不需要显示和占用位置空间，直接gone掉
	    public TextView when_count_tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}  
	
	/**
	 * 正是因为list包含在一个可下拉的控件中才能下拉，
	 * 这里的refreshableView就是这个控件
	 * @param refreshableViewId
	 * @param listViewId
	 * @return
	 */
	public  RefreshableView setRefreshListview(int refreshableViewId , int listViewId){
		setContentView(R.layout.new_refreshableview);
		search_tv = (TextView) findViewById(R.id.refreshable_tv);
		search_edit = (EditText) findViewById(R.id.refreshable_edit);
		searchView = findViewById(R.id.refreshable_ll);
		
		//统计时的文字,不需要时，在对应的页面gone掉
		when_count_tv = (TextView) findViewById(R.id.refreshable_when_count_tv);
		
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
