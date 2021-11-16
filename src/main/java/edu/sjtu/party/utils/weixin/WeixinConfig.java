package edu.sjtu.party.utils.weixin;

public class WeixinConfig {


	/**
	 * 获取opId
	 */
	public static String createOpenIdURL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	/**微信JSSDK的ticket请求URL地址 
	 * 
	 */
	public final static String weixin_jssdk_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	
	/**
	 * 支付二维码链接
	 */
	public final static String weixin_pay_qrcode = "weixin://wxpay/bizpayurl?sign=SIGN&appid=APPID&mch_id=MCH_ID&product_id=PRODUCT_ID&time_stamp=TIME_STAMP&nonce_str=NONCE_STR";
	
	/**
	 * 微信扫码支付长连接生成短连接请求
	 */
	public final static String weixin_gen_short_url = "https://api.mch.weixin.qq.com/tools/shorturl";

	//网页授权OAuth2.0获取用户信息
	public static final String GET_OAUTH_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";


}
