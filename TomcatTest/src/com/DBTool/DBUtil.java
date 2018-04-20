package com.DBTool;

import java.io.UnsupportedEncodingException;
import java.sql.*;

public class DBUtil {
	private static String url="jdbc:mysql://localhost:3307/Account";
	private static String driverClass="com.mysql.jdbc.Driver";
	private static String username="root";
	private static String password="123456";
	private static Connection conn;
	//装载驱动
	static{
		try{
			Class.forName(driverClass);
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	//获取数据库连接
	public static Connection getConnection(){
		try{
			conn=DriverManager.getConnection(url,username,password);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
	//建立数据库连接
	public static void main(String[] args){
		Connection conn=DBUtil.getConnection();
		if(conn!=null){
			System.out.println("数据库连接成功");
		}
		else{
			System.out.println("数据库连接失败");
		}
//		if(!conn.isClosed()) 
//            System.out.println("Succeeded connecting to the Database!");

           // statement用来执行SQL语句
           Statement statement;
		try {
			statement = conn.createStatement();
			 // 要执行的SQL语句
	           String sql = "select * from account";

	           // 结果集
	           ResultSet rs = statement.executeQuery(sql);

	           String name = null;

	           while(rs.next()) {
	   
	            // 选择sname这列数据
	            name = rs.getString("name");
	   
	            // 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
	            // 然后使用GB2312字符集解码指定的字节数组
	            try {
					name = new String(name.getBytes("ISO-8859-1"),"GB2312");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            // 输出结果
	            System.out.println(rs.getString("password") + "\t" + name);
	           }

	           rs.close();
	           conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

          
	}
	//关闭数据库连接
	public static void Close(){
		if(conn!=null){
			try{
				conn.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}