package com.boot.service.impl;

import com.boot.entity.PartTime;
import com.boot.mapper.PartTimeDao;
import com.boot.repository.PartTimeRepository;
import com.boot.repository.PartTimeRepository;
import com.boot.service.PartTimeService;
import com.boot.service.PartTimeService;
import com.boot.util.PageRequestHelper;
import com.boot.util.StringUtil;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PartTimeServiceImpl implements PartTimeService {
	@Autowired
	private PartTimeRepository dataRepository;
	@Autowired
	private PartTimeDao partTimeDao;
	/**
	 *后台列表展示
	 */
	@Override
	public Page<PartTime> list(HttpServletRequest request, PartTime data) {
		Pageable pageable = PageRequestHelper.buildPageRequest(request, new Sort(Direction.DESC, "createTime"));
		Specification<PartTime> spec = new Specification<PartTime>() {
			private static final long serialVersionUID = 4925289609016132502L;
			@Override
			public Predicate toPredicate(Root<PartTime> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (data != null) {
					List<Predicate> list = new ArrayList<Predicate>();
					if (StringUtil.isNotBlank(data.getName()))
						list.add(cb.like(root.get("name"), "%" + data.getName() + "%"));
					Predicate[] p2 = new Predicate[list.size()];
					query.where(cb.and(list.toArray(p2)));
					return query.getRestriction();
				}
				return null;
			}
		};
		Page<PartTime> pageList = dataRepository.findAll(spec, pageable);
		return pageList;
	}

	@Override
	public PartTime findOneById(String id) {
		PartTime data = dataRepository.getOne(id);
		return data;
	}

	/**
	 * 根据主键修改
	 */
	@Override
	public BaseMessage<?> save(PartTime data) {
		return MessageHandler.createSuccessVo(dataRepository.save(data), "保存成功");
	}
	
	@Override
	@Transactional
	public BaseMessage<?> delete(String id) {
		dataRepository.deleteById(id);
		return MessageHandler.createSuccessVo("删除成功");
	}

	@Override
	public List<PartTime> pagelist(PartTime data) {
		return partTimeDao.pagelist(data);
	}

	@Override
	public int countDate(Date date) {
		return partTimeDao.countDate(date);
	}

}
