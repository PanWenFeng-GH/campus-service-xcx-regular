package com.boot.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.boot.util.htttp.HttpResponse;
import com.boot.util.htttp.HttpUtil;

@Component
@PropertySource("classpath:config.properties")
public class WechatUtil {
	private static Logger logger = LoggerFactory.getLogger(WechatUtil.class);
//	public final static String WX_NOTIFY = "https://api.cyh.smates.top/api/wechat/pay/notify";
	public final static String WX_NOTIFY = "https://agent.smates.top/api/pay/notify";
	public final static String GET_OPENID_REDIRECT_URI = "";
	public static String access_token = null;
	public static Date access_token_date = null;
	public static String wxTemplateId;
	public static String appId;
	public static String secret;
	//小程序
	public static String xcxAppid;
	public static String xcxSecret;
	//app公众号
	public static String appAppid;
	public static String appSecret;

	@Value("${wx.app.appId}")
	public void setAppAppid(String appAppid) {
		WechatUtil.appAppid = appAppid;
	}

	@Value("${wx.app.secret}")
	public void setAppSecret(String appSecret) {
		WechatUtil.appSecret = appSecret;
	}

	@Value("${wx.template.id}")
	public void setWxTemplateId(String wxTemplateId) {
		WechatUtil.wxTemplateId = wxTemplateId;
	}

	@Value("${wx.secret}")
	public void setSecret(String secret) {
		WechatUtil.secret = secret;
	}

	@Value("${wx.appId}")
	public void setAppId(String appId) {
		WechatUtil.appId = appId;
	}

	@Value("${wx.xcx.appId}")
	public void setXcxAppId(String appId) {
		WechatUtil.xcxAppid = appId;
	}

	@Value("${wx.xcx.secret}")
	public void setXcxSecret(String secret) {
		WechatUtil.xcxSecret = secret;
	}

//	public final static String MCH_ID = "1603115660";//商户1603115660
//	public final static String MCH_API = "95d10e36cf4347a8b0c1f092f6d99a8e";//商户API
	//公司
//	public final static String MCH_ID = "1603352463";//商户
//	public final static String MCH_API = "c1639d4084836565ba58ad35924727a0";//商户API
	//美
	public final static String MCH_ID = "1574278171";//商户
	//public final static String MCH_API = "shanghaiqingyunjiankangkeji54321";//商户API
	public final static String MCH_API = "shanghaiqingyun20207115742781710";//商户API
	//public final static String REDIRECT ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";
	public final static String OPENID_URI = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	public static Map<String, String> getWechatPara(String url) {
		String jsapi_ticket = null;
		access_token = getAccessToken();
		jsapi_ticket = getTicket(access_token);
		return sign(jsapi_ticket, url);
	}

	/**
	 * 获取token
	 * @return
	 */
	private static String getAccessToken() {
		if (access_token_date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(access_token_date);
			calendar.add(Calendar.SECOND, 7200);
			if (new Date().before(calendar.getTime())) {
				return access_token;
			}
		}
		access_token_date = new Date();
		String grant_type = "client_credential";
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grant_type + "&appid=" + appId + "&secret=" + secret; //访问链接
		logger.info("========getAccessToken===="+url);
		System.out.println("========getAccessToken===="+url);
		try {
			HttpResponse response = HttpUtil.get(url);
			System.out.println(response.getContent());
			JSONObject jsonObject = JSONObject.parseObject(response.getContent());
			access_token = jsonObject.getString("access_token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("access_token:{}", access_token);
		return access_token;
	}

	/**
	 * 获取ticket
	 * @return
	 */
	private static String getTicket(String access_token) {
		String ticket = null;
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";//这个url链接和参数不能变  
		try {
			HttpResponse response = HttpUtil.get(url);
			System.out.println(response.getContent());
			JSONObject jsonObject = JSONObject.parseObject(response.getContent());
			ticket = jsonObject.getString("ticket");
			System.out.println(ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}

	/**
	 * 签名
	 * @return
	 */
	private static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ret.put("appId", appId);
		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		return ret;
	}

	/**
	 * 转换
	 * @return
	 */
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static String create_nonce_str() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}

	public static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * Map
	 * @return
	 */
	public static Map<String, String> getMap(String body, String nonceStr, String openid, String orderNo, String ip, String totalFee) {
		Map<String, String> map = new HashMap<>();
		map.put("appid", appId);//公众号id
		map.put("body", body);//商品描述
		map.put("mch_id", MCH_ID);//商户平台id
		map.put("nonce_str", nonceStr);//随机字符串
		map.put("notify_url", WX_NOTIFY);//异步回调api
		map.put("openid", openid);//支付用户openid
		map.put("out_trade_no", orderNo);//商品订单号
		map.put("spbill_create_ip", ip);//支付ip
		map.put("total_fee", totalFee);//真实金额
		map.put("trade_type", "JSAPI");//JSAPI、h5调用
		return map;
	}
	public static Map<String, String> getMapXCX(String body, String nonceStr, String openid, String orderNo, String ip, String totalFee) {
		Map<String, String> map = new HashMap<>();
		map.put("appid", xcxAppid);//公众号id
		map.put("body", body);//商品描述
		map.put("mch_id", MCH_ID);//商户平台id
		map.put("nonce_str", nonceStr);//随机字符串
		map.put("notify_url", WX_NOTIFY);//异步回调api
		map.put("openid", openid);//支付用户openid
		map.put("out_trade_no", orderNo);//商品订单号
		map.put("spbill_create_ip", ip);//支付ip
		map.put("total_fee", totalFee);//真实金额
		map.put("trade_type", "JSAPI");//JSAPI、h5调用
		return map;
	}

	/**
	 * XML
	 * @return
	 */
	public static String getXML(String body, String nonceStr, String openid, String orderNo, String ip, String totalFee, String sign) {
		String xml = "<xml>" + "<appid>" + appId + "</appid>" + "<body>" + body + "</body>" + "<mch_id>" + MCH_ID + "</mch_id>" + "<nonce_str>" + nonceStr + "</nonce_str>" + "<notify_url>" + WX_NOTIFY
				+ "</notify_url>" + "<openid>" + openid + "</openid>" + "<out_trade_no>" + orderNo + "</out_trade_no>" + "<spbill_create_ip>" + ip + "</spbill_create_ip>" + "<total_fee>" + totalFee
				+ "</total_fee>" + "<trade_type>JSAPI</trade_type>" + "<sign>" + sign + "</sign>" + "</xml>";
		return xml;
	}
	
	public static String getXMLXCX(String body, String nonceStr, String openid, String orderNo, String ip, String totalFee, String sign) {
		String xml = "<xml>" + "<appid>" + xcxAppid + "</appid>" + "<body>" + body + "</body>" + "<mch_id>" + MCH_ID + "</mch_id>" + "<nonce_str>" + nonceStr + "</nonce_str>" + "<notify_url>" + WX_NOTIFY
				+ "</notify_url>" + "<openid>" + openid + "</openid>" + "<out_trade_no>" + orderNo + "</out_trade_no>" + "<spbill_create_ip>" + ip + "</spbill_create_ip>" + "<total_fee>" + totalFee
				+ "</total_fee>" + "<trade_type>JSAPI</trade_type>" + "<sign>" + sign + "</sign>" + "</xml>";
		return xml;
	}

	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtil.isNotBlank(ip)) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

	/**
	 * 提现xml
	 * @param openid
	 * @param orderNo
	 * @param ip
	 * @param totalFee
	 * @param sign
	 * @return
	 */
	public static String getTransfersXML(String openid, String nonceStr, String orderNo, String ip, String totalFee, String sign) {
		String xml = "<xml>" + "<mch_appid>" + appId + "</mch_appid>" + "<mchid>" + MCH_ID + "</mchid>" + "<nonce_str>" + nonceStr + "</nonce_str>" + "<partner_trade_no>" + orderNo
				+ "</partner_trade_no>" + "<openid>" + openid + "</openid>" + "<check_name>NO_CHECK</check_name>" + "<re_user_name>孙标</re_user_name>" + "<amount>" + totalFee + "</amount>"
				+ "<desc>测试转账到个人</desc>" + "<spbill_create_ip>" + ip + "</spbill_create_ip>" + "<sign>" + sign + "</sign>" + "</xml>";
		return xml;
	}

	/**
	 * 退款xml
	 * @param openid
	 * @param nonceStr
	 * @param orderNo
	 * @param ip
	 * @param totalFee
	 * @param sign
	 * @return
	 */
	public static String getRefundXml(String nonceStr, String orderNo, String totalFee, String sign) {
		String xml = "<xml>" + "<appid>" + appId + "</appid>" + "<mch_id>" + MCH_ID + "</mch_id>" + "<nonce_str>" + nonceStr + "</nonce_str>" + "<out_refund_no>" + orderNo + "</out_refund_no>"
				+ "<out_trade_no>" + orderNo + "</out_trade_no>" + "<refund_fee>" + totalFee + "</refund_fee>" + "<total_fee>" + totalFee + "</total_fee>" + "<sign>" + sign + "</sign>" + "</xml>";
		return xml;
	}
	/**
	 * 发送模板消息
	 * @param tmpl
	 * @return
	 */
	//	public static JSONObject sendTmplMsg(WeChatTemplate tmpl) {
	//		try {
	//			//获取accessTokenMap
	//			getAccessToken();
	//			//将模板对象序列化成JSON字符串
	//			String jsonMsg = JSON.toJSONString(tmpl);
	//			//请求接口地址
	//			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" 
	//						+ access_token;
	//			logger.info("发送模板消息url:{}",requestUrl);
	//			//发送请求
	//			String result = HttpUtil.httpRequest(requestUrl, "POST", jsonMsg);
	//			return JSON.parseObject(result);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//		
	//		return null;
	//	}
	/**
	 * 小程access_token
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static String getXcxAccessToken(String appid,String secret) {
		String access_token = null;
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret ;//这个url链接和参数不能变  
		try {
			HttpResponse response = HttpUtil.get(url);
			System.out.println(response.getContent());
			JSONObject jsonObject = JSONObject.parseObject(response.getContent());
			access_token = jsonObject.getString("access_token");
			System.out.println(access_token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return access_token;
	}
	
}