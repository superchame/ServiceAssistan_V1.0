package com.cmbc.android.service_assistant.api;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.cmbc.android.service_assistant.entity.DeliveryOrderDetail;
import com.cmbc.android.service_assistant.entity.Order;



/**
 * @author ��������android�� �ų�
 * @see ����һ�������������࣬���Խ���XML����JSON�ַ���
 * @version 2015.7.21Ŀǰ������Ŀ��Ҫ����JSON�ַ���
 * 
 * 
 */
public class ParseTools {
	
	
	/**
	 *	function�� ������½��Ӧ��json����
	 * @param jsonString
	 * @return Map����
	 */
	public static Map<String, String> getLoginResponse(String jsonString){
		Map<String, String> map = new HashMap<String, String>();
		String realName, organization, token, loginName, orgId, id, orgList;
		JSONObject objectObject;
		
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject headerObject = jsonObject.getJSONObject("header");
			//��ȡ�����������token��ʶ�ַ�
			token = headerObject.getString("token");
			
			//��ȡʱ���쳣�����objectΪ�վͻ���ֿ�ָ���쳣
			try{
				objectObject = jsonObject.getJSONObject("object");
			}catch(Exception e){
				objectObject = null;
			}
			
			if(objectObject != null){
				realName = objectObject.getString("realName"); 
				organization = objectObject.getString("orgName");
				loginName = objectObject.getString("loginName");
				orgId = objectObject.getString("orgId");
				id = objectObject.getString("id");
				orgList = objectObject.getString("orgList");
				
				map.put("realName", realName);
				map.put("orgName", organization);
				map.put("loginName", loginName);
				map.put("orgId", orgId);
				map.put("id", id);
				map.put("orgList", orgList);
				
			}
			String responseString = headerObject.getString("rspDesc");
			map.put("responseString", responseString);
			map.put("token", token);
			
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String, Object> getDeliveryOrderDetailResponse(String jsonString){

		Map<String, Object> map = new HashMap<String, Object>();
		List<DeliveryOrderDetail> goodsList = new ArrayList<DeliveryOrderDetail>();
		JSONArray jsonArray;
		
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject headerObject = jsonObject.getJSONObject("header");
			//��ȡʱ���쳣�����objectΪ�վͻ���ֿ�ָ���쳣
			try{
				jsonArray  = jsonObject.getJSONArray("object");
			}catch(Exception e){
				jsonArray = null;
			}
			
			if(jsonArray != null){
				//ʹ��ѭ������,����ӵ�������
				for(int i = 0 ; i < jsonArray.length(); i++){
					//���������е�ÿ��json���󣬲���ӵ�����
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
					deliveryOrderDetail.setUnit(jsonObject2.getString("unit"));
					deliveryOrderDetail.setSkuName(jsonObject2.getString("skuName"));
					deliveryOrderDetail.setSpuCode(jsonObject2.getString("spuCode"));
					deliveryOrderDetail.setSkuCode(jsonObject2.getString("skuCode"));
					deliveryOrderDetail.setOutQty(jsonObject2.getString("outQty"));		
					
					goodsList.add(deliveryOrderDetail);
				}
			
				map.put("goodsDetailList", goodsList);
			}
			String responseString = headerObject.getString("rspDesc");
			map.put("responseString", responseString);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String,Object> getDeliveryOrderResponse(String jsonString){
		Map<String, Object> map = new HashMap<String, Object>();
		List<DeliveryOrderDetail> goodsList = new ArrayList<DeliveryOrderDetail>();
		String outNo, shopName, goodsSum, totalGoodsSum, orderCnt;
		JSONObject objectObject;
		
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject headerObject = jsonObject.getJSONObject("header");
			//��ȡʱ���쳣�����objectΪ�վͻ���ֿ�ָ���쳣
			try{
				objectObject = jsonObject.getJSONObject("object");
			}catch(Exception e){
				objectObject = null;
			}
			
			if(objectObject != null){
				outNo = objectObject.getString("outNo"); 
				shopName = objectObject.getString("shopName");
				goodsSum = objectObject.getString("goodsSum");
				totalGoodsSum = objectObject.getString("totalGoodsSum");
				orderCnt = objectObject.getString("orderCnt");
				
				//��ȡjson�еļ���
				JSONArray jsonArray = objectObject.getJSONArray("goodsList");
				//ʹ��ѭ������,����ӵ�������
				for(int i = 0 ; i < jsonArray.length(); i++){
					//���������е�ÿ��json���󣬲���ӵ�����
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					DeliveryOrderDetail deliveryOrderDetail = new DeliveryOrderDetail();
					deliveryOrderDetail.setSkuName(jsonObject2.getString("skuName"));
					deliveryOrderDetail.setSkuCode(jsonObject2.getString("skuCode"));
					deliveryOrderDetail.setOutQty(jsonObject2.getString("outQty"));
					goodsList.add(deliveryOrderDetail);
				}
			
				map.put("goodsList", goodsList);
				
				map.put("outNo", outNo);
				map.put("shopName", shopName);
				map.put("goodsSum", goodsSum);
				map.put("totalGoodsSum", totalGoodsSum);
				map.put("orderCnt", orderCnt);
						
			}
			String responseString = headerObject.getString("rspDesc");
			map.put("responseString", responseString);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//��ö����б��ѯ��Ӧ
	public static Map<String, Object> getOrderListResponse(String jsonString){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Order> orderList = new ArrayList<Order>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject headObject = jsonObject.getJSONObject("header");
			String responseString = headObject.getString("rspDesc");
			int totalLine = 0,totalPages = 0,pageNo = 0;
			if(responseString != null && responseString.equals("�����ɹ���")){
				JSONObject objectObject = jsonObject.getJSONObject("object");
				totalLine = objectObject.getInt("totalLine");
				totalPages = objectObject.getInt("totalPages");
				pageNo = objectObject.getInt("pageNo");
				JSONArray listArray = objectObject.getJSONArray("list");
				for(int i = 0 ; i < listArray.length() ; i++){
					Order order = new Order();
					JSONObject jsonObject2 = listArray.getJSONObject(i);
					order.setOrder_create_time(jsonObject2.getLong("order_create_time"));
					order.setSendName(jsonObject2.getString("sendName"));
					order.setStatus(jsonObject2.getString("status"));
					order.setRecvName(jsonObject2.getString("recvName"));
					order.setRtn_no(jsonObject2.getString("rtn_no"));
					order.setOrg_abc(jsonObject2.getString("org_abc"));
					order.setStatusValue(jsonObject2.getString("statusValue"));
					order.setOrder_no(jsonObject2.getString("order_no"));
					order.setOrder_type(jsonObject2.getString("order_type"));
					
					orderList.add(order);
				}
			}
			map.put("pageNo", pageNo);
			map.put("totalLine", totalLine);
			map.put("totalPages", totalPages);
			map.put("responseString", responseString);
			map.put("orderList", orderList);
			return map;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
