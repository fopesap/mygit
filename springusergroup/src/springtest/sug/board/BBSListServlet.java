package springtest.sug.board;

import javax.servlet.http.*;

import java.io.*;
import java.sql.*;
import java.util.List;



public class BBSListServlet {
    public static BBSList doGet1(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
       
    	
    	String strFirstSeqNo = request.getParameter("FIRST_SEQ_NO");
    	String strLastSeqNo = request.getParameter("LAST_SEQ_NO");
    	String strPageNo = request.getParameter("PAGE_NO");
    	BBSList list;
    	if (strFirstSeqNo != null) {
    		list = readPrevPage(Integer.parseInt(strFirstSeqNo));
    	}
    	else if (strLastSeqNo != null) {
    		list = readNextPage(Integer.parseInt(strLastSeqNo));
    	}
    	else if (strPageNo != null) {
    		list = readPage(Integer.parseInt(strPageNo));
    	}
    	else {
	    	list = readNextPage(Integer.MAX_VALUE);
	    	list.setFirstPage(true);
    	}
    	list.setPageNum(readPageNum());        
        
        
		return list; 
    }
    
    
    
    private static BBSList readPrevPage(int lowerSeqNo) throws Exception {
    	// DB로부터 이전 페이지를 읽는 메서드
    	BBSList list = new BBSList();
    	Connection conn = null;
    	Statement stmt = null;
    	
    	try {
    	Class.forName("org.apache.commons.dbcp.PoolingDriver");
    	Class.forName("com.mysql.jdbc.Driver");    	
    	conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:/wdbpool");// wdbpool.jocl 파일에 dbcp 풀 설정
    	
    	if (conn == null)
    		throw new Exception("데이터베이스에 연결할 수 없습니다.");
    	stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery("select * from bbs where seqNo > " + lowerSeqNo +
    			" order by seqno asc;");

    	for (int cnt = 0; cnt < 10; cnt++) {
    	if (!rs.next())
	    		break;
	    	list.setSeqNo(0, rs.getInt("seqNo"));
	    	list.setTitle(0, (rs.getString("title")));				// list.setTitle(0, toUnicode(rs.getString("title")));
	    	list.setWriter(0, (rs.getString("writer")));			// list.setWriter(0, toUnicode(rs.getString("writer")));
	    	list.setDate(0, rs.getDate("wdate"));
	    	list.setTime(0, rs.getTime("wtime"));
    	}            
            
    	if (!rs.next())   
       		list.setFirstPage(true);
    	}
    	catch (Exception e) {
    		throw new Exception(e);
    		
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
    	return list;
    }    
    

    private static BBSList readNextPage(int upperSeqNo) throws Exception { // DB로부터 다음 페이지를 읽는 메서드
    	BBSList list = new BBSList();
    	Connection conn = null;
    	Statement stmt = null;
    	
    	try {
    	Class.forName("org.apache.commons.dbcp.PoolingDriver");
    	Class.forName("com.mysql.jdbc.Driver");  
    	conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:/wdbpool");
    	
    	if (conn == null)
    		throw new Exception("데이터베이스에 연결할 수 없습니다.");
    	stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery("select * from bbs where seqNo < " + upperSeqNo +
    			" order by seqno desc;");
    	
       	for (int cnt = 0; cnt < 10; cnt++) {
	    	if (!rs.next())
	    		break;
	    	list.setSeqNo(cnt, rs.getInt("seqNo"));
	    	list.setTitle(cnt, (rs.getString("title")));
	    	list.setWriter(cnt, (rs.getString("writer")));
	    	list.setDate(cnt, rs.getDate("wdate"));
	    	list.setTime(cnt, rs.getTime("wtime"));
    	}
    	if (!rs.next())
    		list.setLastPage(true);
    	}
    	catch (Exception e) {
    		throw new Exception(e);
    		
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
    	return list;
    	}
    

    private static BBSList readPage(int pageNo) throws Exception {	    // DB로부터 특정 페이지를 읽는 메서드
    	BBSList list = new BBSList();
    	Connection conn = null;
    	Statement stmt = null;
    	try {
    	Class.forName("org.apache.commons.dbcp.PoolingDriver");
    	Class.forName("com.mysql.jdbc.Driver");      	
    	conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:/wdbpool");
    	
    	if (conn == null)
    		throw new Exception("데이터베이스에 연결할 수 없습니다.");
    	stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery(
    	"select * from bbs order by seqno desc;");
    	if (pageNo > 1)
    		rs.absolute((pageNo - 1) * 10);

       	for (int cnt = 0; cnt < 10; cnt++) {
	    	if (!rs.next())
	    		break;
	    	list.setSeqNo(cnt, rs.getInt("seqNo"));
	    	list.setTitle(cnt, (rs.getString("title")));
	    	list.setWriter(cnt, (rs.getString("writer")));
	    	list.setDate(cnt, rs.getDate("wdate"));
	    	list.setTime(cnt, rs.getTime("wtime"));
    	}
    	if (pageNo == 1)
    		list.setFirstPage(true);
    	if (!rs.next())
    		list.setLastPage(true);
    	}
    	catch (Exception e) {
    		throw new Exception(e);
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
    	return list;
    	}
    	

    	private static int readPageNum() throws Exception {				// DB로부터 페이지 수를 읽는 메서드
    		int pageNum;
    		Connection conn = null;
    		Statement stmt = null;
    		
    		try {
    		Class.forName("org.apache.commons.dbcp.PoolingDriver");
    		Class.forName("com.mysql.jdbc.Driver");     		
    		conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:/wdbpool");
    		
    		if (conn == null)
    			throw new Exception("데이터베이스에 연결할 수 없습니다.");
    		stmt = conn.createStatement();
    		ResultSet rs = stmt.executeQuery("select count(*) as NUM from bbs");
    		
    		if (!rs.next())
    			return 0;
    		pageNum = rs.getInt("NUM");
    		}
    		catch (Exception e) {
    			throw new Exception(e);
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
    		return (pageNum + 9) / 10;							// <c:forEach var="cnt" begin="1" end="${BBS_LIST.pageNum}">
    		}								
    
    private String toUnicode(String str) {   					// ISO-8859-1 문자열을 Unicode 문자열로 바꾸는 메서드
        if (str == null)
            return null;
        try {
            byte[] b = str.getBytes("ISO-8859-1");
            return new String(b);
        }
        catch (java.io.UnsupportedEncodingException uee) {
            System.out.println(uee.getMessage());
            return null;
        }
    }
}
