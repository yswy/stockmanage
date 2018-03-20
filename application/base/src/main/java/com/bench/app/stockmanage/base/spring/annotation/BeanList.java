/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.spring.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bench.app.stockmanage.base.model.Null;
import com.bench.app.stockmanage.base.model.NullAnnotation;

/**
 * Bean列表
 * 
 * @author chenbug
 *
 * @version $Id: BeanList.java, v 0.1 2016年6月20日 下午8:59:31 chenbug Exp $
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanList {

	/**
	 * bean类
	 * 
	 * @return
	 */
	Class<?> value() default Null.class;

	/**
	 * 附带注解
	 * 
	 * @return
	 */
	Class<? extends Annotation> withAnnotation() default NullAnnotation.class;
}
