package com.boot.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 反馈
 */
@Entity
@Table(name = "c_feedback")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@ApiModel("数据模块")
public class Feedback implements Serializable {

	private static final long serialVersionUID = 8500502465083714761L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;// 主键

	@ApiModelProperty(hidden=true)
	@Column(name = "wechat_id")
	private String wechatId;
	
	private String name;//标题
	
	private String content;//内容

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
	private TWechatUser wechatUser;
}
