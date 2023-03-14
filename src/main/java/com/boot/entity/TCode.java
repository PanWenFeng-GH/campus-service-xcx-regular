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
 * 数据表
 */
@Entity
@Table(name = "t_code")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@ApiModel("数据模块")
public class TCode implements Serializable {

	private static final long serialVersionUID = 8500502465083714761L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;// 主键

	@ApiModelProperty(hidden=true)
	@Column(name = "user_id")
	private Integer userId;
	
	private String name;
	
	@ApiModelProperty("内容")
	private String content;// 
	
	@ApiModelProperty("描述")
	private String description;//状态

	private Integer type;//1.毕业生服务,2专升本服务,3驾校服务,4社团服务,5考试公告,6团建,7消息,8学校公告
	
	@ApiModelProperty(hidden=true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "create_time", updatable = false)
	private Date createTime = new Date();// 创建时间

	@ApiModelProperty(hidden=true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "update_time")
	private Date updateTime = new Date();// 修改时间

	@ApiModelProperty(hidden=true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "release_time")
	private Date releaseTime;
}
