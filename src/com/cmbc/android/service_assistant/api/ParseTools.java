package com.cmbc.android.service_assistant.api;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.cmbc.android.service_assistant.entity.GoodsDetail;
import com.cmbc.android.service_assistant.entity.LogisticsInfo;
import com.cmbc.android.service_assistant.entity.Order;
import com.cmbc.android.service_assistant.entity.PurchaseOrder;
import com.cmbc.android.service_assistant.entity.Supplier;



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
	public static Map<String, Object> getLoginResponse(String jsonString){
		Map<String, Object> map = new HashMap<String, Object>();
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
			String responseString = headerObject.getString("rspCode");
			String responseWords = headerObject.getString("rspDesc");
			map.put("responseWords", responseWords);
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
		List<GoodsDetail> goodsList = new ArrayList<GoodsDetail>();
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
					GoodsDetail deliveryOrderDetail = new GoodsDetail();
					deliveryOrderDetail.setUnit(jsonObject2.getString("unit"));
					deliveryOrderDetail.setSkuName(jsonObject2.getString("skuName"));
					deliveryOrderDetail.setSpuCode(jsonObject2.getString("spuCode"));
					deliveryOrderDetail.setSkuCode(jsonObject2.getString("skuCode"));
					deliveryOrderDetail.setOutQty(jsonObject2.getString("outQty"));		
					
					goodsList.add(deliveryOrderDetail);
				}
			
				map.put("goodsDetailList", goodsList);
			}
			String responseString = headerObject.getString("rspCode");
			String responseWords = headerObject.getString("rspDesc");
			map.put("responseWords", responseWords);
			map.put("responseString", responseString);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String,Object> getDeliveryOrderResponse(String jsonString){
		Map<String, Object> map = new HashMap<String, Object>();
		List<GoodsDetail> goodsList = new ArrayList<GoodsDetail>();
		boolean error = false;
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
			
			if(objectObject != null && objectObject.getBoolean("error")){
				outNo = objectObject.getString("outNo"); 
				shopName = objectObject.getString("shopName");
				goodsSum = objectObject.getString("goodsSum");
				totalGoodsSum = objectObject.getString("totalGoodSum");
				orderCnt = objectObject.getString("orderCnt");
				error = objectObject.getBoolean("error");
				
				//��ȡjson�еļ���
				JSONArray jsonArray = objectObject.getJSONArray("goodsList");
				//ʹ��ѭ������,����ӵ�������
				for(int i = 0 ; i < jsonArray.length(); i++){
					//���������е�ÿ��json���󣬲���ӵ�����
					JSONObject jsonObject2 = jsonArray.getJSONObject(i);
					GoodsDetail deliveryOrderDetail = new GoodsDetail();
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
			String responseString = headerObject.getString("rspCode");
			String responseWords = headerObject.getString("rspDesc");
			map.put("responseWords", responseWords);
			map.put("responseString", responseString);
			map.put("error", error);
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
			String responseString = headObject.getString("rspCode");
			String responseWords = headObject.getString("rspDesc");
			map.put("responseWords", responseWords);
			
			int totalLine = 0,totalPages = 0,pageNo = 0;
			if(responseString != null && responseString.equals("0000")){
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
	//��ȡ����������Ӧ
	public static Map<String, Object> getOrderDetailResponse(String jsonString) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Order> goodsList = new ArrayList<Order>();//���ڴ�Ŷ����Ʒ�б�ļ���
		List<LogisticsInfo> logisticsList = new ArrayList<LogisticsInfo>(); //���ڴ��������Ϣ�б�ļ���
		String createTime = null, total_price=null, orderType=null,status=null;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject headerObject = jsonObject.getJSONObject("header");
			String responseString = headerObject.getString("rspCode");
			String responseWords = headerObject.getString("rspDesc");
			map.put("responseWords", responseWords);
			try {
				JSONObject objectObject = jsonObject.getJSONObject("object");
				JSONArray orderDetailList = objectObject.getJSONArray("orderDetailList");//�ṩ��Ʒ�б�
				//�ռ���Ʒ�б�
				for(int i = 0 ; i < orderDetailList.length() ; i++){
					JSONObject jsonObject2 = orderDetailList.getJSONObject(i);
					Order order = new Order();
					order.setSku_name(jsonObject2.getString("sku_name"));
					order.setSale_price(jsonObject2.getString("sale_price"));
					order.setQty(jsonObject2.getString("qty"));
					order.setTotal_price(jsonObject2.getString("totalPrice"));
					goodsList.add(order);
				}
				map.put("goodsList", goodsList);
				JSONArray orderMapList = objectObject.getJSONArray("orderMapList");//�ṩ����������Ϣ
				for(int j = 0 ; j < orderMapList.length() ; j++){//����������Ϣֻ��һ���Ŷԣ�Ϊʲô������Ҫ���ó�����
					JSONObject jsonObject3 = orderMapList.getJSONObject(j);
					createTime = jsonObject3.getString("order_create_time");
					total_price = jsonObject3.getString("total_price");
					orderType = jsonObject3.getString("order_type");
					status = jsonObject3.getString("status");
				}
				map.put("createTime", createTime);
				map.put("total_price", total_price);
				map.put("orderType", orderType);
				map.put("status", status);
				
				JSONArray orderOpertinInfoList = objectObject.getJSONArray("orderOpertinInfoList");
				for(int x = 0 ; x < orderOpertinInfoList.length() ; x++){
					JSONObject jsonObject4 = orderOpertinInfoList.getJSONObject(x);
					LogisticsInfo logisticsInfo = new LogisticsInfo();
					logisticsInfo.setRemark(jsonObject4.getString("remark"));
					logisticsInfo.setCreateTime(jsonObject4.getString("order_no"));
					logisticsList.add(logisticsInfo);
				}
				map.put("logisticsList", logisticsList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("responseString", responseString);
			return map;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	//��ȡ����ͳ��
	public static Map<String, Object> getOrderCountResponse(String jsonString) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject headoObject = jsonObject.getJSONObject("header");
			String responseString = headoObject.getString("rspCode");
			String responseWords = headoObject.getString("rspDesc");
			map.put("responseWords", responseWords);
			
			if(responseString != null && responseString.equals("0000")){
				JSONObject object = jsonObject.getJSONObject("object");
				list.add(object.getString("50"));//��ǩ��
				list.add(object.getString("40"));//�ѳ���
				list.add(object.getString("12"));//δ����
				map.put("list", list);
			}
			map.put("responseString", responseString);
			return map;
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	
	

	//��ȡ�������ͻ����ŵ��б� 
	public static Map<String, Object> getOrderTypeOrStoreResponse(String jsonString) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		JSONArray objectJSON;
		try {
			JSONObject reponseJSON = new JSONObject(jsonString);
			JSONObject headerJSON = reponseJSON.getJSONObject("header");
			String responseString = headerJSON.getString("rspCode");
			String responseWords = headerJSON.getString("rspDesc");
			map.put("responseWords", responseWords);
			try{
				objectJSON = reponseJSON.getJSONArray("object");
				for(int i = 0 ; i< objectJSON.length(); i++){
					JSONObject jsonObject = objectJSON.getJSONObject(i);
					Map<String, String> wordsAndValue = new HashMap<String, String>();
					//�����ŵ�Ͷ������͵��ֶβ�ͬ����Ҫ�жϺ�ֿ�����
					String name = jsonObject.getString("name");
					wordsAndValue.put("name", name);
					try {
						String value = jsonObject.getString("value");
						wordsAndValue.put("value", value);
					} catch (Exception e) {
						//����˵��û��value�ֶΣ�����code�ֶ�
						String code = jsonObject.getString("code");
						wordsAndValue.put("code", code);
					}
					list.add(wordsAndValue);
							
				}
			}catch(Exception e){
				objectJSON = null;
			}
			map.put("responseString", responseString);
			map.put("List",list);
			return map;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//��ȡ��Ӧ����Ϣ
	public static Map<String, Object> getSupplierResponse(String jsonString) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Supplier> supList = new ArrayList<Supplier>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject headObject = jsonObject.getJSONObject("header");
			String responseString = headObject.getString("rspCode");
			String responseWords = headObject.getString("rspDesc");
			map.put("responseWords", responseWords);
			if(responseString != null && responseString.equals("0000")){
				JSONObject object = jsonObject.getJSONObject("object");
				int pageNo = 0 , totalPages = 0 , totalLine = 0;
				pageNo = object.getInt("pageNo");
				totalPages = object.getInt("totalPages");
				totalLine = object.getInt("totalLine");
				map.put("pageNo", pageNo);
				map.put("totalPages", totalPages);
				map.put("totalLine", totalLine);
				
				JSONArray listArray = object.getJSONArray("list");
				for(int i = 0 ; i < listArray.length() ; i++){
					JSONObject jsonObject2 = listArray.getJSONObject(i);
					Supplier supplier = new Supplier();
					supplier.setSupplierName(jsonObject2.getString("supplier_names"));
					supplier.setSupplierCode(jsonObject2.getString("supplier_no"));
					supList.add(supplier);
				}
				map.put("supList", supList);
			}
			map.put("responseString", responseString);
			return map;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//��ȡ�ɹ���ɸѡ����
	public static Map<String, Object> getPurchaseOrderConditionResponse(String jsonString) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Supplier> supplierList = null;
		List<Map<String, String>> orgList = null, statusList = null, typeList = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject headObject = jsonObject.getJSONObject("header");
			String responseString = headObject.getString("rspCode");
			String responseWords = headObject.getString("rspDesc");
			map.put("responseWords", responseWords);
			if(responseString != null && responseString.equals("0000")){
				JSONObject objectObject = jsonObject.getJSONObject("object");
				
				//��Ӧ�̳���
				JSONArray cityArray = objectObject.getJSONArray("cityList");
				supplierList = new ArrayList<Supplier>();
				for(int i = 0 ; i < cityArray.length() ; i++){
					JSONObject jsonObject2 = cityArray.getJSONObject(i);
					Supplier supplier = new Supplier();
					supplier.setCity( jsonObject2.getString("name"));
					supplier.setCityCode(jsonObject2.getString("code"));
					supplierList.add(supplier);
				}
				map.put("supplierList", supplierList);
				
				//�ջ��ֿ�
				JSONArray orgArray = objectObject.getJSONArray("orgList");
				orgList = new ArrayList<Map<String,String>>();
				for(int j = 0 ; j < orgArray.length() ; j++){
					JSONObject jsonObject2 = orgArray.getJSONObject(j);
					Map<String, String> orgMap = new HashMap<String, String>();
					orgMap.put("words",  jsonObject2.getString("name"));
					orgMap.put("value", jsonObject2.getString("code"));
					orgList.add(orgMap);
				}
				map.put("orgList", orgList);
				
				//�ɹ���״̬
				JSONArray statusArray = objectObject.getJSONArray("statusList");
				statusList = new ArrayList<Map<String,String>>();
				for(int x = 0 ; x < statusArray.length() ; x++){
					JSONObject jsonObject2 = statusArray.getJSONObject(x);
					Map<String, String> statusMap = new HashMap<String, String>();
					statusMap.put("words", jsonObject2.getString("code_name"));
					statusMap.put("value", jsonObject2.getString("code_id"));
					statusList.add(statusMap);
				}
				map.put("statusList", statusList);
				
				//�ɹ�������
				JSONArray typeArray = objectObject.getJSONArray("typeList");
				typeList = new ArrayList<Map<String,String>>();
				for(int y = 0 ; y < typeArray.length() ; y++){
					JSONObject jsonObject2 = typeArray.getJSONObject(y);
					Map<String, String> typeMap = new HashMap<String, String>();
					typeMap.put("words", jsonObject2.getString("code_name"));
					typeMap.put("value", jsonObject2.getString("code_id"));
					typeList.add(typeMap);
				}
				map.put("typeList", typeList);
				
			}
			map.put("responseString", responseString);
			return map;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	//��ȡ�ɹ����б�
	public static Map<String, Object> getPurchaseOrderListResponse(String jsonString) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PurchaseOrder> list = new ArrayList<PurchaseOrder>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject headObject = jsonObject.getJSONObject("header");
			String responseString = headObject.getString("rspCode");
			String responseWords = headObject.getString("rspDesc");
			map.put("responseWords", responseWords);
			int totalLine = 0,totalPages = 0,pageNo = 0;
			if(responseString != null && responseString.equals("0000")){
				JSONObject objectObject = jsonObject.getJSONObject("object");
				totalLine = objectObject.getInt("totalLine");
				totalPages = objectObject.getInt("totalPages");
				pageNo = objectObject.getInt("pageNo");
				if(objectObject.getJSONArray("list") != null){
					JSONArray listArray = objectObject.getJSONArray("list");
					for(int i = 0 ; i < listArray.length() ; i++ ){
						JSONObject jsonObject2 = listArray.getJSONObject(i);
						PurchaseOrder purchaseOrder = new PurchaseOrder();
						purchaseOrder.setBatch_no(jsonObject2.getString("batch_no"));
						purchaseOrder.setCreate_date(jsonObject2.getString("create_date"));
						purchaseOrder.setCreate_user(jsonObject2.getString("create_user"));
						purchaseOrder.setCreate_user_name(jsonObject2.getString("create_user_name"));
						purchaseOrder.setDirector_user(jsonObject2.getString("director_user"));
						purchaseOrder.setDirector_user_name(jsonObject2.getString("director_user_name"));
						purchaseOrder.setOrg_code(jsonObject2.getString("org_code"));
						purchaseOrder.setOrganization_name(jsonObject2.getString("organization_name"));
						purchaseOrder.setPo_no(jsonObject2.getString("po_no"));
						purchaseOrder.setStatus(jsonObject2.getString("status"));
						purchaseOrder.setSupplier_code(jsonObject2.getString("supplier_code"));
						purchaseOrder.setSupplier_names(jsonObject2.getString("supplier_names"));
						purchaseOrder.setType(jsonObject2.getString("type"));
						list.add(purchaseOrder);
					}
					map.put("list", list);
				}
				map.put("totalLine", totalLine);
				map.put("totalPages", totalPages);
				map.put("pageNo", pageNo);	
			}
			map.put("responseString", responseString);
			return map;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> getPurchaseOrderDetailResponse(String jsonString) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<GoodsDetail> list = new ArrayList<GoodsDetail>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject headObject = jsonObject.getJSONObject("header");
			String responseString = headObject.getString("rspCode");
			String responseWords = headObject.getString("rspDesc");
			map.put("responseWords", responseWords);
			if(responseString != null && responseString.equals("0000")){
				JSONArray object = jsonObject.getJSONArray("object");
				for(int i = 0 ; i < object.length() ; i++){
					JSONObject jsonObject2 = object.getJSONObject(i);
					GoodsDetail goodsDetail = new GoodsDetail();
					goodsDetail.setOutQty(String.valueOf(jsonObject2.getInt("purchase_qty")));
					goodsDetail.setSkuCode(jsonObject2.getString("sku_code"));
					goodsDetail.setSkuName(jsonObject2.getString("sku_name"));
					goodsDetail.setSpuCode(jsonObject2.getString("spu_no"));
					goodsDetail.setUnit(jsonObject2.getString("unit"));
					list.add(goodsDetail);
				}
				map.put("list", list);
			
			}
			map.put("responseString", responseString);
			return map;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> getPurchaseOrderCountResponse(String jsonString) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject headObject = jsonObject.getJSONObject("header");
			String responseString = headObject.getString("rspCode");
			String responseWords = headObject.getString("rspDesc");
			map.put("responseWords", responseWords);
			if(responseString != null && responseString.equals("0000")){
				JSONObject object = jsonObject.getJSONObject("object");
				list.add(object.getString("30"));//ȫ���ջ�
				list.add(object.getString("31"));//�����ջ�
				list.add(object.getString("20"));//�����
				list.add(object.getString("10"));//δ���
				map.put("list", list);
			}
			map.put("responseString", responseString);
			return map;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	//�ɹ���ͳ��ʱ��ɸѡ��ֻ��Ҫ�ջ���֯������
	public static Map<String, Object> getPurchaseOrderConditionResponseForCount(String jsonString){
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> nameList = new ArrayList<String>();
		List<String> codeList = new ArrayList<String>();
		
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONObject headObject = jsonObject.getJSONObject("header");
			String responseString = headObject.getString("rspCode");
			String responseWords = headObject.getString("rspDesc");
			map.put("responseWords", responseWords);
			if(responseString != null && responseString.equals("0000")){
				JSONObject object = jsonObject.getJSONObject("object");
				//�ջ��ֿ�
				JSONArray orgArray = object.getJSONArray("orgList");
				for(int j = 0 ; j < orgArray.length() ; j++){
					JSONObject jsonObject2 = orgArray.getJSONObject(j);
					nameList.add(jsonObject2.getString("name"));
					codeList.add(jsonObject2.getString("code"));
				}
				map.put("List", nameList);
				map.put("codeList", codeList);
			}
			map.put("responseString", responseString);
			return map;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
