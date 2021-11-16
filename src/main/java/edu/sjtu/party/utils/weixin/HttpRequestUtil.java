package edu.sjtu.party.utils.weixin;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class HttpRequestUtil {
	
	public static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

		public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {

		       JSONObject jsonObject = null;

		       StringBuffer buffer = new StringBuffer();  

		       try {  

		           // ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��  
		           TrustManager[] tm = { new WeixinX509TrustManager() };  
		           SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
		           sslContext.init(null, tm, new java.security.SecureRandom());  
		           // ������SSLContext�����еõ�SSLSocketFactory����  
		           SSLSocketFactory ssf = sslContext.getSocketFactory();  
		           URL url = new URL(requestUrl);  
		           HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
		           httpUrlConn.setSSLSocketFactory(ssf);  
		           httpUrlConn.setDoOutput(true);  
		           httpUrlConn.setDoInput(true);  
		           httpUrlConn.setUseCaches(false);  
		           // ��������ʽ��GET/POST��  
		           httpUrlConn.setRequestMethod(requestMethod);  
		           if ("GET".equalsIgnoreCase(requestMethod))  

		               httpUrlConn.connect();  

		           // ����������Ҫ�ύʱ  
		           if (null != outputStr) {  
		               OutputStream outputStream = httpUrlConn.getOutputStream();  
		               // ע������ʽ����ֹ��������  
		               outputStream.write(outputStr.getBytes("UTF-8"));  
		               outputStream.close();  
		           }  

		           // �����ص�������ת�����ַ���  
		           InputStream inputStream = httpUrlConn.getInputStream(); 
		           InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
		           BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
		           String str = null;  
		           while ((str = bufferedReader.readLine()) != null) {  
		               buffer.append(str);  
		           }  

		           bufferedReader.close();  
		           inputStreamReader.close();  
		           // �ͷ���Դ  
		           inputStream.close();  
		           inputStream = null;  
		           httpUrlConn.disconnect();  
		           jsonObject = JSONObject.parseObject(buffer.toString());
		                    
		       } catch (ConnectException ce) {  
		          ce.printStackTrace();
		       } catch (Exception e) {  
		           e.printStackTrace(); 
		       }  
		       return jsonObject;  
		   }  
		
		public static String httpRequestStr(String requestUrl, String requestMethod, String outputStr) {  

		       String result = "";

		       StringBuffer buffer = new StringBuffer();  

		       try {  

		           // ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��  
		           TrustManager[] tm = { new WeixinX509TrustManager() };  
		           SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
		           sslContext.init(null, tm, new java.security.SecureRandom());  
		           // ������SSLContext�����еõ�SSLSocketFactory����  
		           SSLSocketFactory ssf = sslContext.getSocketFactory();  
		           URL url = new URL(requestUrl);  
		           HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
		           httpUrlConn.setSSLSocketFactory(ssf);  
		           httpUrlConn.setDoOutput(true);  
		           httpUrlConn.setDoInput(true);  
		           httpUrlConn.setUseCaches(false);  
		           // ��������ʽ��GET/POST��  
		           httpUrlConn.setRequestMethod(requestMethod);  
		           if ("GET".equalsIgnoreCase(requestMethod))  

		               httpUrlConn.connect();  

		           // ����������Ҫ�ύʱ  
		           if (null != outputStr) {  
		               OutputStream outputStream = httpUrlConn.getOutputStream();  
		               // ע������ʽ����ֹ��������  
		               outputStream.write(outputStr.getBytes("UTF-8"));  
		               outputStream.close();  
		           }  

		           // �����ص�������ת�����ַ���  
		           InputStream inputStream = httpUrlConn.getInputStream(); 
		           InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
		           BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
		           String str = null;  
		           while ((str = bufferedReader.readLine()) != null) {  
		               buffer.append(str);  
		           }  

		           bufferedReader.close();  
		           inputStreamReader.close();  
		           // �ͷ���Դ  
		           inputStream.close();  
		           inputStream = null;  
		           httpUrlConn.disconnect();  
		           result = buffer.toString();
		                    
		       } catch (ConnectException ce) {  
		          ce.printStackTrace();
		       } catch (Exception e) {  
		           e.printStackTrace(); 
		       }  
		       return result;  
		   }  
		
		public static Map<String, String> getPayNo(String requestUrl,String xmlParam){
			logger.info("xml��:"+xmlParam);
			 //CloseableHttpClient client = createSSLClientDefault();
			 String prepay_id = "";
			 StringBuffer buffer = new StringBuffer();
			 Map<String, String> result = new HashMap<String, String>();
		     try {
//		    	 HttpPost httpost= new HttpPost(url);
//		    	 httpost.addHeader("Connection", "keep-alive");
//	        	 httpost.addHeader("Accept", "*/*");
//	        	 httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//	        	 httpost.addHeader("Host", "api.mch.weixin.qq.com");
//	        	 httpost.addHeader("X-Requested-With", "XMLHttpRequest");
//	        	 httpost.addHeader("Cache-Control", "max-age=0");
//	        	 httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
//				 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
//				 HttpResponse response = client.execute(httpost);
		    	 TrustManager[] tm = { new WeixinX509TrustManager() };  
		           SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
		           sslContext.init(null, tm, new java.security.SecureRandom());  
		           // ������SSLContext�����еõ�SSLSocketFactory����  
		           SSLSocketFactory ssf = sslContext.getSocketFactory();  
		           URL url = new URL(requestUrl);  
		           HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
		           httpUrlConn.setSSLSocketFactory(ssf);  
		           httpUrlConn.setDoOutput(true);  
		           httpUrlConn.setDoInput(true);  
		           httpUrlConn.setUseCaches(false);  
		           // ��������ʽ��GET/POST��  
		           httpUrlConn.setRequestMethod("POST");  
		           // ����������Ҫ�ύʱ  
		           if (null != xmlParam) {  
		               OutputStream outputStream = httpUrlConn.getOutputStream();  
		               // ע������ʽ����ֹ��������  
		               outputStream.write(xmlParam.getBytes("UTF-8"));  
		               outputStream.close();  
		           }  

		           InputStream inputStream = httpUrlConn.getInputStream(); 
		           InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
		           BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
		           String str = null;  
		           while ((str = bufferedReader.readLine()) != null) {  
		               buffer.append(str);  
		           }  

		           bufferedReader.close();  
		           inputStreamReader.close();
		           inputStream.close();  
		           inputStream = null;  
		           httpUrlConn.disconnect();  
//			     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			     Map map = XmlUtil.payParseXml(buffer.toString());
			     if(buffer.toString().indexOf("FAIL")!=-1){
			    	 result.put("return_msg", map.get("return_msg").toString());
			    	 result.put("prepay_id", prepay_id);
			    	 result.put("return_code", map.get("return_code").toString());
			    	 return result;
			     }else {
			    	 result.put("return_msg", map.get("return_msg").toString());
			    	 result.put("prepay_id", map.get("prepay_id").toString());
			    	 result.put("return_code", map.get("return_code").toString());
			    	 return result;
			     }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		  }
		

}
