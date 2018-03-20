/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.spring.annotation.processor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.bench.app.stockmanage.base.model.Null;
import com.bench.app.stockmanage.base.spring.annotation.BeanMap;
import com.bench.app.stockmanage.base.utils.FieldUtils;

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

public class BeanMapAnnotationBeanPostProcessor implements BeanPostProcessor {

	private ConfigurableListableBeanFactory beanFactory;

	public BeanMapAnnotationBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
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
			BeanMap beanMapAnno = field.getAnnotation(BeanMap.class);
			if (beanMapAnno == null) {
				continue;
			}
			Class<?> beanClass = beanMapAnno.beanClass();
			if (beanClass == Null.class) {
				beanClass = (Class<?>) FieldUtils.getFieldParameterizedType(field)[1];
			}
			Map<String, ?> beanInstanceMap = this.beanFactory.getBeansOfType(beanMapAnno.beanClass());
			Map<Object, Object> propertyValueMap = (Map<Object, Object>) FieldUtils.getFieldValue(bean, field.getName());
			if (propertyValueMap == null) {
				propertyValueMap = new HashMap<Object, Object>();
				FieldUtils.setFieldValue(bean, field.getName(), propertyValueMap);
			}
			for (Object beanInstance : beanInstanceMap.values()) {
				try {
					propertyValueMap.put(MethodUtils.invokeMethod(beanInstance, beanMapAnno.getKeyMethod(), null), beanInstance);
				} catch (Exception e) {
					throw new RuntimeException("调用方法获取BeanMap的key值异常,beanMapAnno=" + beanMapAnno + ",bean=" + bean, e);
				}
			}

		}
		return bean;
	}

}
