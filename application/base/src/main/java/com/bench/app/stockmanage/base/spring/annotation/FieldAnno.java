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
 *  ������
 * </p>
 * @author chenbug
 * @version $Id: FieldAlias.java,v 0.1 2009-9-8 ����01:35:34 chenbug Exp $
 */
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAnno {

    /**
     * ����
     * @return
     */
    public String alias() default "";

    /**
     * ����
     * @return
     */
    public String describe() default "";

}
