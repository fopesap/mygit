package springtest.sug.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import springtest.sug.domain.Board;
import springtest.sug.service.BoardService;


@Component
public class BoardValidator implements Validator {
	@Autowired 
	private BoardService boardService;

	public boolean supports(Class<?> clazz) {
		return Board.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		Board formBoard = (Board)target;
		String boardTitle = formBoard.getTitle();
		String boardContent = formBoard.getContent();
		
		if (boardTitle == null || boardContent == null) errors.rejectValue("title", "needtitle");		
				//입력값 체크
	}

}
