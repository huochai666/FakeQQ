package utils;

import java.sql.*;
import java.util.Properties;

public class DBConnec{
	public static Connection getConnection()throws SQLException,ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		String  url="jdbc:mysql://localhost:3306/qq";
		String  username="root";
		String  password="root";
		Connection conn= DriverManager.getConnection(url,username,password);
		return conn;
	}

	public static void release(Statement st, Connection conn){
		if(st!=null){
			try{
				st.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
			st=null;
		}
		if(conn!=null){
			try{
				conn.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
			conn=null;
		}
	}
	public static void release(ResultSet rs, Statement st, Connection conn){
		if(rs!=null){
			try{
				rs.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
			rs=null;
		}
		release(st,conn);
	}
}