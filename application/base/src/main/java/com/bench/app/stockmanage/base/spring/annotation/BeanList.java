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
 * Bean�б�
 * 
 * @author chenbug
 *
 * @version $Id: BeanList.java, v 0.1 2016��6��20�� ����8:59:31 chenbug Exp $
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanList {

	/**
	 * bean��
	 * 
	 * @return
	 */
	Class<?> value() default Null.class;

	/**
	 * ����ע��
	 * 
	 * @return
	 */
	Class<? extends Annotation> withAnnotation() default NullAnnotation.class;
}
