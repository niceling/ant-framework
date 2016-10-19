package com.antteam.test.framework.demo.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * <b>创建人：</b>张美玲<br>
 * <b>类描述：</b>post方式调用http接口<br>
 * <b>创建时间：</b>2014-9-12 下午12:14:13<br>
 */
public class HttpClientUtil {

	private final static int REQUESTTIMEOUT = 5*60*1000;//http数据请求超时
	private static HttpClientUtil httpClientUtil;

	private HttpClientUtil() {

	}
	/**单例模式创建HttpClientUtil*/
	public static HttpClientUtil getInstance() {
		if (httpClientUtil == null) {
			synchronized (HttpClientUtil.class) {
				if (httpClientUtil == null) {
					httpClientUtil = new HttpClientUtil();
				}
			}
		}
		return httpClientUtil;
	}

	/**
	 * 发送多参数Http请求
	 * @param url 链接地址
	 * @param paramsMap 参数
	 * @return
	 */
	public String postHttpMultiPramas(String url, Map<String, String> paramsMap) {
		String responseMsg = "";
		/**
		 * 使用多线程的主要目的，是为了实现并行的下载。
		 * 在httpclient运行的过程中，每个http协议的方法，使用一个HttpConnection实例。
		 * 由于连接是一种有限的资源，每个连接在某一时刻只能供一个线程和方法使用，
		 * 所以需要确保在需要时正确地分配连接。
		 * HttpClient采用了一种类似jdbc连接池的方法来管理连接，
		 * 这个管理工作由 MultiThreadedHttpConnectionManager完成。
		 */
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		// 1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setContentCharset("UTF-8");
		httpClient.getParams().setSoTimeout(REQUESTTIMEOUT); 
		// 2.构造PostMethod的实例
		PostMethod postMethod = new PostMethod(url);
		// 3.把参数值放入到PostMethod对象中
		if (paramsMap != null) {
			Object[] keys = paramsMap.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				postMethod.addParameter(keys[i].toString(), paramsMap.get(keys[i]));
			}
		}
		
		//模拟cookie
		if(StringUtils.isNotBlank(paramsMap.get("token"))){
		    postMethod.addRequestHeader("Cookie", "token=\""+paramsMap.get("token")+"\"");
		}
		
		InputStream in=null;
		InputStreamReader inr=null;
		BufferedReader br=null;
		try {
			// 4.执行postMethod,调用http接口
			int status = httpClient.executeMethod(postMethod);
			if (HttpStatus.SC_OK == status) {
				// 5.读取内容
//				postMethod.getResponseBodyAsStream();//以流的方式读取数据是最节省资源的
//				responseMsg = postMethod.getResponseBodyAsString().trim();
			    String charset=postMethod.getResponseCharSet();
				in=postMethod.getResponseBodyAsStream();
				inr=new InputStreamReader(in,charset);
				br= new BufferedReader(inr);
				StringBuffer buffer = new StringBuffer();
				while ((responseMsg = br.readLine()) != null) {
					buffer.append(responseMsg);
				}
				responseMsg=buffer.toString();
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(br!=null){
					br.close();
					br=null;
				}
				if(inr!=null){
					inr.close();
					inr=null;
				}
				if(in!=null){
					in.close();
					in=null;
				}
			} catch (Exception e) {
			}
			// 7.释放连接
			postMethod.releaseConnection();
		}
		return responseMsg;
	}

	/**
	 * 发送单参数请求
	 * @param url
	 * @param content
	 * @return
	 */
	public String postHttpSinglePramas(String url, String content) {
		String respString = "";
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpClient client = new HttpClient(connectionManager);
		client.getParams().setSoTimeout(REQUESTTIMEOUT);
		PostMethod method = new PostMethod(url);
		try {
			method.addRequestHeader("Connection", "Keep-Alive");
			method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler(0, false));
			method.setRequestEntity(new ByteArrayRequestEntity(content.getBytes("UTF-8")));
			method.addRequestHeader("Content-Type","application/x-www-form-urlencoded");
			int statusCode = client.executeMethod(method);
			if (HttpStatus.SC_OK == statusCode) {
			    String charset=method.getResponseCharSet();
				respString = new String(method.getResponseBody(),charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return respString;
	}

}
