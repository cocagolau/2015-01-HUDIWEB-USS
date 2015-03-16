package uss.database.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBExecuter {

	public static Connection getConnection() {
		Connection con = null;
		String url = "";
		String id = "";
		String pw = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	Connection conn;

	public DBExecuter() {
		conn = getConnection();
	}

	private static ResultSet getResultSet(Connection conn, String sql, Object[] parameters) {
		ResultSet rs = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if (parameters != null)
				for (int j = 0; j < parameters.length; j++) {
					pstmt.setObject(j + 1, parameters[j]);
				}
			rs = pstmt.executeQuery();
			close(pstmt);
		} catch (SQLException e) {
			System.out.println(sql);
			System.out.println(parameters);
			e.printStackTrace();
		}
		return rs;
	}

	public List<Object> getRecord(String sql, int resultSize, Object... parameters) {
		List<Object> record = new ArrayList<Object>();
		try {
			ResultSet rs = getResultSet(conn, sql, parameters);
			while (rs.next()) {
				for (int i = 0; i < resultSize; i++) {
					record.add(rs.getObject(i + 1));
				}
			}
			close(rs);
		} catch (SQLException e) {
			System.out.println(sql);
			System.out.println(parameters);
			e.printStackTrace();
		}
		return record;
	}

	public Map<String, Object> getRecordMap(String sql, Object... parameters) {
		Map<String, Object> record = new LinkedHashMap<String, Object>();
		try {
			ResultSet rs = getResultSet(conn, sql, parameters);
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					record.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
			}
			close(rs);
		} catch (SQLException e) {
			System.out.println(sql);
			System.out.println(parameters);
			e.printStackTrace();
		}
		return record;
	}

	public List<List<Object>> getRecords(String sql, int resultSize, Object... parameters) {
		List<Object> record;
		List<List<Object>> result = new ArrayList<List<Object>>();
		try {
			ResultSet rs = getResultSet(conn, sql, parameters);
			while (rs.next()) {
				record = new ArrayList<Object>();
				for (int i = 0; i < resultSize; i++) {
					record.add(rs.getObject(i + 1));
				}
				result.add(record);
			}
			close(rs);
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}

		return result;
	}
	
	public List<Map<String, Object>> getRecordsMap(String sql, Object... parameters) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			ResultSet rs = getResultSet(conn, sql, parameters);
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> columns = new LinkedHashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					columns.put(metaData.getColumnLabel(i), rs.getObject(i));
				}
				result.add(columns);
			}
			close(rs);
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}
		return result;
	}
	
	public <T> T getRecord(Class<T> cLass, String sql, Object...parameters){
		Map<String, Object> record = getRecordMap(sql, parameters);
		T result = getInvokedInstance(cLass, record);
		return result;
	}
	
	public <T> List<T>  getRecords(Class<T> cLass, String sql, Object...parameters){
		List<Map<String, Object>> records = getRecordsMap(sql, parameters);
		List<T> result = new ArrayList<T>();
		records.forEach(record->{
			result.add(this.getInvokedInstance(cLass, record));
		});
		return result;
	}

	private <T> T getInvokedInstance(Class<T> cLass, Map<String, Object> record) {
		Field[] fields = cLass.getFields();
		T result = null;
		try {
			result = cLass.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		for(int i=0; i<fields.length;i++){
			Object obj = record.get(fields[i].getName());
			if(obj == null)
				continue;
			try {
				Method setterMethod = cLass.getMethod(setterString(fields[i].getName()), obj.getClass());
				if(setterMethod == null)
					continue;
				try {
					setterMethod.invoke(result, obj);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				}
			} catch (NoSuchMethodException | SecurityException e) {
			}
		}
		return result;
	}
	

	public Boolean execute(String sql, Object... parameters) {
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			for (int j = 0; j < parameters.length; j++) {
				pstmt.setObject(j + 1, parameters[j]);
			}
			pstmt.execute();
			int result = pstmt.getUpdateCount();
			close(pstmt);
			return result != 0;
		} catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}
		return false;
	}

	private static void close(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException sqle) {
			}
	}

	private static void close(PreparedStatement pstmt) {
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException sqle) {
			}
	}

	public void close() {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException sqle) {
			}
	}
	
	public static String setterString(String fieldName) {
		return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
