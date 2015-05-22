package springtest.sug.service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springtest.sug.boardjdbc.BBSJdbcList;
import springtest.sug.dao.BoardDao;
import springtest.sug.domain.Board;



@Service
@Transactional
public class BoardServiceImpl implements BoardService {
	private BoardDao boardDao;
	
	@Autowired
	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	public Board add(Board board) {
		board.initDates();
		return this.boardDao.add(board);
	}

	public void delete(int id) {
		this.boardDao.delete(id);
	}

	public Board get(int id) {
		return this.boardDao.get(id);
	}

	public Board update(Board board) {
		board.setModified();
		return this.boardDao.update(board);
	}

	public Board findBoard(HttpServletRequest request, HttpServletResponse response) {
		return this.boardDao.findBoard(request, response);
	}

	public List<Board> getAll() {
		return this.boardDao.getAll();
	}

	public List<Board> jdbcBbsList(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		return this.boardDao.jdbcBbsList(request, response);
	}

	
	public BBSJdbcList boardListSet(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		return this.boardDao.boardListSet(request, response);
	}



	
}
