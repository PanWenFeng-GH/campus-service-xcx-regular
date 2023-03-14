package com.boot.api;

import com.boot.controller.BaseController;
import com.boot.entity.Banner;
import com.boot.service.BannerService;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/banner")
public class ApiBannerController extends BaseController {

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

}
