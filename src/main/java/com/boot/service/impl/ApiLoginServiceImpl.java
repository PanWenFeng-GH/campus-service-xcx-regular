package com.boot.service.impl;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.boot.entity.TWechatUser;
import com.boot.repository.TWechatUserRepository;
import com.boot.service.ApiLoginService;
import com.boot.util.StringUtil;
import com.boot.util.WechatUtil;
import com.boot.util.htttp.HttpResponse;
import com.boot.util.htttp.HttpUtil;
 
@Service
public class ApiLoginServiceImpl implements ApiLoginService{
	private static Logger logger = LoggerFactory.getLogger(ApiLoginServiceImpl.class);
	@Autowired
	private TWechatUserRepository wechatUserRepository;
 
	@Override
	@Transactional
	public TWechatUser getOpenId(String code, String dataSource) throws Exception {
		TWechatUser wechatUser = null;
		HttpResponse res = null;
		logger.info("OPENID_URI==" + String.format(WechatUtil.OPENID_URI,WechatUtil.appId,WechatUtil.secret,code));
		res = HttpUtil.post(String.format(WechatUtil.OPENID_URI,WechatUtil.appId,WechatUtil.secret,code));
		
		String jsonString = res.getContent();
		logger.info("jsonString==" + jsonString);
		JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonString);
		String openid = (String) jsonObject.get("openid");
		String accessToken = (String) jsonObject.get("access_token");
		if(StringUtil.isNotBlank(openid)){
			String openIdUrl2 = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openid+"&lang=zh_CN";
			HttpResponse res2 = HttpUtil.post(openIdUrl2);
			String jsonString2 = res2.getContent();
			logger.info("jsonString2=="+jsonString2);
			JSONObject userJson = (JSONObject) JSONObject.parse(jsonString2);
			String nickname = userJson.getString("nickname");
			//nickname = Base64Utils.getBase64Str(nickname);
			//nickname = nickname.replaceAll("[\ue000-\uefff]", "");
			String sex = userJson.getString("sex");
			String province = userJson.getString("province");
			String city = userJson.getString("city");
			String country = userJson.getString("country");
			String headimgurl = userJson.getString("headimgurl").trim();
			String privilege = userJson.getString("privilege");
			String unionid = userJson.getString("unionid");
			
			logger.info("==================");
			logger.info("nickname=="+nickname);
			logger.info("sex=="+sex);
			logger.info("province=="+province);
			logger.info("city=="+city);
			logger.info("country=="+country);
			logger.info("headimgurl=="+headimgurl);
			logger.info("privilege=="+privilege);
			logger.info("unionid=="+unionid);
			logger.info("===================");
			
			wechatUser = wechatUserRepository.findByWxOpenId(openid);
			//if (wechatUser == null) wechatUser = wechatUserRepository.findByWxUnionid(unionid);
			//if (StringUtil.isNotBlank(unionid)&&wechatUser == null) wechatUser = new TWechatUser();
			if (wechatUser == null) wechatUser = new TWechatUser();
			wechatUser.setWxOpenid(openid);
			
			if (StringUtil.isBlank(wechatUser.getNickname()))wechatUser.setNickname(nickname);
			if (StringUtil.isBlank(wechatUser.getSex()))wechatUser.setSex(sex);
			if (StringUtil.isBlank(wechatUser.getImgurl()))wechatUser.setImgurl(headimgurl);
			//省 城市
//			if(StringUtil.isBlank(wechatUser.getProvince()))wechatUser.setProvince(province);
//			if(StringUtil.isBlank(wechatUser.getCity()))wechatUser.setCity(city);
			
			wechatUser.setWxNickname(nickname);
//			wechatUser.setWxSex(sex);
//			wechatUser.setWxProvince(province);
//			wechatUser.setWxCity(city);
//			wechatUser.setWxCountry(country);
			wechatUser.setWxHeadimgurl(headimgurl);
//			wechatUser.setWxPrivilege(privilege);
			wechatUser.setWxUnionid(unionid);
			
			wechatUserRepository.save(wechatUser);
		}
		return wechatUser;
	}
}