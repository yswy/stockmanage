/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.spring.annotation.processor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import com.bench.app.stockmanage.base.model.Null;
import com.bench.app.stockmanage.base.model.NullAnnotation;
import com.bench.app.stockmanage.base.spring.annotation.BeanList;
import com.bench.app.stockmanage.base.utils.FieldUtils;
import com.bench.app.stockmanage.base.utils.ListUtils;

/**
 * {@link OsgiReference}的{@link BeanPostProcessor}实现。用来处理{@link OsgiReference}注解
 * 
 * <p>
 * TODO 方法级别的注入。
 * <p>
 * TODO required属性的支持。
 * 
 * @author chenbug
 * @version $Id: OsgiReferenceAnnotationBeanPostProcessor.java,v 0.1 2009-2-23
 *          下午04:50:54 chenbug Exp $
 */
@Component
public class BeanListAnnotationBeanPostProcessor implements BeanPostProcessor {

	private ConfigurableListableBeanFactory beanFactory;

	public BeanListAnnotationBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#
	 * postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		return bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#
	 * postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		for (Field field : FieldUtils.getAllField(bean.getClass())) {
			BeanList beanList = field.getAnnotation(BeanList.class);
			if (beanList == null) {
				continue;
			}
			Class<?> beanClass = beanList.value();
			if (beanClass == Null.class) {
				beanClass = FieldUtils.getFieldParameterizedClass(field).get(0);
			}
			Map<String, ?> beanInstanceMap = this.beanFactory.getBeansOfType(beanClass);
			Set<Object> matchedBeanList = new HashSet<Object>();
			for (Object beanInstance : beanInstanceMap.values()) {
				if (beanList.withAnnotation() == NullAnnotation.class || beanClass.getAnnotation(beanList.withAnnotation()) != null) {
					matchedBeanList.add(beanInstance);
				}
			}
			List<Object> fieldValue = (List<Object>) FieldUtils.getFieldValue(bean, field.getName());
			if (fieldValue == null) {
				FieldUtils.setFieldValue(bean, field.getName(), new ArrayList<Object>(matchedBeanList));
			} else {
				ListUtils.addUnequalAll(new ArrayList<Object>(matchedBeanList), fieldValue);
			}
		}
		return bean;
	}

}
