/**
 * 
 */
package com.bench.app.stockmanage.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * @author chenbug 2009-11-24 ÉÏÎç12:32:48
 * 
 */
public class IOUtils extends org.apache.commons.io.IOUtils {

	private static Logger logger = Logger.getLogger(IOUtils.class);

	public static void flush(Flushable flushable) {
		try {
			if (flushable != null)
				flushable.flush();
		} catch (IOException e) {
			// ºöÂÔ´íÎó
		}
	}

	public static byte[] getFileBytes(String filePath) throws IOException {
		return getFileBytes(new File(filePath));
	}

	public static byte[] getFileBytes(File file) throws IOException {
		return toByteArray(new FileInputStream(file));
	}

	/**
	 * @param content
	 * @param filePath
	 */
	public static boolean write(String content, String filePath, boolean ignoreExists) throws IOException {
		File f = new File(filePath);
		if (f.exists() && ignoreExists)
			return true;

		new File(f.getParent()).mkdirs();
		f.createNewFile();
		FileWriter fw = new FileWriter(f);
		IOUtils.write(content, fw);
		fw.flush();
		IOUtils.closeQuietly(fw);
		return true;

	}

}
