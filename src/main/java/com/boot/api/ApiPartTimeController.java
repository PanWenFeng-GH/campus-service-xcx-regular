package com.boot.api;

import com.boot.annotation.LoginUser;
import com.boot.controller.BaseController;
import com.boot.entity.PartTime;
import com.boot.entity.TWechatUser;
import com.boot.service.PartTimeService;
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

/**
 * 兼职控制器
 */
@Controller
@RequestMapping("/api/partTime")
public class ApiPartTimeController extends BaseController {

    @Autowired
    private PartTimeService dataService;
    @Autowired
    private WechatUserService wechatUserService;

    /**
     * 列表展示
     */
    @ResponseBody
    @RequestMapping("/list")
    public BaseMessage<?> list(HttpServletRequest request, ModelMap map, PartTime data) {
        Pageable pageable = PageRequestHelper.buildPageRequest(request, null);
        Page<PartTime> page = PageHelper.startPage(pageable.getPageNumber()+1, pageable.getPageSize());
        data.setState(3);
        List<PartTime> list = dataService.pagelist(data);
        return MessageHandler.createSuccessVo(list,"操作成功",(int)page.getTotal());
    }

    /**
     * 列表展示
     */
    @ResponseBody
    @RequestMapping("/listMy")
    @LoginUser
    public BaseMessage<?> listMy(HttpServletRequest request, ModelMap map, PartTime data) {
        Pageable pageable = PageRequestHelper.buildPageRequest(request, null);
        Page<PartTime> page = PageHelper.startPage(pageable.getPageNumber()+1, pageable.getPageSize());
        String userId = JwtTokenUtil.getUserId();
        data.setWechatId(userId);
        List<PartTime> list = dataService.pagelist(data);
        return MessageHandler.createSuccessVo(list,"操作成功",(int)page.getTotal());
    }

    /**
     * 编辑弹框显示
     */
    @ResponseBody
	@RequestMapping("detail")
    public BaseMessage<?> edit(String id, ModelMap map) {
        PartTime data = dataService.findOneById(id);
        //
        if(data!=null&& StringUtil.isNotBlank(data.getWechatId())){
            TWechatUser wechatUser = wechatUserService.detail(data.getWechatId());
            data.setWechatUser(wechatUser);
        }
		return MessageHandler.createSuccessVo(data, "操作成功!");
    }

    @RequestMapping("/save")
    @ResponseBody
    @LoginUser
    public BaseMessage<?> save(PartTime d) {
        String userId = JwtTokenUtil.getUserId();
        d.setWechatId(userId);
        d.setState(1);
        return dataService.save(d);
    }

    /**
     * 删除
     */
    @RequestMapping("/dell")
    @ResponseBody
    @LoginUser
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
