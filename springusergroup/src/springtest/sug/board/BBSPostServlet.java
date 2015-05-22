package springtest.sug.board;

import javax.servlet.http.*;
import javax.servlet.*;


import java.io.*;
import java.sql.*;
import java.util.*;


public class BBSPostServlet extends HttpServlet  {
	public static String post1(String username, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("euc-kr");
		String writer = username;

		if (writer == null)
			response.sendRedirect("../board/list");		
					
		String title = request.getParameter("TITLE");
		String content = request.getParameter("CONTENT");
		
		if (title == null || content == null)
			response.sendRedirect("../board/list");		
		int seqNo = 1;
		GregorianCalendar now = new GregorianCalendar();
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.apache.commons.dbcp.PoolingDriver");
			Class.forName("com.mysql.jdbc.Driver"); 			
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:/wdbpool");	// wdbpool.jocl  화일에 dbcp 풀설정, 화일이름이 설정이름이된다.
		

			if (conn == null)
				throw new ServletException("데이터베이스에 연결이~");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select max(seqno) as max_seqno from bbs");
			if (rs.next())
			seqNo = rs.getInt("max_seqno") + 1;
			String command = String.format("insert into bbs " +
				"(seqNo, title, content, writer, wdate, wtime) values " +
				"(%d, '%s', '%s', '%s', '%TF', '%TT');", 
				seqNo, title, content, writer, now, now);
			int rowNum = stmt.executeUpdate(command);
			if (rowNum < 1)
				throw new ServletException("데이터를 DB에 입력할 수 없습니다.");
		}
	
		catch (ClassNotFoundException cnfe) {
			throw new ServletException(cnfe);
		}
		catch (SQLException se) {
			throw new ServletException(se);
		}
		finally {
			try {
				stmt.close();
			}
			catch (Exception ignored) {
			}
			try {
				conn.close();
			}
			catch (Exception ignored) {
			}
		}

	return "redirect:../list";
	}
}