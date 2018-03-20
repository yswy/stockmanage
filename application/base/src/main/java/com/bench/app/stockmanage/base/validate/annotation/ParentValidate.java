package com.bench.app.stockmanage.base.validate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bench.app.stockmanage.base.EnumBase;

/**
 * 
 * <p>
 *  验证，表明需要从父类里去获取验证规则
 * </p>
 * @author chenbug
 * @version $Id: Validate.java,v 0.1 2009-9-8 下午04:23:15 chenbug Exp $
 */
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ParentValidate {
    /**
     *  错误代码
     * @return
     */
    String errorCode() default "";

    /**
     * 错误枚举类
     * @return
     */
    Class<? extends EnumBase> errorEnumClass() default EnumBase.class;

}
