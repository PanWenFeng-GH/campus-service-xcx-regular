package com.boot;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.vo.BaseMessage;
import com.boot.vo.MessageHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
	    /**
	     * 系统异常处理
	     * @throws Exception
	     */
	    @ExceptionHandler(value = Exception.class)
	    @ResponseBody
	    public BaseMessage<?> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
	    	e.printStackTrace();
	        return MessageHandler.createFailedVo(e.getCause().getMessage()==null?"操作失败，请联系管理员":e.getCause().getMessage());
	    }
}
