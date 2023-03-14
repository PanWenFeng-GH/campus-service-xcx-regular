package com.boot.controller;

import com.boot.entity.Feedback;
import com.boot.entity.TWechatUser;
import com.boot.service.FeedbackService;
import com.boot.service.WechatUserService;
import com.boot.util.PageRequestHelper;
import com.boot.util.StringUtil;
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
@RequestMapping("/private/feedback")
public class CFeedbackController extends BaseController {

    @Autowired
    private FeedbackService dataService;
    @Autowired
    private WechatUserService wechatUserService;

    /**
     * 列表展示
     */
    @ResponseBody
    @RequestMapping("/list")
    public BaseMessage<?> list(HttpServletRequest request, ModelMap map, Feedback data) {
        Pageable pageable = PageRequestHelper.buildPageRequest(request, null);
        Page<Feedback> page = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<Feedback> list = dataService.pagelist(data);
        return MessageHandler.createSuccessVo(list,"操作成功",(int)page.getTotal());
    }

    /**
     * 编辑弹框显示
     */
    @ResponseBody
	@RequestMapping("detail")
    public BaseMessage<?> edit(String id, ModelMap map) {
        Feedback bbs = dataService.findOneById(id);
        //
        if(bbs!=null&& StringUtil.isNotBlank(bbs.getWechatId())){
            TWechatUser wechatUser = wechatUserService.detail(bbs.getWechatId());
            bbs.setWechatUser(wechatUser);
        }
		return MessageHandler.createSuccessVo(bbs, "操作成功!");
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public BaseMessage<?> save(Feedback d) {
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
