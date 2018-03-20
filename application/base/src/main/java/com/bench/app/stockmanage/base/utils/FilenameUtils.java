/**
 * 
 */
package com.bench.app.stockmanage.base.utils;

import java.io.File;

/**
 * @author chenbug 2009-12-31 上午12:27:16
 * 
 */
public class FilenameUtils extends org.apache.commons.io.FilenameUtils {

	public static final FilenameUtils INSTANCE = new FilenameUtils();

	public static String formatPath(String path) {
		path = StringUtils.replace(path, "\r", "");
		path = StringUtils.replace(path, "\n", "");
		path = StringUtils.replace(path, "\t", "");
		path = StringUtils.replace(path, " ", "");
		if (!path.startsWith("/") && !path.startsWith("\\"))
			path = "/" + path;

		return path;
	}

	/**
	 * 是否相同路径
	 * 
	 * @param path1
	 * @param path2
	 * @return
	 */
	public static boolean isSamePath(String path1, String path2) {
		if (path1 == null || path2 == null) {
			return false;
		}

		path1 = StringUtils.replace(path1, "/", StringUtils.EMPTY_STRING);
		path1 = StringUtils.replace(path1, "\\", StringUtils.EMPTY_STRING);

		path2 = StringUtils.replace(path2, "/", StringUtils.EMPTY_STRING);
		path2 = StringUtils.replace(path2, "\\", StringUtils.EMPTY_STRING);

		return path1.equals(path2);
	}

	/**
	 * @param sourceFile
	 * @param findStr
	 * @param replaceStr
	 */
	public static void replaceName(String sourceFile, String findStr, String replaceStr) {
		replaceName(new File(sourceFile), findStr, replaceStr);
	}

	/**
	 * @param sourceFile
	 * @param findStr
	 * @param replaceStr
	 */
	public static void replaceName(File sourceFile, String findStr, String replaceStr) {
		if (!sourceFile.exists()) {
			return;
		}
		String newName = StringUtils.replace(sourceFile.getName(), findStr, replaceStr);
		if (!StringUtils.equals(newName, sourceFile.getName())) {
			sourceFile.renameTo(new File(sourceFile.getParent(), newName));
		}
		if (sourceFile.isDirectory()) {
			for (File childFile : sourceFile.listFiles()) {
				replaceName(childFile, findStr, replaceStr);
			}
		}

	}

	/**
	 * @param sourceFile
	 * @param findStr
	 * @param replaceStr
	 */
	public static void rename(String sourceFile, FileRenamer renamer) {
		rename(new File(sourceFile), renamer);
	}

	/**
	 * @param sourceFile
	 * @param findStr
	 * @param replaceStr
	 */
	public static void rename(File sourceFile, FileRenamer renamer) {
		if (!sourceFile.exists()) {
			return;
		}
		String newName = renamer.rename(sourceFile);
		if (!StringUtils.equals(newName, sourceFile.getName())) {
			sourceFile.renameTo(new File(sourceFile.getParent(), newName));
		}
		if (sourceFile.isDirectory()) {
			for (File childFile : sourceFile.listFiles()) {
				rename(childFile, renamer);
			}
		}
	}

	public static interface FileRenamer {
		public String rename(File file);
	}
}
