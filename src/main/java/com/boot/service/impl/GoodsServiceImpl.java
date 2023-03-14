package com.boot.service.impl;

import com.boot.entity.Goods;
import com.boot.mapper.GoodsDao;
import com.boot.repository.GoodsRepository;
import com.boot.repository.GoodsRepository;
import com.boot.service.GoodsService;
import com.boot.service.GoodsService;
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
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsRepository dataRepository;
    @Autowired
    private GoodsDao dataDao;

    /**
     * 后台列表展示
     */
    @Override
    public Page<Goods> list(HttpServletRequest request, Goods data) {
        Pageable pageable = PageRequestHelper.buildPageRequest(request, new Sort(Direction.DESC, "createTime"));
        Specification<Goods> spec = new Specification<Goods>() {
            private static final long serialVersionUID = 4925289609016132502L;

            @Override
            public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
        Page<Goods> pageList = dataRepository.findAll(spec, pageable);
        return pageList;
    }

    @Override
    public Goods findOneById(String id) {
        Goods data = dataRepository.getOne(id);
        return data;
    }

    /**
     * 根据主键修改
     */
    @Override
    public BaseMessage<?> save(Goods data) {
        return MessageHandler.createSuccessVo(dataRepository.save(data), "保存成功");
    }

    @Override
    @Transactional
    public BaseMessage<?> delete(String id) {
        dataRepository.deleteById(id);
        return MessageHandler.createSuccessVo("删除成功");
    }

    @Override
    public List<Goods> pagelist(Goods data) {
        return dataDao.pagelist(data);
    }

}
