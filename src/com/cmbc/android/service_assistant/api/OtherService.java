package com.cmbc.android.service_assistant.api;




import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.cmbc.android.service_assistant.R;
import com.cmbc.android.service_assistant.entity.User;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;



/**
 * @author 民生电商android部 张超
 * @see 一些其他服务，例如生成一些对话框
 */
public class OtherService{
	private static final String PHONE = "A001";
	private static final String TABLE = "A002";
	private static final String OTHER = "A000";
	
	//中文互换对应值需要的标识常量
	private static final int ORDERSTATUS = 1;
	private static final int ORDERTYPE = 2;
	private static final int PURCHASESTATUS = 6;
	private static final int PURCHASETYPE = 7;
	//标识设置的筛选时间的类型的标识常量
	private static final int BEGINTIME = 3;
	private static final int ENDTIME = 4;
	
	/*
	 * 记录用户登录是否重复登录，登陆界面会判断，如果有地方因为重复登录，
	 * 该属性会被记录为true再次跳到登陆界面，此时登陆界面再判断这个值，
	 * 并弹出重复登录的提醒吐司
	 */
	public static  boolean relogin = false;
	
	/*
	 * 记录凭证是否过期，在登陆页面判断，如果为false，没有过期，
	 * 如果出现网关返回的过期信息，修改为true，再次跳转到登陆界面
	 */
	public static  boolean overdue = false;
	
	
	//生成进度对话框，背景透明
	public static Dialog  createTransparentProgressDialog(Context context){
		 Dialog progressDialog = new Dialog(context, R.style.progress_dialog);   
		  progressDialog.setContentView(R.layout.dialog_transparent_progress);   
		  progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
		  progressDialog.setCancelable(false);//设置用户不可取消
		  progressDialog.show();
		  return progressDialog;
	}
	
	/**
	 * 获取当前系统时间的字符串
	 * @return String
	 */
	public static String getCurrentSystemTime(){
		
		//获取时间和日历对象,注意获取的日期和用户设置的日期的月份都要加1
		Calendar calendar = Calendar.getInstance();
		 int y = calendar.get(Calendar.YEAR);
		 int M = calendar.get(Calendar.MONTH);
		 int d = calendar.get(Calendar.DAY_OF_MONTH);
		 int h = calendar.get(Calendar.HOUR);
		 int m = calendar.get(Calendar.MINUTE);
		 int  s = calendar.get(Calendar.SECOND);
		 String now = y+""+(M+1)+""+d+""+h+""+m+""+s;
		 return now;
	}
	
	/**
	 * 判断设备类型
	 * @param context
	 * @return 设备类型字符串
	 */
	public static String DeviceType(Context context) {
		try {
			 if((context.getResources().getConfiguration().screenLayout
	                 & Configuration.SCREENLAYOUT_SIZE_MASK)
	                 >= Configuration.SCREENLAYOUT_SIZE_LARGE){
	        	 return TABLE;
	         }else{
	        	 return PHONE;
	         }       
		} catch (Exception e) {
			return OTHER;
		}
	}
	
	/**
	   * 获取版本号
	   * @return 当前应用的版本号
	   */
	  public static String getVersion(Activity activity) {
	     try {
	         PackageManager manager = activity.getPackageManager();
	         PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
	         String version = info.versionName;
	         return activity.getString(R.string.version_name) + version;
	     } catch (Exception e) {
	    	 e.printStackTrace();
	        return activity.getString(R.string.can_not_find_version_name);
	     }
	 }
	  
	  /**
	   * 生成自定义view对话框
	   */
	  public static void createViewDialog(Activity activity, String skuCode, String skuName, String spuCode, 
			  String outQty, String unit){
		  MyCustomDialog.Builder builder = new MyCustomDialog.Builder(activity);  
	        builder.setSkuCode(skuCode);  
	        builder.setSkuName(skuName);
	        builder.setSpuCode(spuCode);
	        builder.setOutQty(outQty);
	        builder.setUnit(unit);
	        builder.create().show();  
	        
		
	  }
	  
	  /**
	   * 
	   * @param 格式如2015-8-22 time
	   * @return 返回没有短横线的时间字符串，例如20150822
	   */
	  public static String timeFactory(String time, int index){
		 String time2 = "";
		 if(!time.equals("")){
			 String [] a = time.split("-");
			 for(int i = 0 ; i < a.length ; i++){
				 if(i == 1 || i == 2){
					 int value = Integer.parseInt(a[i]);
					 if(value < 10)
						 a[i] = "0"+a[i];
				 }
				 time2 += a[i];
			 }
			 switch (index) {
			case BEGINTIME:
				 return time2+"000000";
			case ENDTIME:
				return time2+"235959";
			}
		 }
		 return null;
	  }
	  
	  /**
	   * 
	   * @param words 原文字 
	   * @param index 类型
	   * @return 错误类型为0
	   */
	  public static String getWordsValue(String words, int index){
		switch (index) {
		case ORDERSTATUS:
			 if(words.equals("已出库")){
				  return "40";
			  }else if(words.equals("待签收")){
				  return "50";
			  }else if(words.equals("已取消")){
				  return "99";
			  }else if(words.equals("待出库")){
				  return "12";
			  }else{
				  return "";
			  }
		default:
			return "";
		}
	  }
	  
	  /**
	   * 
	   * @param value
	   * @return 返回值对应的订单类型
	   */
	  public static String getValueWords(String value, int index){
		  if(value != null){ 
			  switch (index) {
			case ORDERTYPE:
				 if(value.equals("1")){
					  return "仓配订单";
				  }else if(value.equals("2")) {
					  return "预售订单";
				  }else if(value.equals("3")){
					  return "现货订单";
				  }else if(value.equals("4")){
					  return "服务订单";
				  }else if(value.equals("5")){
					  return "宅配订单";
				  }else{
					  return "未知订单类型";
				  }
			case ORDERSTATUS:
				if(value.equals("50")){
					  return "待签收";
				  }else if(value.equals("99")) {
					  return "已取消";
				  }else if(value.equals("40")){
					  return "已出库";
				  }else{
					  return "待出库";
				  }
			case PURCHASESTATUS:
				if(value.equals("30")){
					return "全部收货";
				}else if (value.equals("31")) {
					return "部分收货";
				}else if (value.equals("20")) {
					return "已审核";
				}else if (value.equals("10")) {
					return "新建";
				}else {
					return "未知采购单状态";
				}
			case PURCHASETYPE:
				if(value.equals("1")){
					return "普通采购单";
				}else if(value.equals("2")){
					return "预售采购单";
				}else if(value.equals("3")){
					return "自动采购单";
				}else{
					return "未知采购单类型";
				}
			default:
				return "类型错误";
			}
		  }
		  return "值错误";
	  }
	  
	  /**
	   * 时间戳转化工具
	   * @return 从时间戳转化后的时间
	   */
	  public static String timeStamp(String str){
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  String date = sdf.format(new Date(Long.parseLong(str)));
		  return date;
	  }

	  /**
	   * function:传入一个时间20150303返回2015-03-03
	   * @param timetring
	   * @return string
	   */
	public static String timeFactoryAddLine(String timestring) {
		if(timestring != null){
			String year = timestring.substring(0,4);
			String month = timestring.substring(4,6);
			String day = timestring.substring(6,8);
			return year+"-"+month+"-"+day;
		}
		return null;
	}
	
	//记录用户信息的文件，只要用户没有退出登陆，下次点击软件直接登录
	public static void saveUserInfo(Context context, User userInfo){
		try{
			//SharedPreferences生成的文件会放置在/data/data/包名/shared_prefs/中,会自动以xml文件形式保存
			SharedPreferences sp = context.getSharedPreferences("config.ini", Context.MODE_PRIVATE);//设置为私有权限
			
			//获得一个编辑器对象
			Editor edit = sp.edit();
			
			//存储数据
			if(userInfo != null){
				edit.putString("loginName", userInfo.getLoginName());
				edit.putString("token",userInfo.getToken());
				edit.putString("id", userInfo.getId());
				edit.putString("orgId", userInfo.getOrgId());
				edit.putString("orgName", userInfo.getOrgName());
				edit.putString("realName", userInfo.getRealName());
			}
		
			
			//提交，数据真正的 存储起来
			edit.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//用户点击退出登陆，会从本地文件清除用户信息
	public static void clearInfo(Context context){
		try{
			//SharedPreferences生成的文件会放置在/data/data/包名/shared_prefs/中,会自动以xml文件形式保存
			SharedPreferences sp = context.getSharedPreferences("config.ini", Context.MODE_PRIVATE);//设置为私有权限
			
			//获得一个编辑器对象
			Editor edit = sp.edit();
			
			//存储数据
			edit.putString("loginName", null);
			edit.putString("token",null);
			edit.putString("id", null);
			edit.putString("orgId", null);
			edit.putString("orgName", null);
			edit.putString("realName", null);
			
			//提交，数据真正的 存储起来
			edit.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//提取用户信息
	public static User getUserInfo(Context context) {
		//同样要用到SharedPerferences对象，
		SharedPreferences sp = context.getSharedPreferences("config.ini", Context.MODE_PRIVATE);
		
		//获取里面的信息
		String loginName = sp.getString("loginName", null);
		if(TextUtils.isEmpty(loginName)){
			return null;
		}else{
			User userInfo = new User();
			userInfo.setLoginName(loginName);
			userInfo.setId(sp.getString("id", null));
			userInfo.setOrgId(sp.getString("orgId", null));
			userInfo.setOrgName(sp.getString("orgName", null));
			userInfo.setRealName(sp.getString("realName", null));
			userInfo.setToken(sp.getString("token", null));
			return userInfo;
		}
		
		
	}
	
	//检测手机是否网络连通
		public static boolean detect(Activity act) {  
			       ConnectivityManager manager = (ConnectivityManager) act  
			              .getApplicationContext().getSystemService(  
			                     Context.CONNECTIVITY_SERVICE);  
			       if (manager == null) {  
			           return false;  
			       }  
			       NetworkInfo networkinfo = manager.getActiveNetworkInfo();  
			       if (networkinfo == null || !networkinfo.isAvailable()) {  
			           return false;  
			       }  
			       return true;  
			    }  
	
}
