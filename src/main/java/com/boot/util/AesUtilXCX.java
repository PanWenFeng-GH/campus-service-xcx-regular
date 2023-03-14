package com.boot.util;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AesUtilXCX {
	/**
	 * AES解密
	 *
	 * @param data
	 *            //密文，被加密的数据
	 * @param key
	 *            //秘钥
	 * @param iv
	 *            //偏移量
	 * @param encodingFormat
	 *            //解密后的结果需要进行的编码
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data, String key, String iv, String encodingFormat) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		// 被加密的数据
		byte[] dataByte = Base64.decodeBase64(data);
		// 加密秘钥
		byte[] keyByte = Base64.decodeBase64(key);
		// 偏移量
		byte[] ivByte = Base64.decodeBase64(iv);
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, encodingFormat);
				return result;
			}
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidParameterSpecException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject decryptData(String sessionKey,String encryptedData, String iv) throws Exception{
        byte[] dataByte = Base64.decodeBase64(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decodeBase64(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decodeBase64(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
//            Security.addProvider(new BouncyCastleProvider());
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding","BC");
//            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static void main(String[] args) throws Exception {
		//String encryptedData ="MsazjXPX2GwzDkG/blYGRBsRcuG5xH9ixyyLnzGGmXOfRLsY0RVINhE56spOE7Du2mPh7v+Fr5zSRw7DMPsFVRsweBFgsWL7M5Ct2sqiSuUasyKHsPP0X3zAIQ+spRqpmXfBCEsKZWy8Df23OtPZ6RUSHq3aHYdXmuoYDgDyI5sNV3GnDGORJgNLSPoh8pYVfWmfAvBpYr9F+n21mqq5kA==";
		//String  sessionKey = "ZBunETwN5Q3X9k1L7pBMog==";
		//String  iv = "p4jUqxEG5MmrlQGav6LPAQ==";
		//{"phoneNumber":"18326109103","purePhoneNumber":"18326109103","countryCode":"86",
		//"watermark":{"timestamp":1615645452,"appid":"wx073ef42f3aef8373"}}
		//WxGetPhoneNumber aes=new WxGetPhoneNumber(encryptedData,sessionKey,iv);
        //System.out.println(aes.getPhoneNumber());
	}
}
