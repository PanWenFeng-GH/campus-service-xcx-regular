package com.boot.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;

/**
 *  字典
 *  标签
 */
@Entity
@Table(name = "t_dictionary")
@Data
public class Dictionary implements Serializable {
	private static final long serialVersionUID = 7989646172462159020L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer pid;
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.REFRESH,targetEntity=Dictionary.class)  
	@JoinColumn(name="pid", referencedColumnName="id")
	@NotFound(action=NotFoundAction.IGNORE)
	private List<Dictionary> children;
	private String code;
	private String name;

}
