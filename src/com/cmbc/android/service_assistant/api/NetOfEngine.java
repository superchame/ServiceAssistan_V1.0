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
 * @author 民生电商android部 张超
 * @see 该类是负责连接网络的类
 */
public class NetOfEngine {
	
	//客户端对象
	private static HttpClient httpClient = new DefaultHttpClient();
	//网络响应对象
	private static HttpResponse httpResponse  = null;
	//输入流 对象
	private static InputStream inputStream = null;
	
	/**
	 * 获取GET访问方式的访问结果
	 * @param tailString
	 * @return InputStream
	 */
	public static InputStream getResponseOfGetVisit(String path, String tailString){
		try {
			HttpGet httpGet = new HttpGet(path+tailString);
			httpResponse = httpClient.execute(httpGet);
			//响应码
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
	 * 传入一个包含了POST请求内容的集合，
	 * 获取POST访问方式的访问结果
	 * @param ArrayList<NameValuePair>
	 * @return InputStream
	 */
	public static InputStream getResponseOfPostVisit(String path, List<NameValuePair> parameters){
		try {
			System.out.println("使用post访问");
			System.out.println("打包发送的东西的条数："+parameters.size());
			//定义POST请求对象
			HttpPost httpPost = new HttpPost(path);
			
			//把post请求的参数包装一层,UrlEncodedFormEntity这个类是用来把输入数据编码成一定格式的内容
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity((ArrayList<NameValuePair>)parameters, "UTF-8");
			
			//把实体放入post对象中
			httpPost.setEntity(entity);
			
			SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());  
			
						//之后的和发送get请求类似，客户端对象执行POST,返回响应对象
							httpResponse = httpClient.execute(httpPost);
							//获取响应码
							int statusCode = httpResponse.getStatusLine().getStatusCode();
							
							if(statusCode == 200){
								//获取流对象
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
	 * 传入一个字节流，返回给你一个字符串
	 * @param is
	 * @return 字符串
	 * @throws IOException 
	 */
	public static String getStringFromInputStream(InputStream is) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024*1024];//声明一个数组，每1024字节的读取
			
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
