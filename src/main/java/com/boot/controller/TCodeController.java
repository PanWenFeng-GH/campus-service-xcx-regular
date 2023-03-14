package com.boot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.entity.TCode;
import com.boot.repository.TCodeRepository;
import com.boot.util.PageRequestHelper;
import com.boot.util.StringUtil;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;

@Controller
@RequestMapping("/private/code")
public class TCodeController extends BaseController {

	@Autowired
	private TCodeRepository codeRepository;
	
	@ResponseBody
	@RequestMapping("/list")
	public  BaseMessage<?>list(TCode data, HttpServletRequest request, HttpServletResponse response) {
		try {
			Pageable pageable = PageRequestHelper.buildPageRequest(request, new Sort(Direction.DESC,"createTime"));
			Specification<TCode> spec = new Specification<TCode>() {
				private static final long serialVersionUID = 3348042767886904924L;
				@Override
				public Predicate toPredicate(Root<TCode> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> list = new ArrayList<Predicate>();
					if (data!=null&&StringUtil.isNotBlank(data.getName())) {
						list.add(cb.like(root.get("name"), "%" + data.getName() + "%"));
					}
					if (data!=null&&data.getType() != null) {
						list.add(cb.equal(root.get("type"), data.getType()));
					}
					Predicate[] p2 = new Predicate[list.size()];
					query.where(cb.and(list.toArray(p2)));
					return query.getRestriction();
				}
			};
			Page<TCode> pageList = codeRepository.findAll(spec, pageable);
			return MessageHandler.createSuccessVo(pageList.getContent(),"操作成功",
					(int) pageList.getTotalElements());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
	
	
	@ResponseBody
	@RequestMapping("/save")
	public  BaseMessage<?> save(TCode u, HttpServletRequest request, HttpServletResponse response) {
		try {
			codeRepository.save(u);
			return MessageHandler.createSuccessVo("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
	@ResponseBody
	@RequestMapping("/detail")
	public  BaseMessage<?> detail(String id, HttpServletRequest request, HttpServletResponse response) {
		try {
			TCode d = codeRepository.getOne(id);
			return MessageHandler.createSuccessVo(d,"操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
	@ResponseBody
	@RequestMapping("/dell")
	public  BaseMessage<?> dell(String id, HttpServletRequest request, HttpServletResponse response) {
		try {
			codeRepository.deleteById(id);
			return MessageHandler.createSuccessVo("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageHandler.createFailedVo("操作失败");
	}
}
