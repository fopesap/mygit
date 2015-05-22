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

/* 날  짜 : 2015.05.20  
 * 개발자 : 채범석
 * 내  용 : 자바서블릿 심플 게시판을 Spring @MVC 리팩토링. */

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
						//.usingGeneratedKeyColumns("seqno");		// 오토인크리먼트 사용시.
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
//				new UserBeanPropertySqlParameterSource(board));		// # db의 날짜 타입을 바꿔야
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
			map("type", "type.value");		// type    <= type.value 맵핑 // ex) insert into board(type, groupid) valuse(type.value, group.id)
			map("groupid", "group.id");		// groupid <= group.id   board 테이블의 groupid 에 group.id 를 맵핑한다. //  MapSqlParameterSource 참조
											// 맵의 각 키값과 일치하는 치환자에 맵의 값이 자동으로 삽입된다.
											// group 의 디폴트 정보는 등록폼의 셀렉트 박스에. 
		}
	}

	
    public BBSJdbcList boardListSet(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
    																		// 게시판 리스트 중 페이지사이즈등 페이징정보 생성
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
    		boardListSet = boardSeqSet(list, boardListSet);					// 뷰에 뿌려줄 페이징 정보 셋팅.
    		
    		chksize = Integer.parseInt(strFirstSeqNo) + pagesize;
        	HasPrevPage = jdbcTemplate.queryForInt("select count(*) from bbs where seqNo > " + chksize +
        			" order by seqno asc;"  );								// 페이지사이즈(pagesize)만큼 더해서 게시판유무 확인.
        	
        	if (HasPrevPage==0) 
        		boardListSet.setFirstPage(true);							// 더이상 없을 경우 처음 페이지로 지정.
     		
    	}
    	else if (strLastSeqNo != null) {
    		list = readNextPage(Integer.parseInt(strLastSeqNo));
    		boardListSet = boardSeqSet(list, boardListSet);
    		
        	HasNextPage = jdbcTemplate.queryForInt("select count(*) from bbs where seqNo < " + (Integer.parseInt(strLastSeqNo)) +
        			" order by seqno desc;");
        	
        	if (HasNextPage < (pagesize + 1) ) 
        		boardListSet.setLastPage(true);								// 마지막 페이지로 지정.
    		
    	}
    	else if (strPageNo != null) {
    		list = readPage(Integer.parseInt(strPageNo));
    		
    		boardListSet = boardSeqSet(list, boardListSet);    		

        	pagechk = (Integer.parseInt(strPageNo)) + 1;					// 다음 페이지로
        	if (pagechk > 1)
        		pagelast = (pagechk - 1) * pagesize;						// 다음 페이지 사이즈 확인
             	
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
    
    private List<Board> readPrevPage(int lowerSeqNo) throws Exception {		// DB로부터 이전 페이지를 읽는 메서드
    	int pagesize = 10;
    	int limitsize = 0;

    	limitsize = lowerSeqNo + pagesize + 1 ;								// 다음 페이지까지 간격

    	return jdbcTemplate.query("select * from bbs where (seqNo < " + limitsize  + 
    			" and seqNo > " + lowerSeqNo + " ) order by seqno desc;", rowMapper);	
    	// sqlEx) // select * from bbs where (seqNo < 22 and seqNo > 12 )  order by seqno desc ;
    }
  
    
    private  List<Board> readNextPage(int upperSeqNo) throws Exception {	// DB로부터 다음 페이지를 읽는 메서드	// seqno 가 더 작은 페이지
     	
    	return jdbcTemplate.query("select * from bbs where seqNo < " + upperSeqNo + " order by seqno desc limit 10;", rowMapper);
    }
    
    
   	private  List<Board>  readPage(int pageNo) throws Exception {			// DB로부터 특정 페이지를 읽는 메서드
    	int page = 0 ;
    	int pagesize = 10;
   	
    	if (pageNo > 1)
    		page = (pageNo - 1) * pagesize;
    	
    	return jdbcTemplate.query("select * from bbs order by seqno desc limit " + page + ", " + pagesize + " ;", 
				rowMapper);    	
    }
   	
    private  int readPageNum() throws Exception {							// DB로부터 페이지 수를 읽는 메서드
    	int pageNum;
    	int pagesize = 10;
    	long pnum;

   		//pnum = jdbcTemplate.queryForLong("select count(*) as NUM from bbs");
   		pnum = jdbcTemplate.queryForLong("select count(0) from bbs");
   		if (pnum==0)
   			return 0;

   		pageNum = (int) pnum;
    		
   		return (pageNum + 9) / pagesize;									// 페이지번호(pageNum) + 최대잔여게시물 / 페이지사이즈 
    	}


    
	private BBSJdbcList boardSeqSet(List<Board> list, BBSJdbcList boardListSet){// 뷰에 뿌려줄 페이징 정보 셋팅.
		int size;
    	int firstSeq = 0;
    	int lastSeq = 0;
    	Board boardFirstSeq = new Board() ;									// FirstSeq 를 받기위한 오브젝트
    	Board boardLastSeq = new Board() ;

    	size = list.size();
		
		boardFirstSeq = list.get(size -1);									// Board 리스트의 첫번째 오브젝트 반환
		boardLastSeq = list.get(0);											// Board 리스트의 마지막 오브젝트 반환
		firstSeq	= boardFirstSeq.getSeqNo();
		lastSeq		= boardLastSeq.getSeqNo();
		
		boardListSet.setFirstSeq(firstSeq);									// FirstSeq 셋팅
		boardListSet.setLastSeq(lastSeq);	
    	boardListSet.setListSize(size);										// 리스트 사이즈
		
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
																// 넘겨받은 board 인서트작업(SimpleJdbcInsert)을 실행 
																// 테이블(withTableName("bbs"))에, 자동으로 생성된 오토인크리먼트
																// 값을 참조하여 입력. 
				new UserBeanPropertySqlParameterSource(board)).intValue();
		board.setSeqNo(generatedId);
		return board;
	}
	
 
 
 
 
 
 */





