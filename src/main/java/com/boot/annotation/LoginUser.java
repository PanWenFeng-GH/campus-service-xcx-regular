package com.boot.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE,ElementType.METHOD})
@Documented
@Inherited
public @interface LoginUser {

    String value() default "";
}
