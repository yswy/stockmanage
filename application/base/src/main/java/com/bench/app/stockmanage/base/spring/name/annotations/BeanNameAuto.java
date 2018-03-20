package com.bench.app.stockmanage.base.spring.name.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * bean名称自动，根据接口和类的关系，取接口的命名方式<br>
 * ， 接口是Test,实现类是TestImpl，则bean名称是test<br>
 * ， 接口是Test,实现类是DefaultTest，则bean名称是test <br>
 * 适用于1个接口只有一个实现类的情况
 * 
 * @author chenbug
 *
 * @version $Id: BeanNameRandom.java, v 0.1 2018年3月7日 上午11:50:30 chenbug Exp $
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanNameAuto {

}
