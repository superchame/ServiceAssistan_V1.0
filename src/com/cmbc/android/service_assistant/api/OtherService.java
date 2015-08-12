package com.cmbc.android.service_assistant.api;




import java.util.Calendar;
import com.cmbc.android.service_assistant.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;



/**
 * @author 民生电商android部 张超
 * @see 一些其他服务，例如生成一些对话框
 */
public class OtherService{
	private static final String PHONE = "A001";
	private static final String TABLE = "A002";
	private static final String OTHER = "A000";
	
	
	//生成进度对话框，背景透明
	public static Dialog  createTransparentProgressDialog(Context context){
		 Dialog progressDialog = new Dialog(context, R.style.progress_dialog);   
		  progressDialog.setContentView(R.layout.dialog_transparent_progress);   
		  progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
		  progressDialog.setCancelable(false);
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
}
