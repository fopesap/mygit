package springtest.sug.service;


import java.io.IOException;
import java.util.List;








import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import springtest.sug.boardjdbc.BBSJdbcList;
import springtest.sug.domain.Board;


public interface BoardService extends GenericService<Board> {
	
	List<Board> getAll();
	Board findBoard(HttpServletRequest request, HttpServletResponse response);
	List<Board> jdbcBbsList(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception ;
	BBSJdbcList boardListSet(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception;
	
}
