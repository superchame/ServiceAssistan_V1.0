package com.cmbc.android.service_assistant.api;




import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;



import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;


import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;

import org.apache.http.impl.client.DefaultHttpClient;




/**
 * @author ��������android�� �ų�
 * @see �����Ǹ��������������
 */
public class NetOfEngine {
	
	//�ͻ��˶���
	private static HttpClient httpClient = new DefaultHttpClient();
	//������Ӧ����
	private static HttpResponse httpResponse  = null;
	//������ ����
	private static InputStream inputStream = null;
	
	/**
	 * ��ȡGET���ʷ�ʽ�ķ��ʽ��
	 * @param tailString
	 * @return InputStream
	 */
	public static InputStream getResponseOfGetVisit(String path, String tailString){
		try {
			HttpGet httpGet = new HttpGet(path+tailString);
			httpResponse = httpClient.execute(httpGet);
			//��Ӧ��
			int statusNum = httpResponse.getStatusLine().getStatusCode();
			if(statusNum == 200){
				inputStream = httpResponse.getEntity().getContent();
				return inputStream;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(httpClient != null){
				httpClient.getConnectionManager().shutdown();
			}
		}
		return null;
	}
	
	/**
	 * ����һ��������POST�������ݵļ��ϣ�
	 * ��ȡPOST���ʷ�ʽ�ķ��ʽ��
	 * @param ArrayList<NameValuePair>
	 * @return InputStream
	 */
	public static InputStream getResponseOfPostVisit(String path, List<NameValuePair> parameters){
		try {
			System.out.println("ʹ��post����");
			System.out.println("������͵Ķ�����������"+parameters.size());
			//����POST�������
			HttpPost httpPost = new HttpPost(path);
			
			//��post����Ĳ�����װһ��,UrlEncodedFormEntity��������������������ݱ����һ����ʽ������
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity((ArrayList<NameValuePair>)parameters, "UTF-8");
			
			//��ʵ�����post������
			httpPost.setEntity(entity);
			
			SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());  
			
						//֮��ĺͷ���get�������ƣ��ͻ��˶���ִ��POST,������Ӧ����
							httpResponse = httpClient.execute(httpPost);
							//��ȡ��Ӧ��
							int statusCode = httpResponse.getStatusLine().getStatusCode();
							
							if(statusCode == 200){
								//��ȡ������
								inputStream = httpResponse.getEntity().getContent();
								return inputStream;
							}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(httpClient != null){
				httpClient.getConnectionManager().shutdown();
			}
		}
		return null;
	}
	
	/**
	 * ����һ���ֽ��������ظ���һ���ַ���
	 * @param is
	 * @return �ַ���
	 * @throws IOException 
	 */
	public static String getStringFromInputStream(InputStream is) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024*1024];//����һ�����飬ÿ1024�ֽڵĶ�ȡ
			
			int len = -1;
			
			while((len = is.read(buffer))!= -1){
				baos.write(buffer , 0 , len);
			}
			is.close();
			String html = baos.toString();		
			baos.close();
			return html;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
}
