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
 *  别名域
 * </p>
 * @author chenbug
 * @version $Id: FieldAlias.java,v 0.1 2009-9-8 下午01:35:34 chenbug Exp $
 */
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAnno {

    /**
     * 别名
     * @return
     */
    public String alias() default "";

    /**
     * 描述
     * @return
     */
    public String describe() default "";

}
