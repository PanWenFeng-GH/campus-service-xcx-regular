package com.boot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 吃喝玩乐
 */
@Entity
@Table(name = "c_goods")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@ApiModel("数据模块")
public class Goods implements Serializable {

	private static final long serialVersionUID = 8500502465083714761L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;// 主键
	
	private String name;//标题

	private String img;

	private BigDecimal price;

	private String imgs;

	private String content;//内容

	@Column(name = "if_good")
	private Integer ifGood;//1是，0否

	@ApiModelProperty(hidden=true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "create_time", updatable = false)
	private Date createTime = new Date();// 创建时间

	@ApiModelProperty(hidden=true)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	@Column(name = "update_time")
	private Date updateTime = new Date();// 修改时间
}
