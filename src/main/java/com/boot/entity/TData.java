package com.boot.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据表
 */
@Entity
@Table(name = "t_data")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@ApiModel("数据模块")
public class TData implements Serializable {

	private static final long serialVersionUID = 8500502465083714761L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;// 主键

	@ApiModelProperty(hidden=true)
	@Column(name = "wechat_id")
	private String wechatId;
	
	@ApiModelProperty("显示值1")
	private String value;
	
	@ApiModelProperty("显示值2(如:伸缩压)")
	private String value2;// 
	
	@ApiModelProperty("状态，1正常，2异常")
	private String status;//状态
	
	@ApiModelProperty("类型，1体重，2血糖，3血压，4心率，5糖化血红蛋白，6血氧，7体温，8尿酸")
	private Integer type;//1体重，2血糖，3血压，4心率
	
	private String mtype;//额外参数，细化同类数据
	
	@ApiModelProperty(value="录入时间",hidden=true)
	private String date;//时间

	@ApiModelProperty(hidden=true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "create_time", updatable = false)
	private Date createTime = new Date();// 创建时间

	@ApiModelProperty(hidden=true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "update_time")
	private Date updateTime = new Date();// 修改时间

	@Transient
	@TableField(exist=false)
	@ApiModelProperty(hidden=true)
	private String name;
}
