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
 * Bench运行时
 * 
 * @author chenbug
 *
 * @version $Id: BenchRuntime.java, v 0.1 2015年6月9日 下午5:53:17 chenbug Exp $
 */
@Service
public class BenchRuntime {

	private static final Logger log = Logger.getLogger(BenchRuntime.class);

	private boolean started = false;

	public static final BenchRuntime INSTANCE = new BenchRuntime();

	/**
	 * 运行的应用
	 */
	private SpringApplication application;

	/**
	 * 运行参数
	 */
	private String[] args;

	/**
	 * 注意，改类只有在spring容器启动后才有值
	 */
	private ConfigurableApplicationContext context;

	/**
	 * 配置环境，应用启动才有
	 */
	private ConfigurableEnvironment environment;

//	/**
//	 * 直接构造的，在运行阶段收集信息,必须使用静态的，以便只有一个实例
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
	 * 获取服务组件
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
		// 检查器
		BenchCheckComponent benchCheckComponent = this.context.getBean(BenchCheckComponent.class);
		// 生命周期
		List<BenchLifeCycle> lifeCycles = new ArrayList<BenchLifeCycle>(this.context.getBeansOfType(BenchLifeCycle.class).values());
		Collections.sort(lifeCycles, BenchLifeCycleComparator.INSTANCE);

		benchCheckComponent.checkBeforeInit(this);

		/**
		 * 执行init
		 */
		for (BenchLifeCycle lifeCycle : lifeCycles) {
			try {
				lifeCycle.init(this);
			} catch (Exception e) {
				throw new RuntimeException("执行init异常,lifeCycle=" + lifeCycle, e);
			}
		}
		benchCheckComponent.checkAfterInited(this);

		/**
		 * 执行starting
		 */
		for (BenchLifeCycle lifeCycle : lifeCycles) {
			try {
				lifeCycle.starting(this);
			} catch (Exception e) {
				throw new RuntimeException("执行starting异常,lifeCycle=" + lifeCycle, e);
			}
		}

		/**
		 * 执行started
		 */
		for (BenchLifeCycle lifeCycle : lifeCycles) {
			try {
				lifeCycle.started(this);
			} catch (Exception e) {
				throw new RuntimeException("执行started异常,lifeCycle=" + lifeCycle, e);
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
