package com.boot.service.impl;

import com.boot.entity.Set;
import com.boot.repository.SetRepository;
import com.boot.service.SetService;
import com.boot.util.PageRequestHelper;
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
import java.util.List;

@Service
public class SetServiceImpl implements SetService {
	@Autowired
	private SetRepository dataRepository;
	/**
	 *后台列表展示
	 */
	@Override
	public Page<Set> list(HttpServletRequest request, Set data) {
		Pageable pageable = PageRequestHelper.buildPageRequest(request, new Sort(Direction.DESC, "createTime"));
		Specification<Set> spec = new Specification<Set>() {
			private static final long serialVersionUID = 4925289609016132502L;
			@Override
			public Predicate toPredicate(Root<Set> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return null;
			}
		};
		Page<Set> pageList = dataRepository.findAll(spec, pageable);
		return pageList;
	}

	@Override
	public Set findOneById(String id) {
		Set data = dataRepository.getOne(id);
		return data;
	}

	/**
	 * 根据主键修改
	 */
	@Override
	public BaseMessage<?> save(Set data) {
		return MessageHandler.createSuccessVo(dataRepository.save(data), "保存成功");
	}
	
	@Override
	@Transactional
	public BaseMessage<?> delete(String id) {
		dataRepository.deleteById(id);
		return MessageHandler.createSuccessVo("删除成功");
	}

	@Override
	public List<Set> pagelist(Set data) {
		return null;
	}

	@Override
	public Set getLaster() {
		Set set = null;
		List<Set> list = dataRepository.findAll();
		if(list!=null && list.size()>0){
			set = list.get(0);
		}
		return set;
	}

}
