package com.boot.service.impl;

import com.boot.entity.BBS;
import com.boot.mapper.BBSDao;
import com.boot.repository.BBSRepository;
import com.boot.service.BBSService;
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
public class BBSServiceImpl implements BBSService {
	@Autowired
	private BBSRepository bbsRepository;
	@Autowired
	private BBSDao bbsDao;

	/**
	 *后台列表展示
	 */
	@Override
	public Page<BBS> list(HttpServletRequest request, BBS data) {
		Pageable pageable = PageRequestHelper.buildPageRequest(request, new Sort(Direction.DESC, "createTime"));
		Specification<BBS> spec = new Specification<BBS>() {
			private static final long serialVersionUID = 4925289609016132502L;
			@Override
			public Predicate toPredicate(Root<BBS> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
		Page<BBS> pageList = bbsRepository.findAll(spec, pageable);
		return pageList;
	}

	@Override
	public BBS findOneById(String id) {
		BBS data = bbsRepository.getOne(id);
		return data;
	}

	/**
	 * 根据主键修改
	 */
	@Override
	public BaseMessage<?> save(BBS data) {
		return MessageHandler.createSuccessVo(bbsRepository.save(data), "保存成功");
	}
	
	@Override
	@Transactional
	public BaseMessage<?> delete(String id) {
		bbsRepository.deleteById(id);
		return MessageHandler.createSuccessVo("删除成功");
	}

	@Override
	public List<BBS> pagelist(BBS data) {
		return bbsDao.pagelist(data);
	}

	@Override
	public int countDate(Date date) {
		return bbsDao.countDate(date);
	}

}
