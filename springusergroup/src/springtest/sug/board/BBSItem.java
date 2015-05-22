package springtest.sug.board;


import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;
import springtest.sug.support.EntityProxyFactory;



@Repository
public class BBSItem {

	@Autowired private EntityProxyFactory entityProxyFactory;
	private SimpleJdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert bbsInsert;

	private int seqNo;         // 순번
    private String title;      // 제목
    private String content;    // 내용
    private String writer;     // 작성자
    private Date date;         // 저장일자
    private Time time;         // 저장시각
    
    public BBSItem() {							// no-arg 생성자(파라미터가 없는 생성자)
    }
    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }
    public String getTitle() {
//        return toUnicode(title);
        return (title);
    }
   
    public String getContent() {
//        return toUnicode(content);
        return (content);
    }
	public String getWriter() {
//        return toUnicode(writer);
        return (writer);
    }
   
    public Date getDate() {
         return date;
    }
   
    public Time getTime() {
         return time;
    }

    public void setTitle(String title) {
		this.title = title;
	}
 	public void setContent(String content) {
		this.content = content;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	
    
    public void readDB(HttpServletRequest request, HttpServletResponse response) throws Exception { // 데이터베이스로부터 게시글을 읽는 메서드
        Connection conn = null;
        Statement stmt = null;
        
        String strSeqNo = request.getParameter("seqNo");
        int SeqNo = 0 ;
    	if (strSeqNo != null) {
    		SeqNo = Integer.parseInt(strSeqNo);
    	}

        
        try {
            Class.forName("org.apache.commons.dbcp.PoolingDriver");
            Class.forName("com.mysql.jdbc.Driver");
            
        	conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:/wdbpool");	// wdbpool.jocl 파일에 dbcp 풀 설정

            if (conn == null)
                throw new Exception("데이터베이스에 연결이 잘~~.");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bbs where seqNo = '" + SeqNo + "';");

            if (rs.next()) {
                title = rs.getString("title");		   // 제목//ResultSet rs 의 title의 값을 getString()메소드를 통해 가져온다.         
                									   // 메소드를 이용해도된다. setTitle(rs.getString("title"));
                content = rs.getString("content");     // 내용
                writer = rs.getString("writer");       // 작성자
                date = rs.getDate("wdate");            // 저장일자
                time = rs.getTime("wtime");            // 저장시각
            }
        }
        catch (Exception e) {
//            throw new ServletException(e);
            throw new ServletException(e);
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
    }
    private String toUnicode(String str) {    			// ISO-8859-1 문자열을 Unicode 문자열로 바꾸는 메서드
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

