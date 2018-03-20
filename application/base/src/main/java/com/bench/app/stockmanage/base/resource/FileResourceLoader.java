/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.app.stockmanage.base.resource;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.log4j.Logger;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author zuoer
 *
 * @version $Id: FileResourceLoader.java, v 0.1 2018年3月16日 下午3:15:00 zuoer Exp $
 */
public class FileResourceLoader extends ResourceLoader {
	
	private static final Logger log=Logger.getLogger(FileResourceLoader.class);
	
    Map<String, Long> fileLastModified = new HashMap<>();

    @Override
    public void init(ExtendedProperties configuration) {

    }

    @Override
    public InputStream getResourceStream(String source) throws ResourceNotFoundException {
        File file = null;
        try {
            file = getResourceFile(source);
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
        	log.info("FileNotFoundException:" + source + "  " + file.getPath());
            return this.getClass().getResourceAsStream(source);
        } finally {
            if (file != null)
                fileLastModified.put(source, file.lastModified());
        }
    }

    @Override
    public boolean isSourceModified(Resource resource) {
        long lastModified = resource.getLastModified();
        File file = getResourceFile(resource.getName());
        return lastModified != file.lastModified();
    }

    @Override
    public long getLastModified(Resource resource) {
        return fileLastModified.get(resource.getName());
    }

    private File getResourceFile(String name) {
        return new File(String.format("%s/%s", "/htdocs/template/", name));
    }
}