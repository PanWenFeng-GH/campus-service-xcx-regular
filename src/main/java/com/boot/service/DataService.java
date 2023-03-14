package com.boot.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.entity.TData;

public interface DataService  extends IService<TData>{

	List<TData> pagelist(String name,String type);

}