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
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:/wdbpool");	// wdbpool.jocl  ȭ�Ͽ� dbcp Ǯ����, ȭ���̸��� �����̸��̵ȴ�.
		

			if (conn == null)
				throw new ServletException("�����ͺ��̽��� ������~");
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
				throw new ServletException("�����͸� DB�� �Է��� �� �����ϴ�.");
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