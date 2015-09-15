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
 * @author ��������android�� �ų�
 * @see һЩ����������������һЩ�Ի���
 */
public class OtherService{
	private static final String PHONE = "A001";
	private static final String TABLE = "A002";
	private static final String OTHER = "A000";
	
	//���Ļ�����Ӧֵ��Ҫ�ı�ʶ����
	private static final int ORDERSTATUS = 1;
	private static final int ORDERTYPE = 2;
	private static final int PURCHASESTATUS = 6;
	private static final int PURCHASETYPE = 7;
	//��ʶ���õ�ɸѡʱ������͵ı�ʶ����
	private static final int BEGINTIME = 3;
	private static final int ENDTIME = 4;
	
	/*
	 * ��¼�û���¼�Ƿ��ظ���¼����½������жϣ�����еط���Ϊ�ظ���¼��
	 * �����Իᱻ��¼Ϊtrue�ٴ�������½���棬��ʱ��½�������ж����ֵ��
	 * �������ظ���¼��������˾
	 */
	public static  boolean relogin = false;
	
	/*
	 * ��¼ƾ֤�Ƿ���ڣ��ڵ�½ҳ���жϣ����Ϊfalse��û�й��ڣ�
	 * ����������ط��صĹ�����Ϣ���޸�Ϊtrue���ٴ���ת����½����
	 */
	public static  boolean overdue = false;
	
	
	//���ɽ��ȶԻ��򣬱���͸��
	public static Dialog  createTransparentProgressDialog(Context context){
		 Dialog progressDialog = new Dialog(context, R.style.progress_dialog);   
		  progressDialog.setContentView(R.layout.dialog_transparent_progress);   
		  progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);  
		  progressDialog.setCancelable(false);//�����û�����ȡ��
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
	  
	  /**
	   * 
	   * @param ��ʽ��2015-8-22 time
	   * @return ����û�ж̺��ߵ�ʱ���ַ���������20150822
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
	   * @param words ԭ���� 
	   * @param index ����
	   * @return ��������Ϊ0
	   */
	  public static String getWordsValue(String words, int index){
		switch (index) {
		case ORDERSTATUS:
			 if(words.equals("�ѳ���")){
				  return "40";
			  }else if(words.equals("��ǩ��")){
				  return "50";
			  }else if(words.equals("��ȡ��")){
				  return "99";
			  }else if(words.equals("������")){
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
	   * @return ����ֵ��Ӧ�Ķ�������
	   */
	  public static String getValueWords(String value, int index){
		  if(value != null){ 
			  switch (index) {
			case ORDERTYPE:
				 if(value.equals("1")){
					  return "���䶩��";
				  }else if(value.equals("2")) {
					  return "Ԥ�۶���";
				  }else if(value.equals("3")){
					  return "�ֻ�����";
				  }else if(value.equals("4")){
					  return "���񶩵�";
				  }else if(value.equals("5")){
					  return "լ�䶩��";
				  }else{
					  return "δ֪��������";
				  }
			case ORDERSTATUS:
				if(value.equals("50")){
					  return "��ǩ��";
				  }else if(value.equals("99")) {
					  return "��ȡ��";
				  }else if(value.equals("40")){
					  return "�ѳ���";
				  }else{
					  return "������";
				  }
			case PURCHASESTATUS:
				if(value.equals("30")){
					return "ȫ���ջ�";
				}else if (value.equals("31")) {
					return "�����ջ�";
				}else if (value.equals("20")) {
					return "�����";
				}else if (value.equals("10")) {
					return "�½�";
				}else {
					return "δ֪�ɹ���״̬";
				}
			case PURCHASETYPE:
				if(value.equals("1")){
					return "��ͨ�ɹ���";
				}else if(value.equals("2")){
					return "Ԥ�۲ɹ���";
				}else if(value.equals("3")){
					return "�Զ��ɹ���";
				}else{
					return "δ֪�ɹ�������";
				}
			default:
				return "���ʹ���";
			}
		  }
		  return "ֵ����";
	  }
	  
	  /**
	   * ʱ���ת������
	   * @return ��ʱ���ת�����ʱ��
	   */
	  public static String timeStamp(String str){
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  String date = sdf.format(new Date(Long.parseLong(str)));
		  return date;
	  }

	  /**
	   * function:����һ��ʱ��20150303����2015-03-03
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
	
	//��¼�û���Ϣ���ļ���ֻҪ�û�û���˳���½���´ε�����ֱ�ӵ�¼
	public static void saveUserInfo(Context context, User userInfo){
		try{
			//SharedPreferences���ɵ��ļ��������/data/data/����/shared_prefs/��,���Զ���xml�ļ���ʽ����
			SharedPreferences sp = context.getSharedPreferences("config.ini", Context.MODE_PRIVATE);//����Ϊ˽��Ȩ��
			
			//���һ���༭������
			Editor edit = sp.edit();
			
			//�洢����
			if(userInfo != null){
				edit.putString("loginName", userInfo.getLoginName());
				edit.putString("token",userInfo.getToken());
				edit.putString("id", userInfo.getId());
				edit.putString("orgId", userInfo.getOrgId());
				edit.putString("orgName", userInfo.getOrgName());
				edit.putString("realName", userInfo.getRealName());
			}
		
			
			//�ύ������������ �洢����
			edit.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//�û�����˳���½����ӱ����ļ�����û���Ϣ
	public static void clearInfo(Context context){
		try{
			//SharedPreferences���ɵ��ļ��������/data/data/����/shared_prefs/��,���Զ���xml�ļ���ʽ����
			SharedPreferences sp = context.getSharedPreferences("config.ini", Context.MODE_PRIVATE);//����Ϊ˽��Ȩ��
			
			//���һ���༭������
			Editor edit = sp.edit();
			
			//�洢����
			edit.putString("loginName", null);
			edit.putString("token",null);
			edit.putString("id", null);
			edit.putString("orgId", null);
			edit.putString("orgName", null);
			edit.putString("realName", null);
			
			//�ύ������������ �洢����
			edit.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//��ȡ�û���Ϣ
	public static User getUserInfo(Context context) {
		//ͬ��Ҫ�õ�SharedPerferences����
		SharedPreferences sp = context.getSharedPreferences("config.ini", Context.MODE_PRIVATE);
		
		//��ȡ�������Ϣ
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
	
	//����ֻ��Ƿ�������ͨ
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
