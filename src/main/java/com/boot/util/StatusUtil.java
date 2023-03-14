package com.boot.util;

import com.boot.entity.TData;
import com.boot.entity.TWechatUser;

public class StatusUtil {
	/**
	 * 类型，1体重，2血糖，3血压，4心率，5糖化血红蛋白，6血氧，7体温，8尿酸
	 * 1.体重  51-80kg 男，46-55kg 女
	 * 2.血糖 3.9～6.1mmol/l
	 * 3.血压  收缩压在90~140毫米 舒张压在60~90毫米
	 * 4.心率 60～100次/分钟
	 * 5.糖化血红蛋白   4%---6%
	 * 6.血氧     90%---100%
	 * 7.体温     36摄氏度---37摄氏度
	 * 8.尿酸    200-416μmol/L男  150-357μmol/L  女
	 */
	public static String getStatus(TData data,TWechatUser user) {
		if(data.getType()!=null) {
			if(data.getType()==1) {
				//性别,1:男,2:女,0:未知
				if("1".equals(user.getSex())) {
					if(Integer.valueOf(data.getValue())>=51 && Integer.valueOf(data.getValue())<=80) {
						return "1";
					}else return "2";
				}else if("2".equals(user.getSex())) {
					if(Integer.valueOf(data.getValue())>=46 && Integer.valueOf(data.getValue())<=55) {
						return "1";
					}else return "2";
				}
			}else if(data.getType()==2) {//2.血糖 3.9～6.1mmol/l
				if(Double.valueOf(data.getValue())>=3.9 
						&& Double.valueOf(data.getValue())<=6.1) {
					return "1";
				}else return "2";
			}else if(data.getType()==3) {//3血压  收缩压在90~140毫米 舒张压在60~90毫米  
				if(Integer.valueOf(data.getValue())>=90 && Integer.valueOf(data.getValue())<=14
						&& Integer.valueOf(data.getValue2())>=60 && Integer.valueOf(data.getValue2())<=90) {
					return "1";
				}else return "2";
			}else if(data.getType()==4) {//4.心率 60～100次/分钟
				if(Double.valueOf(data.getValue())>=60 
						&& Double.valueOf(data.getValue())<=100) {
					return "1";
				}else return "2";
			}else if(data.getType()==5) {//5糖化血红蛋白   4%---6%
				if(Double.valueOf(data.getValue())>=4 
						&& Double.valueOf(data.getValue())<=6) {
					return "1";
				}else return "2";
			}else if(data.getType()==6) {//6.血氧     90%---100%
				if(Double.valueOf(data.getValue())>=90 
						&& Double.valueOf(data.getValue())<=100) {
					return "1";
				}else return "2";
			}else if(data.getType()==7) {//7.体温     36摄氏度---37摄氏度
				if(Double.valueOf(data.getValue())>=36 
						&& Double.valueOf(data.getValue())<=37) {
					return "1";
				}else return "2";
			}else if(data.getType()==4) {//8.尿酸    200-416μmol/L男  150-357μmol/L  女
				//性别,1:男,2:女,0:未知
				if("1".equals(user.getSex())) {
					if(Integer.valueOf(data.getValue())>=200 && Integer.valueOf(data.getValue())<=416) {
						return "1";
					}else return "2";
				}else if("2".equals(user.getSex())) {
					if(Integer.valueOf(data.getValue())>=150 && Integer.valueOf(data.getValue())<=357) {
						return "1";
					}else return "2";
				}
			}
		}
		return "1";
	}
}
