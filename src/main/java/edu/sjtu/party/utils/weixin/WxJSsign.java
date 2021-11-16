package edu.sjtu.party.utils.weixin;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WxJSsign {
	public static Logger logger = LoggerFactory.getLogger(WxJSsign.class);
	
	public static String getTicket(String accessToken) {
		String ticket = "";
		if (!StringUtils.isEmpty(accessToken)) {
			ticket = WeixinUtils.getJSSDKTicket(accessToken);
		}
		return ticket;
	}
	
	 /** 获取微信签名
	  * 
	  * @param jsapi_ticket
	  * @param url
	  * 
	  * @return
	  */
	 public static Map<String, String> sign(String jsapi_ticket, String url) {
	        Map<String, String> ret = new HashMap<String, String>();
	        String nonce_str = create_nonce_str();
	        String timestamp = create_timestamp();
	        String string1;
	        String signature = "";

	        //注意这里参数名必须全部小写，且必须有序
	        string1 = "jsapi_ticket=" + jsapi_ticket +
	                  "&noncestr=" + nonce_str +
	                  "&timestamp=" + timestamp +
	                  "&url=" + url;
	        logger.info(string1);

	        try
	        {
	            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	            crypt.reset();
	            crypt.update(string1.getBytes("UTF-8"));
	            signature = byteToHex(crypt.digest());
	        }
	        catch (NoSuchAlgorithmException e)
	        {
	            e.printStackTrace();
	        }
	        catch (UnsupportedEncodingException e)
	        {
	            e.printStackTrace();
	        }

	        ret.put("url", url);
	        ret.put("jsapi_ticket", jsapi_ticket);
	        ret.put("nonceStr", nonce_str);
	        ret.put("timestamp", timestamp);
	        ret.put("signature", signature);

	        return ret;
	    }
	 
	 /**byte转换为16进制
	  * 
	  * @param hash
	  * @return
	  */
	 private static String byteToHex(final byte[] hash) {
	        Formatter formatter = new Formatter();
	        for (byte b : hash)
	        {
	            formatter.format("%02x", b);
	        }
	        String result = formatter.toString();
	        formatter.close();
	        return result;
	 }
     
	 /**创建nonceStr
	  * 
	  * @return
	  */
	 public static String create_nonce_str() {
	        return UUID.randomUUID().toString().replace("-", "");
	 }
     
	 /**获取时间戳
	  * 
	  * @return
	  */
	 public static String create_timestamp() {
	        return Long.toString(System.currentTimeMillis() / 1000);
	 }
	 
	 private static String getTime() {
		 Date date = new Date();
		 SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		 logger.info(df.format(date));
		 return df.format(date);
		 
	 }

}
