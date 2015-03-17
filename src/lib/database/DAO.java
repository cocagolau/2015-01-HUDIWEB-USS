package lib.database;

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

import uss.setting.Setting;

public class DAO {

	public static Connection getConnection() {
		Connection con = null;
		String url = Setting.DB_URL;
		String id = Setting.DB_ID;
		String pw = Setting.DB_PASSWORD;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	Connection conn;

	public DAO() {
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
		T result = MapParser.getObject(cLass, record);
		return result;
	}
	
	public <T> List<T>  getRecords(Class<T> cLass, String sql, Object...parameters){
		List<Map<String, Object>> records = getRecordsMap(sql, parameters);
		List<T> result = new ArrayList<T>();
		records.forEach(record->{
			result.add(MapParser.getObject(cLass, record));
		});
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
	
}
