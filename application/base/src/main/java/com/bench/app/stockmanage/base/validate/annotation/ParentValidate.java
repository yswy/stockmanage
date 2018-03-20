package com.bench.app.stockmanage.base.validate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bench.app.stockmanage.base.EnumBase;

/**
 * 
 * <p>
 *  ��֤��������Ҫ�Ӹ�����ȥ��ȡ��֤����
 * </p>
 * @author chenbug
 * @version $Id: Validate.java,v 0.1 2009-9-8 ����04:23:15 chenbug Exp $
 */
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ParentValidate {
    /**
     *  �������
     * @return
     */
    String errorCode() default "";

    /**
     * ����ö����
     * @return
     */
    Class<? extends EnumBase> errorEnumClass() default EnumBase.class;

}
