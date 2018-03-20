package com.bench.app.stockmanage.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;

import com.bench.app.stockmanage.base.checker.BenchCheckComponent;
import com.bench.app.stockmanage.base.lifecycle.BenchLifeCycle;
import com.bench.app.stockmanage.base.lifecycle.comparator.BenchLifeCycleComparator;

/**
 * Bench����ʱ
 * 
 * @author chenbug
 *
 * @version $Id: BenchRuntime.java, v 0.1 2015��6��9�� ����5:53:17 chenbug Exp $
 */
@Service
public class BenchRuntime {

	private static final Logger log = Logger.getLogger(BenchRuntime.class);

	private boolean started = false;

	public static final BenchRuntime INSTANCE = new BenchRuntime();

	/**
	 * ���е�Ӧ��
	 */
	private SpringApplication application;

	/**
	 * ���в���
	 */
	private String[] args;

	/**
	 * ע�⣬����ֻ����spring�������������ֵ
	 */
	private ConfigurableApplicationContext context;

	/**
	 * ���û�����Ӧ����������
	 */
	private ConfigurableEnvironment environment;

//	/**
//	 * ֱ�ӹ���ģ������н׶��ռ���Ϣ,����ʹ�þ�̬�ģ��Ա�ֻ��һ��ʵ��
//	 */
//	private ExtensionManager extensionManager = new ExtensionManagerImpl();
//
//	public BenchRuntime() {
//		super();
//		extensionManager = new ExtensionManagerImpl();
//	}
//
//	public ExtensionManager getExtensionManager() {
//		return extensionManager;
//	}
//	
	public static BenchRuntime getInstance() {
		return INSTANCE;
	}

	/**
	 * ��ȡ�������
	 * 
	 * @param name
	 * @return
	 */
	public Object getService(String name) {
		return context.getBean(name);
	}

	public <T> T getService(Class<T> clasz) {
		return context.getBean(clasz);
	}

	public boolean isStarted() {
		return started;
	}

	public static final void error(String errorMessage) {
		error(errorMessage, null);
	}

	public static final void error(String errorMessage, Throwable t) {
		log.error(errorMessage);
		System.err.println(errorMessage);
		throw new RuntimeException(errorMessage, t);
	}

	public void start(ConfigurableApplicationContext context) {
		// TODO Auto-generated method stub
		// �����
		BenchCheckComponent benchCheckComponent = this.context.getBean(BenchCheckComponent.class);
		// ��������
		List<BenchLifeCycle> lifeCycles = new ArrayList<BenchLifeCycle>(this.context.getBeansOfType(BenchLifeCycle.class).values());
		Collections.sort(lifeCycles, BenchLifeCycleComparator.INSTANCE);

		benchCheckComponent.checkBeforeInit(this);

		/**
		 * ִ��init
		 */
		for (BenchLifeCycle lifeCycle : lifeCycles) {
			try {
				lifeCycle.init(this);
			} catch (Exception e) {
				throw new RuntimeException("ִ��init�쳣,lifeCycle=" + lifeCycle, e);
			}
		}
		benchCheckComponent.checkAfterInited(this);

		/**
		 * ִ��starting
		 */
		for (BenchLifeCycle lifeCycle : lifeCycles) {
			try {
				lifeCycle.starting(this);
			} catch (Exception e) {
				throw new RuntimeException("ִ��starting�쳣,lifeCycle=" + lifeCycle, e);
			}
		}

		/**
		 * ִ��started
		 */
		for (BenchLifeCycle lifeCycle : lifeCycles) {
			try {
				lifeCycle.started(this);
			} catch (Exception e) {
				throw new RuntimeException("ִ��started�쳣,lifeCycle=" + lifeCycle, e);
			}
		}
		benchCheckComponent.checkAfterStarted(this);
		started = true;
	}

	public ConfigurableApplicationContext getContext() {
		return context;
	}

	public void setContext(ConfigurableApplicationContext context) {
		this.context = context;
	}

	public ConfigurableEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(ConfigurableEnvironment environment) {
		this.environment = environment;
	}

	public SpringApplication getApplication() {
		return application;
	}

	public void setApplication(SpringApplication application) {
		this.application = application;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

}
