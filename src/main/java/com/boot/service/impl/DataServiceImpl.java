package com.boot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.entity.TData;
import com.boot.mapper.DataDao;
import com.boot.service.DataService;

@Service
public class DataServiceImpl extends ServiceImpl<DataDao, TData> implements DataService {
	@Autowired
	private DataDao dataDao;
	@Override
	public List<TData> pagelist(String name,String type) {
		return dataDao.pagelist(name,type);
	}

}
