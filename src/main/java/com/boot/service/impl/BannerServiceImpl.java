package com.boot.service.impl;

import com.boot.entity.Banner;
import com.boot.repository.BannerRepository;
import com.boot.service.BannerService;
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
public class BannerServiceImpl implements BannerService {
	@Autowired
	private BannerRepository bannerRepository;

	/**
	 *后台列表展示
	 */
	@Override
	public Page<Banner> list(HttpServletRequest request, Banner banner) {
		Pageable pageable = PageRequestHelper.buildPageRequest(request, new Sort(Direction.ASC, "sort"));
		Specification<Banner> spec = new Specification<Banner>() {
			private static final long serialVersionUID = 4925289609016132502L;

			@Override
			public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (banner != null) {
					List<Predicate> list = new ArrayList<Predicate>();
					if (StringUtil.isNotBlank(banner.getName()))
						list.add(cb.like(root.get("name"), "%" + banner.getName() + "%"));
					Predicate[] p2 = new Predicate[list.size()];
					query.where(cb.and(list.toArray(p2)));
					return query.getRestriction();
				}
				return null;
			}
		};
		Page<Banner> pageList = bannerRepository.findAll(spec, pageable);
		return pageList;
	}

	@Override
	public Banner findOneById(String id) {
		Banner banner = bannerRepository.getOne(id);
		return banner;
	}

	/**
	 * 根据主键修改
	 */
	@Override
	public BaseMessage<?> save(Banner banner) {
		return MessageHandler.createSuccessVo(bannerRepository.save(banner), "保存成功");
	}

	/**
	 * 通过 类型查找banner
	* @author lishanyun
	* @date 2019年10月22日
	 */
	@Override
	public List<Banner> findByType(String type) {
		List<Banner> list = bannerRepository.findByType(type);
		return list;
	}

	
	@Override
	@Transactional
	public BaseMessage<?> delete(String id) {
		bannerRepository.deleteById(id);
		return MessageHandler.createSuccessVo("删除成功");
	}

}
