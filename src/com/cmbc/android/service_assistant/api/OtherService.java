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
 * @author ��������android�� �ų�
 * @see һЩ����������������һЩ�Ի���
 */
public class OtherService{
	private static final String PHONE = "A001";
	private static final String TABLE = "A002";
	private static final String OTHER = "A000";
	
	
	//���ɽ��ȶԻ��򣬱���͸��
	public static Dialog  createTransparentProgressDialog(Context context){
		 Dialog progressDialog = new Dialog(context, R.style.progress_dialog);   
		  progressDialog.setContentView(R.layout.dialog_transparent_progress);   
		  progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
		  progressDialog.setCancelable(false);
		  progressDialog.show();
		  return progressDialog;
	}
	
	/**
	 * ��ȡ��ǰϵͳʱ����ַ���
	 * @return String
	 */
	public static String getCurrentSystemTime(){
		
		//��ȡʱ�����������,ע���ȡ�����ں��û����õ����ڵ��·ݶ�Ҫ��1
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
	 * �ж��豸����
	 * @param context
	 * @return �豸�����ַ���
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
	   * ��ȡ�汾��
	   * @return ��ǰӦ�õİ汾��
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
	   * �����Զ���view�Ի���
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
