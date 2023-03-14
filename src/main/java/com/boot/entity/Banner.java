package com.boot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 轮播图
 */
@Entity
@Table(name = "c_banner")
@TableName("c_banner")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@Data
public class Banner implements Serializable {
	private static final long serialVersionUID = -3409036381834224416L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String id;//主键

	@Column(name = "name")
	private String name;//标题名称

	@Column(name = "url")
	private String url;//链接地址

	@Column(name = "img")
	private String img;//图片

	@Column(name = "type")
	private String type;//

	@Column(name = "sort")
	private Integer sort;//排序

	@Column(name = "create_time", updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date createTime = new Date();// 创建时间

	@Column(name = "update_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date updateTime = new Date();// 修改时间

}
