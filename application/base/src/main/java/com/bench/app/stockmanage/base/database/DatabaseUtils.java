package com.bench.app.stockmanage.base.database;

import java.io.IOException;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.io.IOUtils;

import com.bench.app.stockmanage.base.database.enums.DatabaseTypeEnum;
import com.bench.app.stockmanage.base.utils.StringUtils;

/**
 * 数据库工具类
 * 
 * @author chenbug
 * 
 * @version $Id: DataBaseUtils.java, v 0.1 2013-3-7 下午7:13:27 chenbug Exp $
 */
public class DatabaseUtils {
	/**
	 * 关闭链接
	 * 
	 * @param connection
	 */
	public static void closeConnection(Connection connection) {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
		} catch (Exception e) {

		}
	}

	/**
	 * 关闭stmt
	 * 
	 * @param connection
	 */
	public static void closeStatement(Statement statement) {
		if (statement == null) {
			return;
		}
		try {
			statement.close();
		} catch (Exception e) {

		}
	}

	/**
	 * 关闭ResultSet
	 * 
	 * @param connection
	 */
	public static void closeResultSet(ResultSet rs) {
		if (rs == null) {
			return;
		}
		try {
			rs.close();
		} catch (Exception e) {

		}
	}

	public static String getDriverClass(DatabaseTypeEnum type) {
		if (type == DatabaseTypeEnum.ORACLE) {
			return "oracle.jdbc.driver.OracleDriver";
		} else if (type == DatabaseTypeEnum.MYSQL) {
			return "com.mysql.jdbc.Driver";
		}
		return null;
	}

	public static DatabaseTypeEnum getDatabaseTypeFromConnectUrl(String connectUrl) {
		if (StringUtils.startsWith(connectUrl, "jdbc:mysql:")) {
			return DatabaseTypeEnum.MYSQL;
		} else if (StringUtils.startsWith(connectUrl, "jdbc:oracle:")) {
			return DatabaseTypeEnum.ORACLE;
		}
		return null;
	}

	public static String getTestSql(DatabaseTypeEnum type) {
		if (type == DatabaseTypeEnum.ORACLE) {
			return "select 1 from dual";
		} else if (type == DatabaseTypeEnum.MYSQL) {
			return "select 1";
		}
		return null;
	}

	public static byte[] objectToBytes(Object objectStoreInDB) throws SQLException {
		if (objectStoreInDB == null) {
			return null;
		}
		if (objectStoreInDB instanceof java.sql.Blob) {
			return convertBlobToBytes((java.sql.Blob) objectStoreInDB);
		} else if (objectStoreInDB instanceof byte[]) {
			return (byte[]) objectStoreInDB;
		}
		throw new RuntimeException("无法将数据类型转换为byte[]，object=" + objectStoreInDB);
	}

	public static String objectToString(Object objectStoreInDB) throws SQLException {
		if (objectStoreInDB == null) {
			return null;
		}
		if (objectStoreInDB instanceof java.sql.Clob) {
			return convertClobToString((java.sql.Clob) objectStoreInDB);
		} else if (objectStoreInDB instanceof java.lang.String) {
			return (java.lang.String) objectStoreInDB;
		}
		throw new RuntimeException("无法将数据类型转换为String，object=" + objectStoreInDB);
	}

	/**
	 * 转换clob到String
	 * 
	 * @param clob
	 * @return
	 * @throws SQLException
	 */
	public static String convertClobToString(Clob clob) throws SQLException {
		Reader reader = null;
		try {
			reader = clob.getCharacterStream();
			return IOUtils.toString(reader);
		} catch (IOException e) {
			throw new SQLException("转换Clob到String异常", e);
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}

	/**
	 * 转换blob到bytes
	 * 
	 * @param clob
	 * @return
	 * @throws SQLException
	 */
	public static byte[] convertBlobToBytes(Blob blob) throws SQLException {
		return blob.getBytes(1, (int) blob.length());
	}
}
