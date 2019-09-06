package com.togest.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class AcessDBUtils {

	public static Connection getConn(String filePath) {
		Connection conn = null;
		Properties prop = new Properties();
		prop.put("charSet", "gb2312"); // 这里是解决中文乱码
		prop.put("user", "");
		prop.put("password", "");
		// String url =
		// "jdbc:odbc:driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="
		// + filePath; // 文件地址
		String url = "jdbc:Access:///" + filePath; // 文件地址
		try {
			// Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Class.forName("com.hxtt.sql.access.AccessDriver");
			// conn = DriverManager.getConnection("jdbc:Access:///"+filePath);
			conn = DriverManager.getConnection(url, prop);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return conn;
	}

	/**
	 * TODO : 读取文件access
	 * 
	 * @param filePath
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static List<Map<String, String>> readFileACCESS(String filePath,
			String sql) {

		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConn(filePath);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			ResultSetMetaData data = rs.getMetaData();
			while (rs.next()) {

				Map<String, String> map = new HashMap<String, String>();
				for (int i = 1; i <= data.getColumnCount(); i++) {
					String columnName = data.getColumnName(i); // 列名
					String columnValue = rs.getString(i);
					map.put(columnName, columnValue);

					// System.out.println(columnName + "-----" + columnValue);
				}
				maplist.add(map);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(conn, ps, rs);
		}
		return maplist;
	}

	public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static List<Map<String, String>> readFileACCESSPages(
			String filePath, String table) {
		List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConn(filePath);
			ps = conn.prepareStatement("select count(*) from " + table);
			rs = ps.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				int page = count % 500 == 0 ? count / 500 : count / 500 + 1;
				int pageSize = 500;
				for (int i = 0; i < page+1; i++) {
					int n = (i* pageSize)>count? count:(i* pageSize);
					int m = n==(i* pageSize)?pageSize:(count-(i* pageSize-pageSize));
					String sql = "select * from (select top "
							+ m
							+ " * from (select top "
							+ n
							+ " * from "
							+ table
							+ " order by id desc) order by id) order by id desc";
					PreparedStatement ps1 = conn.prepareStatement(sql);
					ResultSet rs1 = ps1.executeQuery();
					ResultSetMetaData data = rs1.getMetaData();
					while (rs1.next()) {
						//System.out.println(rs1.getObject(1));
						Map<String, String> map = new HashMap<String, String>();
						for (int k = 1; k <= data.getColumnCount(); k++) {
							String columnName = data.getColumnName(k); // 列名
							String columnValue = rs1.getString(k);
							map.put(columnName, columnValue);

							//System.out.println(columnName + "-----"+ columnValue);
						}
						maplist.add(map);
						System.out.println(map);
					}
					close(null, ps1, rs1);
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			close(conn, ps, rs);
		}

		return maplist;
	}
}
