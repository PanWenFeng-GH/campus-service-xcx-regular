package com.boot.controller;

import com.boot.entity.PartTime;
import com.boot.entity.TWechatUser;
import com.boot.service.PartTimeService;
import com.boot.service.PartTimeService;
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
 * 兼职控制器
 */
@Controller
@RequestMapping("/private/partTime")
public class CPartTimeController extends BaseController {

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
        Page<PartTime> page = PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
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
    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public BaseMessage<?> save(PartTime d) {
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
    @RequestMapping("/stateOp")
    @ResponseBody
    public BaseMessage<?> stateOp(String id,Integer state) {
        try {
            PartTime data = dataService.findOneById(id);
            data.setState(state);
            dataService.save(data);
            return MessageHandler.createSuccessVo("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MessageHandler.createFailedVo("操作失败");
    }

}
