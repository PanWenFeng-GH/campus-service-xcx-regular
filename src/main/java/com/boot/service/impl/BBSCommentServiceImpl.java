package com.boot.service.impl;

import com.boot.entity.BBSComment;
import com.boot.mapper.BBSCommentDao;
import com.boot.repository.BBSCommentRepository;
import com.boot.service.BBSCommentService;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class BBSCommentServiceImpl implements BBSCommentService {
	@Autowired
	private BBSCommentRepository dataRepository;
	@Autowired
	private BBSCommentDao dataDao;
	/**
	 *后台列表展示
	 */
	@Override
	public Page<BBSComment> list(HttpServletRequest request, BBSComment data) {
		Pageable pageable = PageRequestHelper.buildPageRequest(request, new Sort(Direction.DESC, "createTime"));
		Specification<BBSComment> spec = new Specification<BBSComment>() {
			private static final long serialVersionUID = 4925289609016132502L;
			@Override
			public Predicate toPredicate(Root<BBSComment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (data != null) {
					List<Predicate> list = new ArrayList<Predicate>();
					Predicate[] p2 = new Predicate[list.size()];
					query.where(cb.and(list.toArray(p2)));
					return query.getRestriction();
				}
				return null;
			}
		};
		Page<BBSComment> pageList = dataRepository.findAll(spec, pageable);
		return pageList;
	}

	@Override
	public BBSComment findOneById(String id) {
		BBSComment data = dataRepository.getOne(id);
		return data;
	}

	/**
	 * 根据主键修改
	 */
	@Override
	public BaseMessage<?> save(BBSComment data) {
		return MessageHandler.createSuccessVo(dataRepository.save(data), "操作成功");
	}
	
	@Override
	@Transactional
	public BaseMessage<?> delete(String id) {
		dataRepository.deleteById(id);
		return MessageHandler.createSuccessVo("删除成功");
	}

	@Override
	public List<BBSComment> pagelist(BBSComment data) {
		return dataDao.pagelist(data);
	}

}
