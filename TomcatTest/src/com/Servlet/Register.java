package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;
import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ID = request.getParameter("ID"); 
        String PW= request.getParameter("PW");
        System.out.println("ID----------------"+ID);
        System.out.println("PW----------------"+PW);

        boolean type=true;
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        try
        {
        	Connection con=DBUtil.getConnection();
        	Statement stmt=con.createStatement();
			String sql="insert into person_info(name,password) values('"+ID+"','"+PW+"');";
			int rs=stmt.executeUpdate(sql);
	        System.out.println("rs----------------"+rs);

//		    while(rs.next())
//		    {
//		    	 String name = rs.getString("name");
//		    	 String password = rs.getString("password");
//
//		  	   
//		            // 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
//		            // 然后使用GB2312字符集解码指定的字节数组
//		            try {
//						name = new String(name.getBytes("ISO-8859-1"),"GB2312");
//						password = new String(password.getBytes("ISO-8859-1"),"GB2312");
//
//					} catch (UnsupportedEncodingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		           if (name.equals(ID)&&password.equals(PW))
//		    	type=true;
//		    }
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        	type=false;
        }
        finally
        {
        
        	out.print(type);
        	
        	DBUtil.Close();
        	out.flush();
        	out.close();
        }
	}

}
