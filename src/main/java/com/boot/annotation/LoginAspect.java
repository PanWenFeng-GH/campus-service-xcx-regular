package com.boot.annotation;

import com.boot.util.JwtTokenUtil;
import com.boot.util.StringUtil;
import com.boot.vo.MessageHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAspect {

    @Pointcut(value = "@annotation(com.boot.annotation.LoginUser)")
    public void access() {

    }

    @Before("access()")
    public void before() {
    }

    @Around("access()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
        try {
            String userId = JwtTokenUtil.getUserId();
            if(StringUtil.isBlank(userId)){
                throw new Exception("请重新登录");
            }
            Object o =  proceedingJoinPoint.proceed();
            return o;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new Exception("请重新登录");
        }
    }
}