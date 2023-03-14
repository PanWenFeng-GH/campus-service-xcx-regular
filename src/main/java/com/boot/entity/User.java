package com.boot.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "t_user")
@Data
public class User implements Serializable{
	private static final long serialVersionUID = -4298309205022284451L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String username;//登录账号
	private String password;//密码
	private Integer type;//用户类型 ，1普通用户，2管理员
	
	@OneToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name="role_id",referencedColumnName="id",nullable=true)
	@NotFound(action=NotFoundAction.IGNORE)
	private Role role;
	
	@Column(name="create_time")
	private Date createTime = new Date();//创建时间
	
	@Column(name="update_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private Date updateTime = new Date();
}
