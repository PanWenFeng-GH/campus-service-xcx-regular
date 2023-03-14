package com.boot.controller;

import com.boot.entity.Banner;
import com.boot.service.BannerService;
import com.boot.util.StringUtil;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/private/banner")
public class CBannerController extends BaseController {

    @Autowired
    private BannerService bannerService;

    /**
     * 列表展示
     */
    @ResponseBody
    @RequestMapping("/list")
    public BaseMessage<?> list(HttpServletRequest request, ModelMap map, Banner banner) {
        Page<Banner> pageList = bannerService.list(request, banner);
        return MessageHandler.createSuccessVo(pageList.getContent(),"操作成功",
                (int) pageList.getTotalElements());
    }

    /**
     * 编辑弹框显示
     */
    @ResponseBody
	@RequestMapping("detail")
    public BaseMessage<?> edit(String id, ModelMap map) {
        Banner banner = bannerService.findOneById(id);
		return MessageHandler.createSuccessVo(banner, "操作成功!");
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public BaseMessage<?> save(Banner banner) {
        return bannerService.save(banner);
    }

    /**
     * 删除
     */
    @RequestMapping("/dell")
    @ResponseBody
    public BaseMessage<?> dell(String id) {
        try {
            bannerService.delete(id);
            return MessageHandler.createSuccessVo("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MessageHandler.createFailedVo("操作失败");
    }

}
