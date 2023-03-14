package com.boot.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import lombok.Data;

@Entity
@Table(name = "t_menu")
@Data
public class Menu implements Serializable{
	private static final long serialVersionUID = 1181635814644845327L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//主键
	
	private String  name;
	private String  url;
	
	@Column(name="p_id")
	private Integer supId;
	
	private Integer status;//0 启用  1 禁用
	private String icon;//显示图标
	private Integer sort;//排序
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH,targetEntity=Menu.class)  
	@JoinColumn(name="p_id", referencedColumnName="id")
	@NotFound(action=NotFoundAction.IGNORE)
	@OrderBy("sort ASC")
	@Where(clause="status=0")
	private List<Menu> childMenu;
	
	@Column(name="create_time",updatable=false)
	private Date createTime = new Date();//创建时间
}
