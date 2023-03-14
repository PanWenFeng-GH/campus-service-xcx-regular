package com.boot.controller;

import com.boot.entity.Set;
import com.boot.entity.TWechatUser;
import com.boot.service.SetService;
import com.boot.util.StringUtil;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
import org.springframework.data.domain.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/private/set")
public class CSetController extends BaseController {

    @Autowired
    private SetService dataService;

    /**
     * 列表展示
     */
    @ResponseBody
    @RequestMapping("/list")
    public BaseMessage<?> list(HttpServletRequest request, ModelMap map, Set data) {
        Page<Set> pageList = dataService.list(request, data);
        return MessageHandler.createSuccessVo(pageList.getContent(),"操作成功",
                (int) pageList.getTotalElements());
    }

    @ResponseBody
    @RequestMapping("/getLaster")
    public BaseMessage<?> getLaster(HttpServletRequest request, ModelMap map, Set data) {
        Set set = dataService.getLaster();
        return MessageHandler.createSuccessVo(set,"操作成功");
    }

    /**
     * 编辑弹框显示
     */
    @ResponseBody
	@RequestMapping("detail")
    public BaseMessage<?> edit(String id, ModelMap map) {
        Set data = dataService.findOneById(id);
		return MessageHandler.createSuccessVo(data, "操作成功!");
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public BaseMessage<?> save(Set d) {
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
