package com.cmbc.android.service_assistant.api;









import java.util.Map;

import org.apache.http.Header;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import android.os.Handler;
import android.os.Message;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author ��������android�� �ų�
 * @see ������ֱ��ʹ�õ����������������,
 * ����Ϊ��Ŀ�е��������
 */
public class NetService {
	//����
	private static final int SUCCESS = 1;
	private static final int ERROR = 0;
	private static final String HTTP = "http://123.59.44.208:8080";
	private static final String HTTPS = "https://123.59.44.208:8080";
	private static final String HOMEDOMAIN ="P000";
	private static final String LOGINPATH_HTTPS = HTTPS+"/auth/login";
	private static final String LOGINPATH_HTTP = HTTP+"/auth/login";
	private static final String LOGOUTPATH = HTTP+"/v/logout";
	private static final String DO_PATH = HTTP+"/v/orderOut/orderOutQuery/getOrderOutInfo";
	private static final String DOD_PATH = HTTP+"/v/orderOut/orderOutQuery/getOrderOutInfoDetail";
	private static final String DOR_PATH = "http://123.59.44.208:8080/v/receiving/recv/getReceivingOrder";
	private static final String BL_PATH = "http://123.59.44.208:8080/v/baseData/getOrgList";
	private static final String OL_PATH = "http://123.59.44.208:8080/v/order/orderQuery/getOrderListSampleParam";
	private static final String OC_PATH = "http://123.59.44.208:8080/v/order/orderQuery/getOrderStatisticsForEach";
	private static final String OD_PATH = "http://123.59.44.208:8080/v/order/orderQuery/getOrderDetailsList";
	private static final String OT_PATH = "http://123.59.44.208:8080";
	private static final String STORE_PATH = "http://123.59.44.208:8080";
	private static final String SUPPLIER_PATH = "http://123.59.44.208:8080";
	private static final String POQ_PATH = "http://123.59.44.208:8080";
	private static final String PL_PATH = "http://123.59.44.208:8080";
	private static final String POD_PATH = "http://123.59.44.208:8080";
	private static final String POC_PATH = "http://123.59.44.208:8080";
	
	//ȫ�ֱ���
	private static AsyncHttpClient client = null;
	
	//��Ϣͷ��Ϣ����Դ
	private static String  processTime = OtherService.getCurrentSystemTime(), 
	imei = android.os.Build.MODEL,
	origDomain ,
	version;
	
	public static  void initialParameter(String orig, String ver){
		origDomain = orig;
		version = ver;
	}
	
	/**
	 * �����Ϣͷ��Ϣ
	 * @param rp
	 */
	public static void addHeader(RequestParams rp){
		rp.put("origDomain", origDomain);//���������
		rp.put("homeDomain", HOMEDOMAIN);//��ط������
		rp.put("version", version);//APP�汾��
		rp.put("processTime", processTime);//���������ʱ��
		rp.put("imei", imei);//�ն�Ψһ��ʶ
	}
	
	//��½
	public static void loginService(final Handler handler , String name , String pwd){
		//ʹ��https���ʱ���Ҫ��������
				SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());  
				//ʹ�ÿ�Դ�Ŀ��-----------------------------------
				client = new AsyncHttpClient();
						
				RequestParams rp = new RequestParams();
					
				//��Ϣʵ��
				rp.put("account", name);
				rp.put("password", pwd);
				
				addHeader(rp);
				
				client.post(LOGINPATH_HTTPS,
								rp, 
								new AsyncHttpResponseHandler() {
										
								@Override//�������������ϵĳɹ�
								public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
									System.out.println("�ɹ���statusCode:"+statusCode + "\n body:"+new String(responseBody) );
									Message message = new Message();
									message.what = SUCCESS;
									message.obj = new String(responseBody);
									handler.sendMessage(message);
								}
										
								@Override//���������ϵ�ʧ��
								public void onFailure(int statusCode, Header[] headers,
										byte[] responseBody, Throwable error) {
										Message message = new Message();
										message.what = ERROR;
										handler.sendMessage(message);
								}
						});	
	}
	
	//��½��ʹ���Ʋ��������http��ʽ��½
	public static void loginService2(final Handler handler , String name , String pwd){
				//ʹ�ÿ�Դ�Ŀ��-----------------------------------
				client = new AsyncHttpClient();
						
				RequestParams rp = new RequestParams();
	
				addHeader(rp);				
				
				//��Ϣʵ��
				rp.put("account", name);
				rp.put("password", pwd);
				
				client.post(LOGINPATH_HTTP,
								rp, 
								new AsyncHttpResponseHandler() {
								@Override//�������������ϵĳɹ�
								public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
									System.out.println("�ɹ���statusCode:"+statusCode + "\n body:"+new String(responseBody) );
									Message message = new Message();
									message.what = SUCCESS;
									message.obj = new String(responseBody);
									handler.sendMessage(message);
								}
										
								@Override//���������ϵ�ʧ��
								public void onFailure(int statusCode, Header[] headers,
										byte[] responseBody, Throwable error) {
										Message message = new Message();
										message.what = ERROR;
										handler.sendMessage(message);
								}
						});	
	}
	
	//�˳���½
	public static void logoutService(String account , String token){
				client = new AsyncHttpClient();
				try {
					RequestParams rp = new RequestParams();
					
					addHeader(rp);
									
					//��Ϣʵ��
					rp.put("account", account);
					rp.put("token", token);
					
					client.get(LOGOUTPATH, 
							rp,
					new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
								System.out.println("�ɹ���"+new String(responseBody));
							}
							
							@Override
							public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
								System.out.println("ʧ��"+new String(responseBody));
							}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
				
	}
	
	//���ⵥ��ѯ
	public static void searchDeliveryOrder(final Handler handler, String account, String token, String orderNo){
		client = new AsyncHttpClient();
		
		try {
			RequestParams rp = new RequestParams();
			
			addHeader(rp);
			
			//��Ϣʵ��
			rp.put("account", account);
			rp.put("token", token);
			rp.put("orderNo",orderNo);
					
			client.get(DO_PATH,
						rp,
			new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						System.out.println("�ɹ���"+new String(responseBody));
						Message message = new Message();
						message.what = SUCCESS;
						message.obj = new String(responseBody);
						handler.sendMessage(message);
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
						System.out.println("ʧ��"+new String(responseBody));
						Message message = new Message();
						message.what = ERROR;
						handler.sendMessage(message);
					}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��ȡ��ϸ���ⵥ��Ʒ��Ϣ
	public static void getDeliveryOrderDetail(final Handler handler, String loginName, String token,
			String outNo, String skuCode) {
		client = new AsyncHttpClient();
		
		try {
			RequestParams rp = new RequestParams();
			
			addHeader(rp);
			
			//��Ϣʵ��
			rp.put("account", loginName);
			rp.put("token", token);
			rp.put("orderNo",outNo);
			rp.put("skuCode", skuCode);
					
			client.get(DOD_PATH,
						rp,
			new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						System.out.println("�ɹ���"+new String(responseBody));
						Message message = new Message();
						message.what = SUCCESS;
						message.obj = new String(responseBody);
						handler.sendMessage(message);
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
						System.out.println("ʧ��"+new String(responseBody));
						Message message = new Message();
						message.what = ERROR;
						handler.sendMessage(message);
					}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//�����б��ѯ
	public static void getOrderList(final Handler handler, String loginName, String token,String userId, Map<String, Object> conditionMap){
		String pageNo = "1";
		String pageCount = "10";
		String orderNo = "";
		if(conditionMap != null){
			if(conditionMap.get("pageNo") != null)
				pageNo = (String) conditionMap.get("pageNo");
			orderNo = (String) conditionMap.get("orderNo");
		}			
		client = new AsyncHttpClient();
		try {
			RequestParams rp = new RequestParams();
			
			addHeader(rp);
			
			//��Ϣʵ��
			rp.put("account", loginName);
			rp.put("token", token);
			rp.put("userId", userId);
			rp.put("pageNo", pageNo);
			rp.put("pageCount", pageCount);
			rp.put("orgCode", "");
			rp.put("orderNo", orderNo);
			rp.put("createOrderTimeFrom", "");
			rp.put("createOrderTimeTo", "");
			rp.put("orderState", "");
			rp.put("orderType", "");
			rp.put("distributeOrg", "");
			rp.put("orgabc", "");
			
			client.get(OL_PATH,
						rp,
			new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						System.out.println("�ɹ���"+new String(responseBody));
						Message message = new Message();
						message.what = SUCCESS;
						message.obj = new String(responseBody);
						handler.sendMessage(message);
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
						System.out.println("ʧ��"+new String(responseBody));
						Message message = new Message();
						message.what = ERROR;
						handler.sendMessage(message);
					}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
