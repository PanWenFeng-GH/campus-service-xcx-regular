package com.boot.controller;

import com.boot.entity.Goods;
import com.boot.service.GoodsService;
import com.boot.util.PageRequestHelper;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 控制器
 */
@Controller
@RequestMapping("/private/goods")
public class CGoodsController extends BaseController {

    @Autowired
    private GoodsService dataService;

    /**
     * 列表展示
     */
    @ResponseBody
    @RequestMapping("/list")
    public BaseMessage<?> list(HttpServletRequest request, ModelMap map, Goods data) {
        Pageable pageable = PageRequestHelper.buildPageRequest(request, null);
        Page<Goods> page = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<Goods> list = dataService.pagelist(data);
        return MessageHandler.createSuccessVo(list,"操作成功",(int)page.getTotal());
    }

    /**
     * 编辑弹框显示
     */
    @ResponseBody
	@RequestMapping("detail")
    public BaseMessage<?> edit(String id, ModelMap map) {
        Goods data = dataService.findOneById(id);
		return MessageHandler.createSuccessVo(data, "操作成功!");
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public BaseMessage<?> save(Goods d) {
        return dataService.save(d);
    }

    /**
     * 删除
     */
    @RequestMapping("/dell")
    @ResponseBody
    public BaseMessage<?> dell(String id) {
        try {
            dataService.delete(id);
            return MessageHandler.createSuccessVo("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MessageHandler.createFailedVo("操作失败");
    }

}
