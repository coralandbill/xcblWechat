package com.imory.cn.annotation;


import com.imory.cn.common.model.OrgCompany;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义SessionCheck注解，用于进行session检查
 *
 * @author xx.liu
 * @version 1.0
 */
@Target( ElementType.METHOD )
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionCheck
{
    String value() default OrgCompany.USER_COMPANY;
}
