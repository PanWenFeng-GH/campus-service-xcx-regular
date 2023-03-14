package com.boot.api;

import com.alibaba.fastjson.JSONObject;
import com.boot.entity.TWechatUser;
import com.boot.repository.TWechatUserRepository;
import com.boot.util.AesUtilXCX;
import com.boot.util.Base64Utils;
import com.boot.util.JwtTokenUtil;
import com.boot.util.WechatUtil;
import com.boot.util.htttp.HttpResponse;
import com.boot.util.htttp.HttpUtil;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(value = "小程序用户接口", tags = "小程序用户接口")
public class ApiWechatController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//@Autowired
	//private ApiLoginService apiLoginService;
	@Autowired
	private TWechatUserRepository wechatUserRepository;

	/**
	 * 小程序微信用户信息
	 * @param encryptedData
	 * @param iv
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("登录")
	@RequestMapping("/getOpenId")
	public BaseMessage<?> getXCXOpenId(String encryptedData, String iv, String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			logger.info("code==" + code);
			logger.info("encryptedData==" + encryptedData);
			logger.info("iv==" + iv);
			String avatarUrl = request.getParameter("avatarUrl");
			String nickName = request.getParameter("nickName");
			if (code == null) {
				return null;
			}
			String grant_type = "authorization_code";
			String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + WechatUtil.xcxAppid + "&secret=" + WechatUtil.xcxSecret + "&js_code=" + code + "&grant_type=" + grant_type;
			HttpResponse res = HttpUtil.post(url);
			String jsonString = res.getContent();
			JSONObject json = (JSONObject) JSONObject.parse(jsonString);
			logger.info("json==" + json);
			String session_key = json.get("session_key").toString();
			String openid = (String) json.get("openid");
			//数据存储对比
			TWechatUser wechatUser = wechatUserRepository.findByWxOpenId(openid);
			if (wechatUser == null) {
				wechatUser = new TWechatUser();
				wechatUser.setName(nickName);
			}
			wechatUser.setWxOpenid(openid);
			try {
				String result = AesUtilXCX.decrypt(encryptedData, session_key, iv, "UTF-8");
				logger.info("result==" + result);
				if (null != result && result.length() > 0) {
					logger.info("encryptedData加密数据进行AES解密");
					JSONObject userJson = JSONObject.parseObject(result);
					//{"country":"China","watermark":{"appid":"wx9a126e59924a5e7d","timestamp":1555925735},"gender":1,"province":"Anhui","city":"Hefei","avatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKMkeOqTiaF6eQ3ib1E4sld0bHGKHv4WtHeM6qYBZTETRlGGWHgoY0eldfmUuMwys23K6RvJXl03W5g/132","openId":"ocTdO5VuzWIQBU1qxuN2KVHVK8pw","nickName":"孙标","language":"zh_CN"}
					logger.info("userJson==" + userJson);
					String nickname = userJson.get("nickName").toString();
					nickname = Base64Utils.getBase64Str(nickname);
					// nickname = nickname.replaceAll("[\ue000-\uefff]", "");
					String sex = userJson.get("gender").toString();
					String province = (String) userJson.get("province");
					String city = (String) userJson.get("city");
					String country = (String) userJson.get("country");
					String headimgurl = userJson.get("avatarUrl").toString().trim();
					logger.info("==================");
					logger.info("nickname==" + nickname);
					logger.info("sex==" + sex);
					logger.info("province==" + province);
					logger.info("city==" + city);
					logger.info("country==" + country);
					logger.info("headimgurl==" + headimgurl);
					logger.info("==================");
 
					wechatUser.setNickname(nickname);
					wechatUser.setSex(sex);
					wechatUser.setName(userJson.get("nickName").toString());
					wechatUser.setImgurl(headimgurl);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			wechatUser.setNickname(nickName);
			wechatUser.setWxHeadimgurl(avatarUrl);
			wechatUserRepository.save(wechatUser);
			//返回值
			Map<String,Object> dataMap = new HashMap<>();
			dataMap.put("token",JwtTokenUtil.createSign(null, 0,wechatUser.getName(),wechatUser.getName(), wechatUser.getId()));
			dataMap.put("session_key", session_key);
			dataMap.put("openid", openid);
//			dataMap.put("phone", wechatUser.getPhone());
			
			return MessageHandler.createSuccessVo(dataMap, "获取成功");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	@ApiOperation("获取用户信息")
	@RequestMapping("/getUserInfo")
	public BaseMessage<?> getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		TWechatUser wechatUser = wechatUserRepository.getOne(JwtTokenUtil.getUserId());
		return MessageHandler.createSuccessVo(wechatUser,"获取成功");
	}
	
	@ApiOperation("更新用户信息")
	@RequestMapping("/updateUserInfo")
	public BaseMessage<?> updateUserInfo(TWechatUser wechatUser,HttpServletRequest request, HttpServletResponse response) throws Exception {
		TWechatUser oldwechatUser = wechatUserRepository.getOne(JwtTokenUtil.getUserId());
		//
		oldwechatUser.setName(wechatUser.getName());
		oldwechatUser.setSex(wechatUser.getSex());
		oldwechatUser.setAge(wechatUser.getAge());

		wechatUserRepository.save(oldwechatUser);
		return MessageHandler.createSuccessVo(wechatUser,"获取成功");
	}
	
}
