package com.boot.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信用户表
 */
@Entity
@Table(name = "t_wechat_user")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@ApiModel("小程序用户")
public class TWechatUser implements Serializable {

	private static final long serialVersionUID = 8500502465083714761L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;// 主键

	@ApiModelProperty("昵称")
	private String nickname;// 用户昵称
	
//	private String phone;//手机号
	
	@ApiModelProperty(hidden=true)
	private String imgurl;// 
	
	@ApiModelProperty("真实姓名")
	private String name;//真实名

	@ApiModelProperty("性别,1:男,2:女,0:未知")
	private String sex;// 性别,1:男,2:女,0:未知
	
	@ApiModelProperty("年龄")
	private Integer age;//真实名
	
//	private String province;//省份
	
//	private String city;//城市

//	private String section;//县区
	
	@ApiModelProperty(hidden=true)
	@Column(name = "wx_unionid")
	private String wxUnionid;//unionid
	
	@ApiModelProperty(hidden=true)
	@Column(name = "wx_openid")
	private String wxOpenid;// 微信唯一标识
	
	@ApiModelProperty(hidden=true)
	@Column(name = "wx_nickname")
	private String wxNickname;// 微信用户昵称
	
//	@Column(name = "wx_sex")
//	private String wxSex;// 微信性别,1:男,2:女,0:未知

//	@Column(name = "wx_province")
//	private String wxProvince;// 省份

//	@Column(name = "wx_city")
//	private String wxCity;// 城市

//	@Column(name = "wx_country")
//	private String wxCountry;// 国家

	@ApiModelProperty(hidden=true)
	@Column(name = "wx_headimgurl")
	private String wxHeadimgurl;// 头像

//	@Column(name = "wx_privilege")
//	private String wxPrivilege;// 用户特权信息，json 数组

	@ApiModelProperty(notes="创建时间",hidden=true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "create_time", updatable = false)
	private Date createTime = new Date();// 创建时间

	@ApiModelProperty(hidden=true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "update_time")
	private Date updateTime = new Date();// 修改时间
	
	
}
