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

	private int seqNo;         // ����
    private String title;      // ����
    private String content;    // ����
    private String writer;     // �ۼ���
    private Date date;         // ��������
    private Time time;         // ����ð�
    
    public BBSItem() {							// no-arg ������(�Ķ���Ͱ� ���� ������)
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
	
    
    public void readDB(HttpServletRequest request, HttpServletResponse response) throws Exception { // �����ͺ��̽��κ��� �Խñ��� �д� �޼���
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
            
        	conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:/wdbpool");	// wdbpool.jocl ���Ͽ� dbcp Ǯ ����

            if (conn == null)
                throw new Exception("�����ͺ��̽��� ������ ��~~.");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from bbs where seqNo = '" + SeqNo + "';");

            if (rs.next()) {
                title = rs.getString("title");		   // ����//ResultSet rs �� title�� ���� getString()�޼ҵ带 ���� �����´�.         
                									   // �޼ҵ带 �̿��ص��ȴ�. setTitle(rs.getString("title"));
                content = rs.getString("content");     // ����
                writer = rs.getString("writer");       // �ۼ���
                date = rs.getDate("wdate");            // ��������
                time = rs.getTime("wtime");            // ����ð�
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
    private String toUnicode(String str) {    			// ISO-8859-1 ���ڿ��� Unicode ���ڿ��� �ٲٴ� �޼���
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

