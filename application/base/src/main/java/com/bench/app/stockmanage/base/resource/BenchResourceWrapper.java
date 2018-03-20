/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2005 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.bench.app.stockmanage.base.config.DBConfigrationRemoteFactory;
import com.bench.app.stockmanage.base.helper.VelocityHelper;
import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * bench资源封装<br>
 * 使用Velocity过滤资源的装饰类
 * 
 * <p>
 * 添加对spring 2.5.5 <code>Resource</code>接口的支持
 * </p>
 * 
 * @author chenbug
 */
public class BenchResourceWrapper implements Resource {

	public static final String NEED_FILTER_SUFFIX = "[filter]";

	// 必须过滤的资源数组
	public static final String[] MUST_FILTER_RESOURCE_NAMES = new String[] {};

	private final Logger log = LoggerFactory.getLogger(BenchResourceWrapper.class);

	/** 对bench-config.properties文件的内容进行缓存 */
	public static Map<String, String> benchConfigs = null;

	/** 被装饰的资源 **/
	private Resource originalResource;

	public BenchResourceWrapper() {
	}

	// ~~~ 构造方法
	/**
	 * 创建一个<code>VelocityFilterResource</code>对象
	 * 
	 * @param decoratedResource
	 */
	public BenchResourceWrapper(Resource originalResource) {
		this.originalResource = originalResource;
	}

	// ~~~ 接口方法

	/**
	 * @see org.springframework.core.io.Resource#createRelative(java.lang.String)
	 */
	public Resource createRelative(String relativePath) throws IOException {
		Resource resource = this.originalResource.createRelative(relativePath);
		if (resource == null) {
			relativePath += NEED_FILTER_SUFFIX;
			resource = this.originalResource.createRelative(relativePath);
		}
		return new BenchResourceWrapper(resource);
	}

	/**
	 * @see org.springframework.core.io.Resource#exists()
	 */
	public boolean exists() {
		return this.originalResource.exists();
	}

	/**
	 * @see org.springframework.core.io.Resource#getDescription()
	 */
	public String getDescription() {
		return "Decoration velocity[ " + this.originalResource.getDescription() + "]";
	}

	/**
	 * @see org.springframework.core.io.Resource#getFile()
	 */
	public File getFile() throws IOException {
		return this.originalResource.getFile();
	}

	/**
	 * @see org.springframework.core.io.Resource#getFilename()
	 */
	public String getFilename() {
		String fileName = this.originalResource.getFilename();
		return StringUtils.substringBefore(fileName, NEED_FILTER_SUFFIX);
	}

	/**
	 * @see org.springframework.core.io.Resource#getURL()
	 */
	public URL getURL() throws IOException {
		return this.originalResource.getURL();
	}

	/**
	 * @see org.springframework.core.io.Resource#getURI()
	 */
	public URI getURI() throws IOException {
		return this.originalResource.getURI();
	}

	/**
	 * @see org.springframework.core.io.Resource#isOpen()
	 */
	public boolean isOpen() {
		return this.originalResource.isOpen();
	}

	/**
	 * @see org.springframework.core.io.InputStreamSource#getInputStream()
	 */
	public InputStream getInputStream() throws IOException {
		if (!ManifestResourceFilterHolder.isAllowFilter(this.getURL().toString())) {
			return this.originalResource.getInputStream();
		}
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("dbConfig", DBConfigrationRemoteFactory.getRemoteDBConfigration());
		try {
			return VelocityHelper.getInstance().evaluate(this.originalResource.getInputStream(), parameterMap);
		} catch (Exception e) {
			log.error("evaluate资源文件" + this.toString() + "异常：" + e.toString(), e);
			return null;
		}
	}

	/**
	 * @see org.springframework.core.io.Resource#isReadable()
	 */
	public boolean isReadable() {
		return this.originalResource.isReadable();
	}

	/**
	 * @see org.springframework.core.io.Resource#lastModified()
	 */
	public long lastModified() throws IOException {
		return this.originalResource.lastModified();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return originalResource.getFilename().toString();
	}

	/**
	 * 初始化Velocity引擎
	 */
	protected void initVelocity() {
	}

	protected byte[] int2byte(int n) {
		byte b[] = new byte[4];
		b[0] = (byte) (n >> 24);
		b[1] = (byte) (n >> 16);
		b[2] = (byte) (n >> 8);
		b[3] = (byte) n;
		return b;
	}

	protected byte[] chars2byte(char[] c) {
		byte[] b = new byte[c.length * 4];
		for (int i = 0; i < c.length; i++) {
			byte[] oneChar = int2byte(c[i]);
			if (i == 0) {
				b[0] = oneChar[0];
				b[1] = oneChar[1];
				b[2] = oneChar[2];
				b[3] = oneChar[3];
			} else {
				b[i] = oneChar[0];
				b[i * 2] = oneChar[1];
				b[i * 3] = oneChar[2];
				b[i * 4] = oneChar[3];
			}
		}
		return b;
	}

	public Resource getDecoratedResource() {
		return originalResource;
	}

	public void setDecoratedResource(Resource decoratedResource) {
		this.originalResource = decoratedResource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.core.io.Resource#contentLength()
	 */
	@Override
	public long contentLength() throws IOException {
		// TODO Auto-generated method stub
		return originalResource.contentLength();
	}
}
