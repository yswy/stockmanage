/**
 * 
 */
package com.bench.app.stockmanage.base.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 *  类别名
 * </p>
 * @author chenbug
 * @version $Id: FieldAlias.java,v 0.1 2009-9-8 下午01:35:34 chenbug Exp $
 */
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassAnno {

    /**
     * 别名值
     * @return
     */
    public String alias();

    /**
     * 别名父类
     * @return
     */
    public String aliasParentClass() default "";

    /**
     * 验证父类
     * @return
     */
    public String validateParentClass() default "";

}
