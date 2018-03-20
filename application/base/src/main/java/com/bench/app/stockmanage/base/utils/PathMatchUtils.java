package com.bench.app.stockmanage.base.utils;

import java.util.List;

import org.springframework.util.StringUtils;

/**
 * 基于Ant的路径匹配
 * 
 * @author chenbug
 *
 * @version $Id: PathMatchUtils.java, v 0.1 2014年12月16日 下午5:58:37 chenbug Exp $
 */
public class PathMatchUtils {

	/** Default path separator: "/" */
	public static final String DEFAULT_PATH_SEPARATOR = "/";

	public static boolean isPattern(String path) {
		return (path.indexOf('*') != -1 || path.indexOf('?') != -1);
	}

	public static boolean match(String pattern, String path) {
		return doMatch(pattern, path, true);
	}

	public static boolean matchStart(String pattern, String path) {
		return doMatch(pattern, path, false);
	}

	/**
	 * 是否任意匹配
	 * 
	 * @param patterns
	 * @param path
	 * @return
	 */
	public static boolean matchAny(String[] patterns, String path) {
		for (String pattern : patterns) {
			boolean matched = doMatch(pattern, path, false);
			if (matched) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否任意匹配
	 * 
	 * @param patterns
	 * @param path
	 * @return
	 */
	public static boolean matchAny(List<String> patterns, String path) {
		for (String pattern : patterns) {
			boolean matched = doMatch(pattern, path, false);
			if (matched) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Actually match the given <code>path</code> against the given
	 * <code>pattern</code>.
	 * 
	 * @param pattern
	 *            the pattern to match against
	 * @param path
	 *            the path String to test
	 * @param fullMatch
	 *            whether a full pattern match is required (else a pattern match
	 *            as far as the given base path goes is sufficient)
	 * @return <code>true</code> if the supplied <code>path</code> matched,
	 *         <code>false</code> if it didn't
	 */
	protected static boolean doMatch(String pattern, String path, boolean fullMatch) {
		if (path.startsWith(DEFAULT_PATH_SEPARATOR) != pattern.startsWith(DEFAULT_PATH_SEPARATOR)) {
			return false;
		}

		String[] pattDirs = StringUtils.tokenizeToStringArray(pattern, DEFAULT_PATH_SEPARATOR);
		String[] pathDirs = StringUtils.tokenizeToStringArray(path, DEFAULT_PATH_SEPARATOR);

		int pattIdxStart = 0;
		int pattIdxEnd = pattDirs.length - 1;
		int pathIdxStart = 0;
		int pathIdxEnd = pathDirs.length - 1;

		// Match all elements up to the first **
		while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
			String patDir = pattDirs[pattIdxStart];
			if ("**".equals(patDir)) {
				break;
			}
			if (!matchStrings(patDir, pathDirs[pathIdxStart])) {
				return false;
			}
			pattIdxStart++;
			pathIdxStart++;
		}

		if (pathIdxStart > pathIdxEnd) {
			// Path is exhausted, only match if rest of pattern is * or **'s
			if (pattIdxStart > pattIdxEnd) {
				return (pattern.endsWith(DEFAULT_PATH_SEPARATOR) ? path.endsWith(DEFAULT_PATH_SEPARATOR) : !path.endsWith(DEFAULT_PATH_SEPARATOR));
			}
			if (!fullMatch) {
				return true;
			}
			if (pattIdxStart == pattIdxEnd && pattDirs[pattIdxStart].equals("*") && path.endsWith(DEFAULT_PATH_SEPARATOR)) {
				return true;
			}
			for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
				if (!pattDirs[i].equals("**")) {
					return false;
				}
			}
			return true;
		} else if (pattIdxStart > pattIdxEnd) {
			// String not exhausted, but pattern is. Failure.
			return false;
		} else if (!fullMatch && "**".equals(pattDirs[pattIdxStart])) {
			// Path start definitely matches due to "**" part in pattern.
			return true;
		}

		// up to last '**'
		while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
			String patDir = pattDirs[pattIdxEnd];
			if (patDir.equals("**")) {
				break;
			}
			if (!matchStrings(patDir, pathDirs[pathIdxEnd])) {
				return false;
			}
			pattIdxEnd--;
			pathIdxEnd--;
		}
		if (pathIdxStart > pathIdxEnd) {
			// String is exhausted
			for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
				if (!pattDirs[i].equals("**")) {
					return false;
				}
			}
			return true;
		}

		while (pattIdxStart != pattIdxEnd && pathIdxStart <= pathIdxEnd) {
			int patIdxTmp = -1;
			for (int i = pattIdxStart + 1; i <= pattIdxEnd; i++) {
				if (pattDirs[i].equals("**")) {
					patIdxTmp = i;
					break;
				}
			}
			if (patIdxTmp == pattIdxStart + 1) {
				// '**/**' situation, so skip one
				pattIdxStart++;
				continue;
			}
			// Find the pattern between padIdxStart & padIdxTmp in str between
			// strIdxStart & strIdxEnd
			int patLength = (patIdxTmp - pattIdxStart - 1);
			int strLength = (pathIdxEnd - pathIdxStart + 1);
			int foundIdx = -1;

			strLoop: for (int i = 0; i <= strLength - patLength; i++) {
				for (int j = 0; j < patLength; j++) {
					String subPat = (String) pattDirs[pattIdxStart + j + 1];
					String subStr = (String) pathDirs[pathIdxStart + i + j];
					if (!matchStrings(subPat, subStr)) {
						continue strLoop;
					}
				}
				foundIdx = pathIdxStart + i;
				break;
			}

			if (foundIdx == -1) {
				return false;
			}

			pattIdxStart = patIdxTmp;
			pathIdxStart = foundIdx + patLength;
		}

		for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
			if (!pattDirs[i].equals("**")) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Tests whether or not a string matches against a pattern. The pattern may
	 * contain two special characters:<br>
	 * '*' means zero or more characters<br>
	 * '?' means one and only one character
	 * 
	 * @param pattern
	 *            pattern to match against. Must not be <code>null</code>.
	 * @param str
	 *            string which must be matched against the pattern. Must not be
	 *            <code>null</code>.
	 * @return <code>true</code> if the string matches against the pattern, or
	 *         <code>false</code> otherwise.
	 */
	private static boolean matchStrings(String pattern, String str) {
		char[] patArr = pattern.toCharArray();
		char[] strArr = str.toCharArray();
		int patIdxStart = 0;
		int patIdxEnd = patArr.length - 1;
		int strIdxStart = 0;
		int strIdxEnd = strArr.length - 1;
		char ch;

		boolean containsStar = false;
		for (int i = 0; i < patArr.length; i++) {
			if (patArr[i] == '*') {
				containsStar = true;
				break;
			}
		}

		if (!containsStar) {
			// No '*'s, so we make a shortcut
			if (patIdxEnd != strIdxEnd) {
				return false; // Pattern and string do not have the same size
			}
			for (int i = 0; i <= patIdxEnd; i++) {
				ch = patArr[i];
				if (ch != '?') {
					if (ch != strArr[i]) {
						return false;// Character mismatch
					}
				}
			}
			return true; // String matches against pattern
		}

		if (patIdxEnd == 0) {
			return true; // Pattern contains only '*', which matches anything
		}

		// Process characters before first star
		while ((ch = patArr[patIdxStart]) != '*' && strIdxStart <= strIdxEnd) {
			if (ch != '?') {
				if (ch != strArr[strIdxStart]) {
					return false;// Character mismatch
				}
			}
			patIdxStart++;
			strIdxStart++;
		}
		if (strIdxStart > strIdxEnd) {
			// All characters in the string are used. Check if only '*'s are
			// left in the pattern. If so, we succeeded. Otherwise failure.
			for (int i = patIdxStart; i <= patIdxEnd; i++) {
				if (patArr[i] != '*') {
					return false;
				}
			}
			return true;
		}

		// Process characters after last star
		while ((ch = patArr[patIdxEnd]) != '*' && strIdxStart <= strIdxEnd) {
			if (ch != '?') {
				if (ch != strArr[strIdxEnd]) {
					return false;// Character mismatch
				}
			}
			patIdxEnd--;
			strIdxEnd--;
		}
		if (strIdxStart > strIdxEnd) {
			// All characters in the string are used. Check if only '*'s are
			// left in the pattern. If so, we succeeded. Otherwise failure.
			for (int i = patIdxStart; i <= patIdxEnd; i++) {
				if (patArr[i] != '*') {
					return false;
				}
			}
			return true;
		}

		// process pattern between stars. padIdxStart and patIdxEnd point
		// always to a '*'.
		while (patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
			int patIdxTmp = -1;
			for (int i = patIdxStart + 1; i <= patIdxEnd; i++) {
				if (patArr[i] == '*') {
					patIdxTmp = i;
					break;
				}
			}
			if (patIdxTmp == patIdxStart + 1) {
				// Two stars next to each other, skip the first one.
				patIdxStart++;
				continue;
			}
			// Find the pattern between padIdxStart & padIdxTmp in str between
			// strIdxStart & strIdxEnd
			int patLength = (patIdxTmp - patIdxStart - 1);
			int strLength = (strIdxEnd - strIdxStart + 1);
			int foundIdx = -1;
			strLoop: for (int i = 0; i <= strLength - patLength; i++) {
				for (int j = 0; j < patLength; j++) {
					ch = patArr[patIdxStart + j + 1];
					if (ch != '?') {
						if (ch != strArr[strIdxStart + i + j]) {
							continue strLoop;
						}
					}
				}

				foundIdx = strIdxStart + i;
				break;
			}

			if (foundIdx == -1) {
				return false;
			}

			patIdxStart = patIdxTmp;
			strIdxStart = foundIdx + patLength;
		}

		// All characters in the string are used. Check if only '*'s are left
		// in the pattern. If so, we succeeded. Otherwise failure.
		for (int i = patIdxStart; i <= patIdxEnd; i++) {
			if (patArr[i] != '*') {
				return false;
			}
		}

		return true;
	}

	/**
	 * Given a pattern and a full path, determine the pattern-mapped part.
	 * <p>
	 * For example:
	 * <ul>
	 * <li>'<code>/docs/cvs/commit.html</code>' and '
	 * <code>/docs/cvs/commit.html</code> -> ''</li>
	 * <li>'<code>/docs/*</code>' and '<code>/docs/cvs/commit</code> -> '
	 * <code>cvs/commit</code>'</li>
	 * <li>'<code>/docs/cvs/*.html</code>' and '
	 * <code>/docs/cvs/commit.html</code> -> '<code>commit.html</code>'</li>
	 * <li>'<code>/docs/**</code>' and '<code>/docs/cvs/commit</code> -> '
	 * <code>cvs/commit</code>'</li>
	 * <li>'<code>/docs/**\/*.html</code>' and '
	 * <code>/docs/cvs/commit.html</code> -> '<code>cvs/commit.html</code>'</li>
	 * <li>'<code>/*.html</code>' and '<code>/docs/cvs/commit.html</code> -> '
	 * <code>docs/cvs/commit.html</code>'</li>
	 * <li>'<code>*.html</code>' and '<code>/docs/cvs/commit.html</code> -> '
	 * <code>/docs/cvs/commit.html</code>'</li>
	 * <li>'<code>*</code>' and '<code>/docs/cvs/commit.html</code> -> '
	 * <code>/docs/cvs/commit.html</code>'</li>
	 * </ul>
	 * <p>
	 * Assumes that {@link #match} returns <code>true</code> for '
	 * <code>pattern</code>' and '<code>path</code>', but does
	 * <strong>not</strong> enforce this.
	 */
	public static String extractPathWithinPattern(String pattern, String path) {
		String[] patternParts = StringUtils.tokenizeToStringArray(pattern, DEFAULT_PATH_SEPARATOR);
		String[] pathParts = StringUtils.tokenizeToStringArray(path, DEFAULT_PATH_SEPARATOR);

		StringBuffer buffer = new StringBuffer();

		// Add any path parts that have a wildcarded pattern part.
		int puts = 0;
		for (int i = 0; i < patternParts.length; i++) {
			String patternPart = patternParts[i];
			if ((patternPart.indexOf('*') > -1 || patternPart.indexOf('?') > -1) && pathParts.length >= i + 1) {
				if (puts > 0 || (i == 0 && !pattern.startsWith(DEFAULT_PATH_SEPARATOR))) {
					buffer.append(DEFAULT_PATH_SEPARATOR);
				}
				buffer.append(pathParts[i]);
				puts++;
			}
		}

		// Append any trailing path parts.
		for (int i = patternParts.length; i < pathParts.length; i++) {
			if (puts > 0 || i > 0) {
				buffer.append(DEFAULT_PATH_SEPARATOR);
			}
			buffer.append(pathParts[i]);
		}

		return buffer.toString();
	}

}
