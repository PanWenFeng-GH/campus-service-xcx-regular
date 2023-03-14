package com.boot.controller;

import com.boot.entity.BBS;
import com.boot.service.BBSService;
import com.boot.service.PartTimeService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/private/home")
public class HomeController extends BaseController {

    @Autowired
    private PartTimeService dataService;
    @Autowired
    private BBSService bbsService;

    @ResponseBody
    @RequestMapping("/index")
    public BaseMessage<?> index(HttpServletRequest request, ModelMap map, BBS data) {
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("partTime",dataService.countDate(new Date()));
        dataMap.put("bbs",bbsService.countDate(new Date()));
        return MessageHandler.createSuccessVo(dataMap,"操作成功");
    }
}
