package com.boot.api;

import com.boot.controller.BaseController;
import com.boot.entity.Set;
import com.boot.service.SetService;
import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/set")
@Api(value = "设置接口", tags = "设置接口")
public class ApiSetController extends BaseController {

    @Autowired
    private SetService dataService;

    @ResponseBody
    @RequestMapping("/getLaster")
    public BaseMessage<?> getLaster(HttpServletRequest request, ModelMap map, Set data) {
        Set set = dataService.getLaster();
        return MessageHandler.createSuccessVo(set,"操作成功");
    }
}
