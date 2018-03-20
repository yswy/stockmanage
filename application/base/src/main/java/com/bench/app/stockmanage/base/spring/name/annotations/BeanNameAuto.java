package com.bench.app.stockmanage.base.spring.name.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * bean�����Զ������ݽӿں���Ĺ�ϵ��ȡ�ӿڵ�������ʽ<br>
 * �� �ӿ���Test,ʵ������TestImpl����bean������test<br>
 * �� �ӿ���Test,ʵ������DefaultTest����bean������test <br>
 * ������1���ӿ�ֻ��һ��ʵ��������
 * 
 * @author chenbug
 *
 * @version $Id: BeanNameRandom.java, v 0.1 2018��3��7�� ����11:50:30 chenbug Exp $
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanNameAuto {

}
