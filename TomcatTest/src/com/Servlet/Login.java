package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBTool.DBUtil;
import com.DBTool.UserInfo;
import com.alibaba.fastjson.JSON;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ")
				.append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ID = request.getParameter("ID");
		String PW = request.getParameter("PW");
		System.out.println("ID----------------" + ID);
		System.out.println("PW----------------" + PW);

		boolean type = false;
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Connection con = DBUtil.getConnection();
			Statement stmt = con.createStatement();
			String sql = "select * from person_info";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String password = rs.getString("password");
				String sex = rs.getString("sex");
				String photo = rs.getString("photo");
				UserInfo user = new UserInfo();

				// 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
				// 然后使用GB2312字符集解码指定的字节数组
				try {
					name = new String(name.getBytes("ISO-8859-1"), "GB2312");
					password = new String(password.getBytes("ISO-8859-1"),
							"GB2312");
					if(sex !=null)
					sex = new String(sex.getBytes("ISO-8859-1"), "GB2312");
					if(photo !=null)
					photo = new String(photo.getBytes("ISO-8859-1"), "GB2312");
					user.setName(name);
					user.setSex(sex);
					user.setPhoto(photo);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (name.equals(ID) && password.equals(PW)) {
					// type=true;

					Map<String, Object> map = new HashMap<>();
					map.put("code", 200);
					map.put("user", user);
					out.print(JSON.toJSONString(map));
					return;
				}else{
					
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("code", 401);
			map.put("user", null);
			out.print(JSON.toJSONString(map));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

			DBUtil.Close();

			out.flush();
			out.close();
		}
	}

	public void queryPersonInfo() {

	}

}
