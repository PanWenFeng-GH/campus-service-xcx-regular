package com.boot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.boot.entity.TWechatUser;
import com.boot.mapper.WechatUserMapper;
import com.boot.repository.TWechatUserRepository;
import com.boot.service.WechatUserService;
import com.boot.util.JpaUtil;
import com.boot.util.PageRequestHelper;
import com.boot.util.StringUtil;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;


@Service
public class WechatUserServiceImpl implements WechatUserService {
	@Autowired
	private TWechatUserRepository wechatUserRepository;
	@Autowired
	private WechatUserMapper wechatUserMapper;

	@Override
	public Page<TWechatUser> setList(HttpServletRequest request, TWechatUser wechatUser) {
		Pageable pageable = PageRequestHelper.buildPageRequest(request, new Sort(Direction.DESC, "updateTime"));
		Specification<TWechatUser> spec = new Specification<TWechatUser>() {
			private static final long serialVersionUID = -4641766676812950422L;

			@Override
			public Predicate toPredicate(Root<TWechatUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (wechatUser != null) {
					List<Predicate> list = new ArrayList<Predicate>();
					if (StringUtil.isNotBlank(wechatUser.getNickname()))list.add(cb.like(root.get("nickname"), "%"+wechatUser.getNickname()+"%"));
//					if (StringUtil.isNotBlank(wechatUser.getWxCity()))list.add(cb.like(root.get("city"), "%"+wechatUser.getWxCity()+"%"));
					if (StringUtil.isNotBlank(wechatUser.getSex()))list.add(cb.equal(root.get("sex"), wechatUser.getSex()));
//					if (StringUtil.isNotBlank(wechatUser.getPhone()))list.add(cb.like(root.get("phone"), "%"+wechatUser.getPhone()+"%"));
					
					if (StringUtil.isNotBlank(wechatUser.getName()))list.add(cb.like(root.get("name"), "%"+wechatUser.getName()+"%"));
					
//					list.add(cb.equal(root.get("isDell"), 0));
					Predicate[] p2 = new Predicate[list.size()];
					query.where(cb.and(list.toArray(p2)));
					return query.getRestriction();
				}
				return null;
			}
		};
		return wechatUserRepository.findAll(spec, pageable);
	}

	@Override
	public List<Map<String, Object>> newUsersPageList(Map<String, Object> paramsMap) {
		return wechatUserMapper.newUsersPageList(paramsMap);
	}

	@Override
	public long newUsersCount(Map<String, Object> paramsMap) {
		return wechatUserMapper.newUsersCount(paramsMap);
	}

	@Override
	public List<Map<?, ?>> newUsersEchartsSex(String startDate, String endDate) {
		return wechatUserMapper.newUsersEchartsSex(startDate,endDate);
	}

	@Override
	public List<Map<?, ?>> newUsersEchartsCityTop10(String startDate, String endDate) {
		return wechatUserMapper.newUsersEchartsCityTop10(startDate,endDate);
	}

	@Override
	public List<Map<?, ?>> newUsersEchartsRh30() {
		return wechatUserMapper.newUsersEchartsRh30();
	}

	@Override
	public List<Map<?, ?>> newUsersEchartsXz30() {
		return wechatUserMapper.newUsersEchartsXz30();
	}

	@Override
	public List<Map<String, Object>> newUsersExportList(String startDate,String endDate) {
		return wechatUserMapper.newUsersExportList(startDate,endDate);
	}

	@Override
	public BaseMessage<?> editPublish(String id, Integer hasPublish) {
		wechatUserRepository.editPublish(id,hasPublish);
		return MessageHandler.createSuccessVo("操作成功");
	}

	@Override
	public BaseMessage<?> save(TWechatUser data) {
		if(StringUtil.isNotBlank(data.getId())) {
			TWechatUser oldData = wechatUserRepository.getOne(data.getId());
			JpaUtil.copyNotNullProperties(oldData, data);
			//
//			data.setMoney(oldData.getMoney());
		}else {
//			Integer count = wechatUserRepository.countByPhone(data.getPhone());
//			if(count>0) {
//				return MessageHandler.createFailedVo("操作失败，手机号已存在");
//			}
			//出事化数据
//			data.setPwd("123456");
		}
		wechatUserRepository.save(data);
		return MessageHandler.createSuccessVo("操作成功");
	}

	@Override
	public BaseMessage<?> dell(String id) {
		wechatUserRepository.dell(id);
		return MessageHandler.createSuccessVo("操作成功");
	}

	@Override
	public TWechatUser detail(String id) {
		return wechatUserRepository.getOne(id);
	}

	@Override
	public List<TWechatUser> alllist(HttpServletRequest request, TWechatUser wechatUser) {
		return wechatUserRepository.alllist();
	}

	@Override
	public BaseMessage<?> open(String id) {
		wechatUserRepository.open(id);
		return MessageHandler.createSuccessVo("操作成功");
	}

	@Override
	public void reset(String id) throws Exception{
		TWechatUser oldData = wechatUserRepository.getOne(id);
//		oldData.setPwd("123456");
		wechatUserRepository.save(oldData);
	}

}
