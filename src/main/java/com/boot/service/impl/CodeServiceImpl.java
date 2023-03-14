package com.boot.service.impl;

import com.boot.entity.TCode;
import com.boot.mapper.CodeDao;
import com.boot.repository.TCodeRepository;
import com.boot.service.CodeService;
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
import java.util.List;

@Service
public class CodeServiceImpl implements CodeService {
	@Autowired
	private TCodeRepository dataRepository;
	@Autowired
	private CodeDao dataDao;
	/**
	 *后台列表展示
	 */
	@Override
	public Page<TCode> list(HttpServletRequest request, TCode data) {
		Pageable pageable = PageRequestHelper.buildPageRequest(request, new Sort(Direction.DESC, "createTime"));
		Specification<TCode> spec = new Specification<TCode>() {
			private static final long serialVersionUID = 4925289609016132502L;
			@Override
			public Predicate toPredicate(Root<TCode> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
		Page<TCode> pageList = dataRepository.findAll(spec, pageable);
		return pageList;
	}

	@Override
	public TCode findOneById(String id) {
		TCode data = dataRepository.getOne(id);
		return data;
	}

	/**
	 * 根据主键修改
	 */
	@Override
	public BaseMessage<?> save(TCode data) {
		return MessageHandler.createSuccessVo(dataRepository.save(data), "保存成功");
	}
	
	@Override
	@Transactional
	public BaseMessage<?> delete(String id) {
		dataRepository.deleteById(id);
		return MessageHandler.createSuccessVo("删除成功");
	}

	@Override
	public List<TCode> pagelist(TCode data) {
		return dataDao.pagelist(data);
	}

}
