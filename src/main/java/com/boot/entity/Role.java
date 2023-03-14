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
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "t_role")
@Data
public class Role implements Serializable {
	private static final long serialVersionUID = -7536712688195725680L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//主键

	@Column(name="role_code")
	private String roleCode;
	
	@Column(name="role_name")
	private String roleName;
	private String description;
	
	@OneToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name="update_user",referencedColumnName="id",nullable=true)
	@NotFound(action=NotFoundAction.IGNORE)
	@JsonIgnore
	private User updateUser;
	
	@Column(name="update_time")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private Date updateTime = new Date();
	
	/*@OneToMany(fetch=FetchType.LAZY)  
	@JoinTable(name="sys_role_menu",
				joinColumns={
						@JoinColumn(name="role_id",referencedColumnName="id")},
				inverseJoinColumns={
						@JoinColumn(name="menu_id",referencedColumnName="id")})
	@NotFound(action=NotFoundAction.IGNORE)
	private List<Menu> menuList;*/

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleCode=" + roleCode + ", roleName=" + roleName + ", description=" + description
				+ ",updateTime=" + updateTime + "]";
	}
	
	
	
}