package lib.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Queue;

import lib.setting.Setting;

public class ConnectionPool {

	Queue<Connection> conns;

	private static final String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String url = Setting.get("database", "url");
	public static final String id = Setting.get("database", "id");
	public static final String pw = Setting.get("database", "password");


	public synchronized Connection getConnection() {
		Connection con = conns.poll();
		if (con != null) {
			return con;
		}
		try {
			Class.forName(COM_MYSQL_JDBC_DRIVER);
			con = DriverManager.getConnection(url, id, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public synchronized void returnConnection(Connection con) {
		conns.add(con);
	}
}
