package springtest.sug.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;
import springtest.sug.boardjdbc.BBSJdbcList;
import springtest.sug.domain.Board;
import springtest.sug.support.EntityProxyFactory;
import springtest.sug.support.MappedBeanPropertySqlParameterSource;

/* ��  ¥ : 2015.05.20  
 * ������ : ä����
 * ��  �� : �ڹټ��� ���� �Խ����� Spring @MVC �����丵. */

@Repository
public class BoardDaoJdbc implements BoardDao {
	@Autowired private EntityProxyFactory entityProxyFactory;
	private SimpleJdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert boardInsert;
	private RowMapper<Board> rowMapper = 
		new RowMapper<Board>() {
			public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
				Board Board = new Board();
				Board.setSeqNo(rs.getInt("seqno"));
				Board.setTitle(rs.getString("title"));
				Board.setContent(rs.getString("content"));
				Board.setWriter(rs.getString("writer"));
				Board.setDate(rs.getDate("wdate"));
				Board.setTime(rs.getTime("wtime"));
				return Board;
			}
		};

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.boardInsert = new SimpleJdbcInsert(dataSource)
						.withTableName("bbs"); 
						//.usingGeneratedKeyColumns("seqno");		// ������ũ����Ʈ ����.
	}
	
	public Board add(Board board) {
		int newSeqNo;
		newSeqNo = this.jdbcTemplate.queryForInt("select max(seqno) as max_seqno from bbs") + 1;
		
		jdbcTemplate.update(
				"insert into bbs(seqno, title, content, writer, wdate, wtime) "
				+ "values( :seqno, :title, :content, :writer, :wdate, :wtime  )",
				new MapSqlParameterSource()
				.addValue("seqno", newSeqNo )
				.addValue("title", board.getTitle() )
				.addValue("content", board.getContent())
				.addValue("writer", board.getWriter())
				.addValue("wdate", board.getDate())
				.addValue("wtime", board.getTime())
				);
		return board;
	}
	
	public Board update(Board board) {

		System.out.println("board.getSeqNo() = "+ board.getSeqNo());
		System.out.println("board.getTitle() = "+ board.getTitle());
		System.out.println("board.getContent() = "+ board.getContent());
		System.out.println("board.getWriter() = "+ board.getWriter());
		System.out.println("board.getDate() = "+ board.getDate());
		System.out.println("board.getTime() = "+ board.getTime());
		
//		int affected = jdbcTemplate.update(							// #
		jdbcTemplate.update(
				"update bbs set " +
				"title = :title, " + 
				"content = :content, " + 
				"writer = :writer, " + 
				"wdate = :wdate, " + 
				"wtime = :wtime " + 
				"where seqno = :seqno",
//				new UserBeanPropertySqlParameterSource(board));		// # db�� ��¥ Ÿ���� �ٲ��
				new MapSqlParameterSource()
				.addValue("seqno", board.getSeqNo() )
				.addValue("title", board.getTitle() )
				.addValue("content", board.getContent())
				.addValue("writer", board.getWriter())
				.addValue("wdate", board.getDate())
				.addValue("wtime", board.getTime())
				);
			
		return board;
	}
	
	public void delete(int seqno) {
		this.jdbcTemplate.update("delete from bbs where seqno = ?", seqno);
	}
	
	public int deleteAll() {
		return this.jdbcTemplate.update("delete from bbs");
	}
	
	public Board get(int seqno) {
		try {
		return this.jdbcTemplate.queryForObject("select * from bbs where seqno = ?", 
				this.rowMapper, seqno);
		}
		catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Board> search(String writer) {
		return this.jdbcTemplate.query("select * from bbs where writer like ?", 
				this.rowMapper, "%" + writer + "%");
	}
	
	public List<Board> getAll() {
		return this.jdbcTemplate.query("select * from bbs order by seqNo desc", 
				this.rowMapper);
	}

	public long count() {
		return this.jdbcTemplate.queryForLong("select count(0) from bbs");
	}
	

	private static class UserBeanPropertySqlParameterSource extends MappedBeanPropertySqlParameterSource {
		public UserBeanPropertySqlParameterSource(Object object) {
			super(object);
			map("type", "type.value");		// type    <= type.value ���� // ex) insert into board(type, groupid) valuse(type.value, group.id)
			map("groupid", "group.id");		// groupid <= group.id   board ���̺��� groupid �� group.id �� �����Ѵ�. //  MapSqlParameterSource ����
											// ���� �� Ű���� ��ġ�ϴ� ġȯ�ڿ� ���� ���� �ڵ����� ���Եȴ�.
											// group �� ����Ʈ ������ ������� ����Ʈ �ڽ���. 
		}
	}

	
    public BBSJdbcList boardListSet(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
    																		// �Խ��� ����Ʈ �� ������������� ����¡���� ����
    	String strFirstSeqNo = request.getParameter("FIRST_SEQ_NO");
    	String strLastSeqNo = request.getParameter("LAST_SEQ_NO");
    	String strPageNo = request.getParameter("PAGE_NO");
    	List<Board> list;
    	List<Board> ItHasNextPage ;
    	
    	BBSJdbcList boardListSet = new BBSJdbcList() ;
    	
    	int HasPrevPage ;
    	int size;
    	int HasNextPage ;
    	int pagechk ;   
    	int pagelast = 0;
    	int pagesize = 10;
    	int chksize = 0;
    	
    	
    	if (strFirstSeqNo != null) {
    		list = readPrevPage(Integer.parseInt(strFirstSeqNo));
    		boardListSet = boardSeqSet(list, boardListSet);					// �信 �ѷ��� ����¡ ���� ����.
    		
    		chksize = Integer.parseInt(strFirstSeqNo) + pagesize;
        	HasPrevPage = jdbcTemplate.queryForInt("select count(*) from bbs where seqNo > " + chksize +
        			" order by seqno asc;"  );								// ������������(pagesize)��ŭ ���ؼ� �Խ������� Ȯ��.
        	
        	if (HasPrevPage==0) 
        		boardListSet.setFirstPage(true);							// ���̻� ���� ��� ó�� �������� ����.
     		
    	}
    	else if (strLastSeqNo != null) {
    		list = readNextPage(Integer.parseInt(strLastSeqNo));
    		boardListSet = boardSeqSet(list, boardListSet);
    		
        	HasNextPage = jdbcTemplate.queryForInt("select count(*) from bbs where seqNo < " + (Integer.parseInt(strLastSeqNo)) +
        			" order by seqno desc;");
        	
        	if (HasNextPage < (pagesize + 1) ) 
        		boardListSet.setLastPage(true);								// ������ �������� ����.
    		
    	}
    	else if (strPageNo != null) {
    		list = readPage(Integer.parseInt(strPageNo));
    		
    		boardListSet = boardSeqSet(list, boardListSet);    		

        	pagechk = (Integer.parseInt(strPageNo)) + 1;					// ���� ��������
        	if (pagechk > 1)
        		pagelast = (pagechk - 1) * pagesize;						// ���� ������ ������ Ȯ��
             	
        	ItHasNextPage = jdbcTemplate.query("select * from bbs order by seqno desc limit " + pagelast + ", 10 ;", rowMapper);
        	size = ItHasNextPage.size();
        	if (size==0) 
        		boardListSet.setLastPage(true);					

        	if (Integer.parseInt(strPageNo)==1)        	
        		boardListSet.setFirstPage(true);
     		
    	}
    	else {
	    	list = readNextPage(Integer.MAX_VALUE);
    		boardListSet = boardSeqSet(list, boardListSet);    		
	    	
	    	boardListSet.setFirstPage(true);
    	}
    	boardListSet.setPageNum(readPageNum());        
       
        
		return boardListSet; 
    }
  
	
    public List<Board> jdbcBbsList(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
    	String strFirstSeqNo = request.getParameter("FIRST_SEQ_NO");
    	String strLastSeqNo = request.getParameter("LAST_SEQ_NO");
    	String strPageNo = request.getParameter("PAGE_NO");
    	List<Board> list;

    	
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
    	}
    	       
		return list; 
    }
    
    private List<Board> readPrevPage(int lowerSeqNo) throws Exception {		// DB�κ��� ���� �������� �д� �޼���
    	int pagesize = 10;
    	int limitsize = 0;

    	limitsize = lowerSeqNo + pagesize + 1 ;								// ���� ���������� ����

    	return jdbcTemplate.query("select * from bbs where (seqNo < " + limitsize  + 
    			" and seqNo > " + lowerSeqNo + " ) order by seqno desc;", rowMapper);	
    	// sqlEx) // select * from bbs where (seqNo < 22 and seqNo > 12 )  order by seqno desc ;
    }
  
    
    private  List<Board> readNextPage(int upperSeqNo) throws Exception {	// DB�κ��� ���� �������� �д� �޼���	// seqno �� �� ���� ������
     	
    	return jdbcTemplate.query("select * from bbs where seqNo < " + upperSeqNo + " order by seqno desc limit 10;", rowMapper);
    }
    
    
   	private  List<Board>  readPage(int pageNo) throws Exception {			// DB�κ��� Ư�� �������� �д� �޼���
    	int page = 0 ;
    	int pagesize = 10;
   	
    	if (pageNo > 1)
    		page = (pageNo - 1) * pagesize;
    	
    	return jdbcTemplate.query("select * from bbs order by seqno desc limit " + page + ", " + pagesize + " ;", 
				rowMapper);    	
    }
   	
    private  int readPageNum() throws Exception {							// DB�κ��� ������ ���� �д� �޼���
    	int pageNum;
    	int pagesize = 10;
    	long pnum;

   		//pnum = jdbcTemplate.queryForLong("select count(*) as NUM from bbs");
   		pnum = jdbcTemplate.queryForLong("select count(0) from bbs");
   		if (pnum==0)
   			return 0;

   		pageNum = (int) pnum;
    		
   		return (pageNum + 9) / pagesize;									// ��������ȣ(pageNum) + �ִ��ܿ��Խù� / ������������ 
    	}


    
	private BBSJdbcList boardSeqSet(List<Board> list, BBSJdbcList boardListSet){// �信 �ѷ��� ����¡ ���� ����.
		int size;
    	int firstSeq = 0;
    	int lastSeq = 0;
    	Board boardFirstSeq = new Board() ;									// FirstSeq �� �ޱ����� ������Ʈ
    	Board boardLastSeq = new Board() ;

    	size = list.size();
		
		boardFirstSeq = list.get(size -1);									// Board ����Ʈ�� ù��° ������Ʈ ��ȯ
		boardLastSeq = list.get(0);											// Board ����Ʈ�� ������ ������Ʈ ��ȯ
		firstSeq	= boardFirstSeq.getSeqNo();
		lastSeq		= boardLastSeq.getSeqNo();
		
		boardListSet.setFirstSeq(firstSeq);									// FirstSeq ����
		boardListSet.setLastSeq(lastSeq);	
    	boardListSet.setListSize(size);										// ����Ʈ ������
		
		return boardListSet;
	}

	
	public Board findBoard(HttpServletRequest request, HttpServletResponse response) {
        String strSeqNo = request.getParameter("seqNo");
	    int seqNo = 0 ;
	    if (strSeqNo != null) {
	    	seqNo = Integer.parseInt(strSeqNo);
	    }
		
    	return this.get(seqNo);
	}
	
	
}


















/*
 * 
 
 	public Board add(Board board) {
		int generatedId = this.boardInsert.executeAndReturnKey(	// SimpleJdbcInsert / executeAndReturnKey 
																// Execute the insert using the values passed in and return the generated key. 
																// * This requires that the name of the columns with auto generated keys have been specified
																// executeAndReturnKey 
																// �Ѱܹ��� board �μ�Ʈ�۾�(SimpleJdbcInsert)�� ���� 
																// ���̺�(withTableName("bbs"))��, �ڵ����� ������ ������ũ����Ʈ
																// ���� �����Ͽ� �Է�. 
				new UserBeanPropertySqlParameterSource(board)).intValue();
		board.setSeqNo(generatedId);
		return board;
	}
	
 
 
 
 
 
 */





