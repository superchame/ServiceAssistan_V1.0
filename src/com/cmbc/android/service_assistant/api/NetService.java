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
 * @author 民生电商android部 张超
 * @see 该类是直接使用第三方连接网络的类,
 * 并作为项目中的网络服务
 */
public class NetService {
	//常量
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
	
	//全局变量
	private static AsyncHttpClient client = null;
	
	//消息头信息的来源
	private static String  processTime = OtherService.getCurrentSystemTime(), 
	imei = android.os.Build.MODEL,
	origDomain ,
	version;
	
	public static  void initialParameter(String orig, String ver){
		origDomain = orig;
		version = ver;
	}
	
	/**
	 * 添加消息头信息
	 * @param rp
	 */
	public static void addHeader(RequestParams rp){
		rp.put("origDomain", origDomain);//发起方域代码
		rp.put("homeDomain", HOMEDOMAIN);//落地方域代码
		rp.put("version", version);//APP版本号
		rp.put("processTime", processTime);//发起请求的时间
		rp.put("imei", imei);//终端唯一标识
	}
	
	//登陆
	public static void loginService(final Handler handler , String name , String pwd){
		//使用https访问必须要用这句代码
				SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());  
				//使用开源的框架-----------------------------------
				client = new AsyncHttpClient();
						
				RequestParams rp = new RequestParams();
					
				//消息实体
				rp.put("account", name);
				rp.put("password", pwd);
				
				addHeader(rp);
				
				client.post(LOGINPATH_HTTPS,
								rp, 
								new AsyncHttpResponseHandler() {
										
								@Override//访问网络意义上的成功
								public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
									System.out.println("成功：statusCode:"+statusCode + "\n body:"+new String(responseBody) );
									Message message = new Message();
									message.what = SUCCESS;
									message.obj = new String(responseBody);
									handler.sendMessage(message);
								}
										
								@Override//访问网络上的失败
								public void onFailure(int statusCode, Header[] headers,
										byte[] responseBody, Throwable error) {
										Message message = new Message();
										message.what = ERROR;
										handler.sendMessage(message);
								}
						});	
	}
	
	//登陆，使用云测服务器，http方式登陆
	public static void loginService2(final Handler handler , String name , String pwd){
				//使用开源的框架-----------------------------------
				client = new AsyncHttpClient();
						
				RequestParams rp = new RequestParams();
	
				addHeader(rp);				
				
				//消息实体
				rp.put("account", name);
				rp.put("password", pwd);
				
				client.post(LOGINPATH_HTTP,
								rp, 
								new AsyncHttpResponseHandler() {
								@Override//访问网络意义上的成功
								public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
									System.out.println("成功：statusCode:"+statusCode + "\n body:"+new String(responseBody) );
									Message message = new Message();
									message.what = SUCCESS;
									message.obj = new String(responseBody);
									handler.sendMessage(message);
								}
										
								@Override//访问网络上的失败
								public void onFailure(int statusCode, Header[] headers,
										byte[] responseBody, Throwable error) {
										Message message = new Message();
										message.what = ERROR;
										handler.sendMessage(message);
								}
						});	
	}
	
	//退出登陆
	public static void logoutService(String account , String token){
				client = new AsyncHttpClient();
				try {
					RequestParams rp = new RequestParams();
					
					addHeader(rp);
									
					//消息实体
					rp.put("account", account);
					rp.put("token", token);
					
					client.get(LOGOUTPATH, 
							rp,
					new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
								System.out.println("成功："+new String(responseBody));
							}
							
							@Override
							public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
								System.out.println("失败"+new String(responseBody));
							}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
				
	}
	
	//出库单查询
	public static void searchDeliveryOrder(final Handler handler, String account, String token, String orderNo){
		client = new AsyncHttpClient();
		
		try {
			RequestParams rp = new RequestParams();
			
			addHeader(rp);
			
			//消息实体
			rp.put("account", account);
			rp.put("token", token);
			rp.put("orderNo",orderNo);
					
			client.get(DO_PATH,
						rp,
			new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						System.out.println("成功："+new String(responseBody));
						Message message = new Message();
						message.what = SUCCESS;
						message.obj = new String(responseBody);
						handler.sendMessage(message);
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
						System.out.println("失败"+new String(responseBody));
						Message message = new Message();
						message.what = ERROR;
						handler.sendMessage(message);
					}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取详细出库单商品信息
	public static void getDeliveryOrderDetail(final Handler handler, String loginName, String token,
			String outNo, String skuCode) {
		client = new AsyncHttpClient();
		
		try {
			RequestParams rp = new RequestParams();
			
			addHeader(rp);
			
			//消息实体
			rp.put("account", loginName);
			rp.put("token", token);
			rp.put("orderNo",outNo);
			rp.put("skuCode", skuCode);
					
			client.get(DOD_PATH,
						rp,
			new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						System.out.println("成功："+new String(responseBody));
						Message message = new Message();
						message.what = SUCCESS;
						message.obj = new String(responseBody);
						handler.sendMessage(message);
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
						System.out.println("失败"+new String(responseBody));
						Message message = new Message();
						message.what = ERROR;
						handler.sendMessage(message);
					}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//订单列表查询
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
			
			//消息实体
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
						System.out.println("成功："+new String(responseBody));
						Message message = new Message();
						message.what = SUCCESS;
						message.obj = new String(responseBody);
						handler.sendMessage(message);
					}
					
					@Override
					public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
						System.out.println("失败"+new String(responseBody));
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
