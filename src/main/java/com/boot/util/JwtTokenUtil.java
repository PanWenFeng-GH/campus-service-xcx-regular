package com.boot.util;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.boot.intecepter.Intecepter;
import com.boot.vo.Token;

public class JwtTokenUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final long EXPIRE_TIME= 7*24*60*60*1000;//毫秒/7天
    private static final String TOKEN_SECRET="c1130327291948bda7be21c17506bcea";  //密钥盐


    /**
     * 签名生成
     * @param user
     * @return
     */
    public static String createSign(Integer businessId, Integer seriaNo, String username, String name,String userId){
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
            		.withIssuer("auth0")
                    .withClaim("businessId",businessId)
                    .withClaim("seriaNo",seriaNo)
                    .withClaim("username",username)
                    .withClaim("name",name)
                    .withClaim("userId",userId)
                    .withClaim("loginTime",new Date().getTime())
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;

    }
    /**
     * 解析
     * @param token
     * @return
     */
    public static Token parse(String token){
    	Token vo = new Token();
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
        DecodedJWT jwt = verifier.verify(token);
//        System.out.println("认证通过：");
//        System.out.println("issuer: " + jwt.getIssuer());
//        System.out.println("username: " + jwt.getClaim("username").asString());
//        System.out.println("过期时间：      " + sdf.format(jwt.getExpiresAt()));
        vo.setBusinessId(jwt.getClaim("businessId").asInt());
        vo.setSeriaNo(jwt.getClaim("seriaNo").asInt());
        vo.setUsername(jwt.getClaim("username").asString());
        vo.setName(jwt.getClaim("name").asString());
        vo.setUserId(jwt.getClaim("userId").asString());
        vo.setLoginTime(jwt.getClaim("loginTime").asLong());
        
        return vo;
    }

    /**
     * 签名验证
     * @param token
     * @return
     */
    public static boolean verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("认证通过：");
            System.out.println("issuer: " + jwt.getIssuer());
            System.out.println("username: " + jwt.getClaim("username").asString());
            System.out.println("过期时间：      " + sdf.format(jwt.getExpiresAt()));
            return true;
        } catch (Exception e){
            return false;
        }
    }
    public static Token getBusinesserToken() throws Exception {
    	String token = getTokenString();
    	return JwtTokenUtil.parse(token);
    }
    /**
	 * 获取请求的Token
	 * @return
	 * @throws Exception
	 */
	public static String getTokenString() throws Exception {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		String token = (String) request.getAttribute(Intecepter.TOKEN_KEY);
		if (StringUtil.isBlank(token)) {
			token = request.getParameter(Intecepter.TOKEN_KEY);
		}
		if (StringUtil.isBlank(token)) {
			Enumeration<?> enum1 = request.getHeaderNames();
	        while (enum1.hasMoreElements()) {
	            String key = (String) enum1.nextElement();
	            String value = request.getHeader(key);
	            if(Intecepter.TOKEN_KEY.equals(key)) {
	            	token = value;
	            }
	        }
		}
		if (StringUtil.isBlank(token)) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (Intecepter.TOKEN_KEY.equals(cookie.getName())) {
						token = URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			} else
				return null;
		}
		if (StringUtil.isBlank(token))
			return null;
		return token;
	}
    public static Integer getBusinessId() {
		Token token = null;
		try {
			token = getBusinesserToken();
		} catch (Exception e) {
			// 捕捉异常
			e.printStackTrace();
		}
		if (token != null) {
			return token.getBusinessId();
		}
		return 0;
	}
    
    public static String getUserId() {
		Token token = null;
		try {
			token = getBusinesserToken();
		} catch (Exception e) {
			// 捕捉异常
			e.printStackTrace();
		}
		if (token != null) {
			return token.getUserId();
		}
		return "";
	}
	public static void main(String[] args) {
		String sign = JwtTokenUtil.createSign(null,null,"","","");
		System.out.println(sign);
		JwtTokenUtil.verify(sign);
	}


}