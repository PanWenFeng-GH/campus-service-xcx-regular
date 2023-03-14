package com.boot.controller;

import com.boot.annotation.LoginUser;
import com.boot.entity.BBS;
import com.boot.entity.BBSComment;
import com.boot.entity.TWechatUser;
import com.boot.service.BBSCommentService;
import com.boot.service.BBSService;
import com.boot.service.WechatUserService;
import com.boot.util.JwtTokenUtil;
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

@Controller
@RequestMapping("/private/bbs")
public class CBBSController extends BaseController {

    @Autowired
    private BBSService bbsService;
    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private BBSCommentService bbsCommentService;

    /**
     * 列表展示
     */
    @ResponseBody
    @RequestMapping("/list")
    public BaseMessage<?> list(HttpServletRequest request, ModelMap map, BBS data) {
        Pageable pageable = PageRequestHelper.buildPageRequest(request, null);
        Page<BBS> page = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<BBS> list = bbsService.pagelist(data);
        return MessageHandler.createSuccessVo(list,"操作成功",(int)page.getTotal());
    }

    /**
     * 编辑弹框显示
     */
    @ResponseBody
	@RequestMapping("detail")
    public BaseMessage<?> edit(String id, ModelMap map) {
        BBS bbs = bbsService.findOneById(id);
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
    public BaseMessage<?> save(BBS d) {
        return bbsService.save(d);
    }

    /**
     * 删除
     */
    @RequestMapping("/dell")
    @ResponseBody
    public BaseMessage<?> dell(String id) {
        try {
            bbsService.delete(id);
            return MessageHandler.createSuccessVo("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MessageHandler.createFailedVo("操作失败");
    }

    /**
     * bbs 评论list
     */
    @ResponseBody
    @RequestMapping("/comment/list")
    public BaseMessage<?> commentlist(HttpServletRequest request, ModelMap map, BBSComment data) {
        List<BBSComment> list = bbsCommentService.pagelist(data);
        return MessageHandler.createSuccessVo(list,"操作成功");
    }

    @ResponseBody
    @RequestMapping("/comment/dell")
    public BaseMessage<?> commentdell(String id) {
        try {
            bbsCommentService.delete(id);
            return MessageHandler.createSuccessVo("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MessageHandler.createFailedVo("操作失败");
    }
    @ResponseBody
    @RequestMapping("/comment/reply")
    public BaseMessage<?> commentreply(HttpServletRequest request, ModelMap map, BBSComment data) {
        BBSComment d = bbsCommentService.findOneById(data.getId());
        d.setReply(data.getReply());
        return bbsCommentService.save(d);
    }
}
