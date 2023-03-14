package com.boot.api;

import com.boot.annotation.LoginUser;
import com.boot.controller.BaseController;
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
@RequestMapping("/api/bbs")
public class ApiBBSController extends BaseController {

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
        Page<BBS> page = PageHelper.startPage(pageable.getPageNumber()+1, pageable.getPageSize());
        List<BBS> list = bbsService.pagelist(data);
        return MessageHandler.createSuccessVo(list,"操作成功",(int)page.getTotal());
    }
    /**
     * 列表展示
     */
    @ResponseBody
    @RequestMapping("/listMy")
    @LoginUser
    public BaseMessage<?> listMy(HttpServletRequest request, ModelMap map, BBS data) {
        Pageable pageable = PageRequestHelper.buildPageRequest(request, null);
        Page<BBS> page = PageHelper.startPage(pageable.getPageNumber()+1, pageable.getPageSize());
        String userId = JwtTokenUtil.getUserId();
        data.setWechatId(userId);
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
    @LoginUser
    public BaseMessage<?> save(BBS d) {
        //判断是否登录
        String userId = JwtTokenUtil.getUserId();
        d.setWechatId(userId);
        return bbsService.save(d);
    }

    /**
     * 删除
     */
    @RequestMapping("/dell")
    @ResponseBody
    @LoginUser
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
    @RequestMapping("/comment/save")
    @LoginUser
    public BaseMessage<?> commentsave(HttpServletRequest request, ModelMap map, BBSComment data) {
        //判断是否登录
        String userId = JwtTokenUtil.getUserId();
        data.setWechatId(userId);
        return bbsCommentService.save(data);
    }
    @ResponseBody
    @RequestMapping("/comment/dell")
    @LoginUser
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
    @LoginUser
    public BaseMessage<?> commentreply(HttpServletRequest request, ModelMap map, BBSComment data) {
        BBSComment d = bbsCommentService.findOneById(data.getId());
        d.setReply(data.getReply());
        return bbsCommentService.save(d);
    }
}
