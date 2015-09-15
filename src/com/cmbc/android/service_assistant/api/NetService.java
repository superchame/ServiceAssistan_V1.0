package com.cmbc.android.service_assistant.api;









import java.util.Map;
import org.apache.http.Header;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import android.os.Handler;
import android.os.Message;
import com.cmbc.android.service_assistant.enums.Parameters;
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
	private static final String HOMEDOMAIN ="P000";
	
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
				client = null;
				//ʹ�ÿ�Դ�Ŀ��-----------------------------------
				client = new AsyncHttpClient();
				RequestParams rp = new RequestParams();
					
				//��Ϣʵ��
				rp.put("account", name);
				rp.put("password", pwd);
				
				addHeader(rp);
				
				client.post(Parameters.LOGINPATH_HTTPS.toString(),
								rp, 
								new AsyncHttpResponseHandler() {
										
								@Override//�������������ϵĳɹ�
								public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
									System.out.println("�ɹ���statusCode:"+statusCode + "\n body:"+new String(responseBody) );
									Message message = new Message();
									message.what = SUCCESS;
									String jsonString = new String(responseBody);
									message.obj = ParseTools.getLoginResponse(jsonString);
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
				client.post(Parameters.LOGINPATH_HTTP.toString(),
								rp, 
								new AsyncHttpResponseHandler() {
								@Override//�������������ϵĳɹ�
								public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
									System.out.println("�ɹ���statusCode:"+statusCode + "\n body:"+new String(responseBody) );
									Message message = new Message();
									message.what = SUCCESS;
									String jsonString = new String(responseBody);
									message.obj = ParseTools.getLoginResponse(jsonString);
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
					
					client.get(Parameters.LOGOUTPATH.toString(), 
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
							@Override
							public void onUserException(Throwable error) {
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
					
			client.get(Parameters.DO_PATH.toString(),
						rp,
			new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						System.out.println("�ɹ���"+new String(responseBody));
						Message message = new Message();
						message.what = SUCCESS;
						String jsonString = new String(responseBody);
						message.obj = ParseTools.getDeliveryOrderResponse(jsonString);
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
					
					//�����Ļ����ǣ���������ᵽ���������쳣��������ʹ����������Ӱ���û�����
					@Override
					public void onUserException(Throwable error) {
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
					
			client.get(Parameters.DOD_PATH.toString(),
						rp,
			new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						System.out.println("�ɹ���"+new String(responseBody));
						Message message = new Message();
						message.what = SUCCESS;
						String jsonString = new String(responseBody);
						message.obj = ParseTools.getDeliveryOrderDetailResponse(jsonString);
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
					//�����Ļ����ǣ���������ᵽ���������쳣��������ʹ����������Ӱ���û�����
					@Override
					public void onUserException(Throwable error) {
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
		String orgCode = "";
		String createOrderTimeFrom = "";
		String createOrderTimeTo = "";
		String orderType = "";
		String orderState = "";
		String distributeOrg = "";
		String orgabc = "";
		
		if(conditionMap != null){
			if(conditionMap.get("pageNo") != null)
				pageNo = (String) conditionMap.get("pageNo");
			
			orderNo = (String) conditionMap.get("orderNo");
			orgCode = (String) conditionMap.get("orgCode");
			createOrderTimeFrom = (String) conditionMap.get("createOrderTimeFrom");
			createOrderTimeTo = (String) conditionMap.get("createOrderTimeTo");
			orderType = (String) conditionMap.get("orderType");
			orderState = (String) conditionMap.get("orderState");
			distributeOrg = (String) conditionMap.get("distributeOrg");
			orgabc = (String) conditionMap.get("orgabc");
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
			rp.put("orgCode", orgCode);
			rp.put("orderNo", orderNo);
			rp.put("createOrderTimeFrom", createOrderTimeFrom);
			rp.put("createOrderTimeTo", createOrderTimeTo);
			rp.put("orderState", orderState);
			rp.put("orderType", orderType);
			rp.put("distributeOrg", distributeOrg);
			rp.put("orgabc", orgabc);
			
			client.get(Parameters.OL_PATH.toString(),
						rp,
			new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
						System.out.println("�ɹ���"+new String(responseBody));
						Message message = new Message();
						message.what = SUCCESS;
						String jsonString = new String(responseBody);
						message.obj = ParseTools.getOrderListResponse(jsonString);
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
					//�����Ļ����ǣ���������ᵽ���������쳣��������ʹ����������Ӱ���û�����
					@Override
					public void onUserException(Throwable error) {
						Message message = new Message();
						message.what = ERROR;
						handler.sendMessage(message);
					}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��ȡ��������
	public static void getOrderDetail(final Handler handler, String loginName, String token, String orderNo){
		client = new AsyncHttpClient();
		try{
			RequestParams rp = new RequestParams();
			
			addHeader(rp);
			
			rp.put("account", loginName);
			rp.put("token", token);
			rp.put("orderNo", orderNo);
			
			client.get(Parameters.OD_PATH.toString(),
					rp,
					new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
							System.out.println("�ɹ���"+new String(responseBody));
							Message message = new Message();
							message.what = SUCCESS;
							String jsonString = new String(responseBody);
							message.obj = ParseTools.getOrderDetailResponse(jsonString);
							handler.sendMessage(message);
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							System.out.println("�ɹ���"+new String(responseBody));
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
						
						/*
						 * �����Ļ����ǣ���������ᵽ���������쳣��
						 * ������½���������ȴû�����쳣������ִ�е�onFailure
						 * ������ʹ����������Ӱ���û�����(non-Javadoc)
						 * @see com.loopj.android.http.AsyncHttpResponseHandler#onUserException(java.lang.Throwable)
						 */
						@Override
						public void onUserException(Throwable error) {
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
					});
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	//��ȡ����ͳ��
	public  static void getOrderCount(final Handler handler, String loginName ,String token,String distributeOrg, String userId){
		client = new AsyncHttpClient();
		try {
			RequestParams rp = new RequestParams();
			
			addHeader(rp);
			
			rp.put("account", loginName);
			rp.put("token", token);
			rp.put("distributeOrg", distributeOrg);
			rp.put("userId", userId);
			
			
			client.get(Parameters.OC_PATH.toString(), 
					rp,
					new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
							System.out.println(new String(responseBody));
							Message message = new Message();
							message.what = SUCCESS;
							String jsonString = new String(responseBody);
							message.obj = ParseTools.getOrderCountResponse(jsonString);
							handler.sendMessage(message);
							
							
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							System.out.println(new String(responseBody));
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
						/*
						 * �����Ļ����ǣ���������ᵽ���������쳣��
						 * ������½���������ȴû�����쳣������ִ�е�onFailure
						 * ������ʹ����������Ӱ���û�����(non-Javadoc)
						 * @see com.loopj.android.http.AsyncHttpResponseHandler#onUserException(java.lang.Throwable)
						 */
						@Override
						public void onUserException(Throwable error) {
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
						
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��ȡ�ŵ��б�
	public static void getStoreList(final Handler handler, String loginName, String token, String userId){
		client = new AsyncHttpClient();
		try {
			RequestParams rp = new RequestParams();
			addHeader(rp);
			rp.put("account", loginName);
			rp.put("token", token);
			rp.put("userId", userId);
			client.get(Parameters.STORE_PATH.toString(),
					rp,
					new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
							System.out.println(new String(responseBody));
							Message message = new Message();
							message.what = SUCCESS;
							String jsonString = new String(responseBody);
							message.obj = ParseTools.getOrderTypeOrStoreResponse(jsonString);
							handler.sendMessage(message);
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							System.out.println(new String(responseBody));
						}
						/*
						 * �����Ļ����ǣ���������ᵽ���������쳣��
						 * ������½���������ȴû�����쳣������ִ�е�onFailure
						 * ������ʹ����������Ӱ���û�����(non-Javadoc)
						 * @see com.loopj.android.http.AsyncHttpResponseHandler#onUserException(java.lang.Throwable)
						 */
						@Override
						public void onUserException(Throwable error) {
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��ȡ��������
	public static void getOrderType(final Handler handler, String loginName ,String token, String userId){

		client = new AsyncHttpClient();
		try {
			RequestParams rp = new RequestParams();
			
			addHeader(rp);
			
			rp.put("account", loginName);
			rp.put("token", token);
			rp.put("userId", userId);
			
			client.get(Parameters.OT_PATH.toString(), 
					rp,
					new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
							System.out.println(new String(responseBody));
							Message message = new Message();
							message.what = SUCCESS;
							String jsonString = new String(responseBody);
							message.obj = ParseTools.getOrderTypeOrStoreResponse(jsonString);
							handler.sendMessage(message);
							
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							System.out.println(new String(responseBody));
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
							
						}
						/*
						 * �����Ļ����ǣ���������ᵽ���������쳣��
						 * ������½���������ȴû�����쳣������ִ�е�onFailure
						 * ������ʹ����������Ӱ���û�����(non-Javadoc)
						 * @see com.loopj.android.http.AsyncHttpResponseHandler#onUserException(java.lang.Throwable)
						 */
						@Override
						public void onUserException(Throwable error) {
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��Ӧ�̲�ѯ
	public static void getSupplier(final Handler handler, String loginName, String token, String cityCode, Map<String, Object> conditionMap){
		String pageNo = "1";
		String pageCount = "10";
		String supplierName = "";
		
		if(conditionMap != null){
			if(conditionMap.get("pageNo") != null)
				pageNo = (String) conditionMap.get("pageNo");
			supplierName = (String) conditionMap.get("supplierName");
		}
	
		client = new AsyncHttpClient();
		try {
			RequestParams rp = new RequestParams();
			addHeader(rp);
			rp.put("account", loginName);
			rp.put("token", token);
			rp.put("pageNo", pageNo);
			rp.put("pageCount", pageCount);
			rp.put("cityCode",cityCode);
			rp.put("supplierName", supplierName);
			client.post(Parameters.SUPPLIER_PATH.toString(),
					rp,
					new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
							System.out.println(new String(responseBody));
							String jsonString = new String(responseBody);
							Message message = new Message();
							message.what = SUCCESS;
							message.obj = ParseTools.getSupplierResponse(jsonString);
							handler.sendMessage(message);
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							System.out.println(new String(responseBody));
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
							
						}
						/*
						 * �����Ļ����ǣ���������ᵽ���������쳣��
						 * ������½���������ȴû�����쳣������ִ�е�onFailure
						 * ������ʹ����������Ӱ���û�����(non-Javadoc)
						 * @see com.loopj.android.http.AsyncHttpResponseHandler#onUserException(java.lang.Throwable)
						 */
						@Override
						public void onUserException(Throwable error) {
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
					
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//�ɹ���ɸѡ������ѯ
	public static void getPurchaseOrderConditon(final Handler handler, String loginName , String token, String userId){
	
		client = new AsyncHttpClient();
		try {
			RequestParams rp = new RequestParams();
			addHeader(rp);
			rp.put("account", loginName);
			rp.put("token", token);
			rp.put("userId", userId);
			client.get(Parameters.POQ_PATH.toString(), 
					rp,
					new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
							System.out.println(new String(responseBody));
							Message message = new Message();
							message.what = SUCCESS;
							String jsonString = new String(responseBody);
							message.obj = ParseTools.getPurchaseOrderConditionResponse(jsonString);
							handler.sendMessage(message);
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							System.out.println(new String(responseBody));
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
						/*
						 * �����Ļ����ǣ���������ᵽ���������쳣��
						 * ������½���������ȴû�����쳣������ִ�е�onFailure
						 * ������ʹ����������Ӱ���û�����(non-Javadoc)
						 * @see com.loopj.android.http.AsyncHttpResponseHandler#onUserException(java.lang.Throwable)
						 */
						@Override
						public void onUserException(Throwable error) {
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//�ɹ���ͳ����Ҫ����һ����ѯ����
		public static void getPurchaseOrderConditonForCount(final Handler handler, String loginName , String token, String userId){
	
			client = new AsyncHttpClient();
			try {
				RequestParams rp = new RequestParams();
				addHeader(rp);
				rp.put("account", loginName);
				rp.put("token", token);
				rp.put("userId", userId);
				client.get(Parameters.POQ_PATH.toString(), 
						rp,
						new AsyncHttpResponseHandler() {
							
							@Override
							public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
								System.out.println(new String(responseBody));
								Message message = new Message();
								message.what = SUCCESS;
								String jsonString = new String(responseBody);
								message.obj = ParseTools.getPurchaseOrderConditionResponseForCount(jsonString);
								handler.sendMessage(message);
							}
							
							@Override
							public void onFailure(int statusCode, Header[] headers,
									byte[] responseBody, Throwable error) {
								System.out.println(new String(responseBody));
								Message message = new Message();
								message.what = ERROR;
								handler.sendMessage(message);
							}
							/*
							 * �����Ļ����ǣ���������ᵽ���������쳣��
							 * ������½���������ȴû�����쳣������ִ�е�onFailure
							 * ������ʹ����������Ӱ���û�����(non-Javadoc)
							 * @see com.loopj.android.http.AsyncHttpResponseHandler#onUserException(java.lang.Throwable)
							 */
							@Override
							public void onUserException(Throwable error) {
								Message message = new Message();
								message.what = ERROR;
								handler.sendMessage(message);
							}
						});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	
	
	//��ȡ�ɹ����б�
	public static void getPurchaseOrderList(final Handler handler, String loginName, String token, String userId,Map<String , Object> conditionMap){
		String pageNo = "1";
		String pageCount = "10";
		String orgCode = "";
		String supplierCityId = "";
		String supplierCode = "";
		String type = "";
		String status = "";
		String poNo = "";
		
		if(conditionMap != null){
			if(conditionMap.get("pageNo") != null)
				pageNo = (String) conditionMap.get("pageNo");
			orgCode = (String) conditionMap.get("orgCode");
			supplierCityId = (String) conditionMap.get("supplierCityId");
			supplierCode = (String) conditionMap.get("supplier_code");
			type = (String) conditionMap.get("type");
			status = (String) conditionMap.get("status");
			poNo = (String) conditionMap.get("poNo");
		}
	
		client = new AsyncHttpClient();
		try {
			RequestParams rp = new RequestParams();
			addHeader(rp);
			rp.put("account", loginName);
			rp.put("token", token);
			rp.put("userId", userId);
			rp.put("pageNo", pageNo);
			rp.put("pageCount", pageCount);
			rp.put("orgCode", orgCode);
			rp.put("supplierCityId", supplierCityId);
			rp.put("supplierCode", supplierCode);
			rp.put("type", type);
			rp.put("status", status);
			rp.put("poNo", poNo);
			
			client.get(Parameters.PL_PATH.toString(), 
					rp,
					new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
							System.out.println(new String(responseBody));
							Message message = new Message();
							message.what = SUCCESS;
							String jsonString = new String(responseBody);
							message.obj = ParseTools.getPurchaseOrderListResponse(jsonString);
							handler.sendMessage(message);
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							System.out.println(new String(responseBody));	
							Message message = new Message();
								message.what = ERROR;
								handler.sendMessage(message);
						}
						/*
						 * �����Ļ����ǣ���������ᵽ���������쳣��
						 * ������½���������ȴû�����쳣������ִ�е�onFailure
						 * ������ʹ����������Ӱ���û�����(non-Javadoc)
						 * @see com.loopj.android.http.AsyncHttpResponseHandler#onUserException(java.lang.Throwable)
						 */
						@Override
						public void onUserException(Throwable error) {
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��òɹ�������
	public static void getPurchaseOrderDetail(final Handler handler, String loginName, String token, String poNo){

		client = new AsyncHttpClient();
		try {
			RequestParams rp = new RequestParams();
			addHeader(rp);
			rp.put("account", loginName);
			rp.put("token", token);
			rp.put("poNo", poNo);
			client.get(Parameters.POD_PATH.toString(), 
					rp,
					new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
							System.out.println(new String(responseBody));
							Message message = new Message();
							message.what = SUCCESS;
							String jsonString = new String(responseBody);
							message.obj = ParseTools.getPurchaseOrderDetailResponse(jsonString);
							handler.sendMessage(message);
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
						/*
						 * �����Ļ����ǣ���������ᵽ���������쳣��
						 * ������½���������ȴû�����쳣������ִ�е�onFailure
						 * ������ʹ����������Ӱ���û�����(non-Javadoc)
						 * @see com.loopj.android.http.AsyncHttpResponseHandler#onUserException(java.lang.Throwable)
						 */
						@Override
						public void onUserException(Throwable error) {
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��ȡ�ɹ���ͳ��
	public static void getPurchaseOrderCount(final Handler handler, String loginName, String token, String orgCode, String userId){
	
		client = new AsyncHttpClient();
		try {
			RequestParams rp = new RequestParams();
			addHeader(rp);
			rp.put("account", loginName);
			rp.put("token", token);
			rp.put("orgCode", orgCode);
			rp.put("userId", userId);
			client.get(Parameters.POC_PATH.toString(), 
					rp,
					new AsyncHttpResponseHandler() {
						
						@Override
						public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
							System.out.println(new String(responseBody));
							Message message = new Message();
							message.what = SUCCESS;
							String jsonString = new String(responseBody);
							message.obj = ParseTools.getPurchaseOrderCountResponse(jsonString);
							handler.sendMessage(message);
						}
						
						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							System.out.println(new String(responseBody));
							Message message = new Message();
							message.what = ERROR;
							handler.sendMessage(message);
						}
						/*
						 * �����Ļ����ǣ���������ᵽ���������쳣��
						 * ������½���������ȴû�����쳣������ִ�е�onFailure
						 * ������ʹ����������Ӱ���û�����(non-Javadoc)
						 * @see com.loopj.android.http.AsyncHttpResponseHandler#onUserException(java.lang.Throwable)
						 */
						@Override
						public void onUserException(Throwable error) {
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
