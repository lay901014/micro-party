package edu.sjtu.party.utils.weixin;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import edu.sjtu.party.model.UserProfile;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: lay
 * @create: 2021/11/12 15:37
 **/
public class WeixinUtils {

    public static Logger logger = LoggerFactory.getLogger(WeixinUtils.class);
    /**
     * 获取openId
     * @param code
     * @return
     */
    public static UserProfile getSpecAccessToken(String appId, String appScreat, String code) {

        String requestUrl = WeixinConfig.createOpenIdURL.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appScreat);
        requestUrl = requestUrl.replace("CODE", code);
        JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, "GET", null);
        if(null != jsonObject) {
            try {
                //网页授权OAuth2.0获取用户信息
                String userInfoUrl=String.format(WeixinConfig.GET_OAUTH_USERINFO, jsonObject.getString("access_token"), jsonObject.getString("openid"));
                JSONObject jsonObject2= HttpRequestUtil.httpRequest(userInfoUrl,"GET", null);
                if(null != jsonObject2 && !jsonObject2.containsKey("errcode")){
                    try{
                       UserProfile userProfile = JSONObject.parseObject(JSONObject.toJSONString(jsonObject2), UserProfile.class);
                       userProfile.setAccessToken(jsonObject.getString("access_token"));
                       return userProfile;
                    } catch (JSONException e) {
                        logger.error("weixin json parse error: {}", e.getMessage());
                    }
                }else{
                    logger.error("wechat error: {}", jsonObject2.containsKey("errcode"));
                }
            }catch(JSONException e) {
               logger.error("weixin json parse error: {}", e.getMessage());
            }
        }
        return null;
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

    /**
     * 获取微信JSSDK的access_token
     * @author Benson
     */
    public static String getJSSDKAccessToken(String tokenurl) {
        String accessToken = "";
        JSONObject jsonObject = HttpRequestUtil.httpRequest(tokenurl, "GET", null);  //Http GET请求
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = jsonObject.getString("access_token");
                logger.info(accessToken);
                if(StringUtils.isEmpty(accessToken)) {
                    logger.info(jsonObject.getString("errmsg"));
                }
            } catch (JSONException e) {
                accessToken = null;
            }
        }
        return accessToken;
    }

    /**
     * 获取微信JSSDK的ticket
     * @author Benson
     */
    public static String getJSSDKTicket(String access_token) {
        String apiTicket = "";
        String requestUrl = WeixinConfig.weixin_jssdk_ticket_url.replace("ACCESS_TOKEN", access_token);
        JSONObject jsonObject = HttpRequestUtil.httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                apiTicket = jsonObject.getString("ticket");
            } catch (JSONException e) {
                apiTicket = null;
            }
        }
        return apiTicket;
    }

    /**
     * 生成支付二维码链接
     * @return
     */
    public static String getPayQrcodeLink(String appId, String mchId,
                                          String orderId, String mchSecret) {
        String qrcodeLink = "";
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appId);
        packageParams.put("mch_id", mchId);
        packageParams.put("time_stamp", create_timestamp());
        packageParams.put("nonce_str", create_nonce_str());
        packageParams.put("product_id", orderId);
        String sign = WeixinSignUtil.createSign(packageParams, mchSecret);
        qrcodeLink = WeixinConfig.weixin_pay_qrcode.replace("APPID", appId);
        qrcodeLink = qrcodeLink.replace("SIGN", sign);
        qrcodeLink = qrcodeLink.replace("MCH_ID", mchId);
        qrcodeLink = qrcodeLink.replace("PRODUCT_ID", orderId);
        qrcodeLink = qrcodeLink.replace("TIME_STAMP", packageParams.get("time_stamp"));
        qrcodeLink = qrcodeLink.replace("NONCE_STR", packageParams.get("nonce_str"));
        qrcodeLink = genWeiLongUrltoShort(appId, mchId, qrcodeLink, mchSecret);
        return qrcodeLink;
    }

    /**
     * 微信扫码支付长连接生成短连接
     * @param appId
     * @param mchId
     * @return
     */
    public static String genWeiLongUrltoShort(String appId, String mchId, String longUrl,
                                              String mchSecret) {
        SortedMap<String, String> map = new TreeMap<String, String>();
        map.put("appid", appId);
        map.put("mch_id", mchId);
        map.put("long_url", longUrl);
        map.put("nonce_str", create_nonce_str());
        String sign = WeixinSignUtil.createSign(map, mchSecret);
        map.put("sign", sign);
        String xmlStr = XmlUtil.map2XmlString(map);
        String result = HttpRequestUtil.httpRequestStr(WeixinConfig.weixin_gen_short_url, "POST", xmlStr);
        if(result == null || result =="") {
            return "";
        }else {
            try {
                SortedMap<String, String> sortMap = XmlUtil.payParseXml(result);
                if(sortMap.get("return_code").equalsIgnoreCase("SUCCESS")) {
                    if(sortMap.get("result_code").equalsIgnoreCase("SUCCESS")) {
                        String signString = WeixinSignUtil.createSign(sortMap, mchSecret);
                        if(sortMap.get("sign").equals(signString)) {
                            return sortMap.get("short_url");
                        }else {
                            return "";
                        }
                    }else {
                        return "";
                    }

                }else {
                    return "";
                }
            }catch(Exception e) {
                e.printStackTrace();
            }

        }
        return "";
    }

    public static String filterEmoji2(String source){
        if(source != null){
            Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE ) ;
            Matcher emojiMatcher = emoji.matcher(source);
            if ( emojiMatcher.find()){
                source = emojiMatcher.replaceAll("");
                return source ;
            }
            return source;
        }
        return source;
    }


    /**
     * 过滤emoji 或者 其他非文字类型的字符
     * @param source
     * @return
     */
    public static String filterEmoji(String source){
        int len = source.length();
        StringBuilder buf = new StringBuilder(len);
        for(int i = 0; i < len; i++){
            char codePoint = source.charAt(i);
            if(isNotEmojiCharacter(codePoint)){
                buf.append(codePoint);
            }else{
                buf.append("");
            }
        }
        return buf.toString();
    }

    private static boolean isNotEmojiCharacter(char codePoint){
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
}
