package springtest.sug.web;



import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import springtest.sug.domain.Board;
import springtest.sug.domain.User;
import springtest.sug.service.BoardService;
import springtest.sug.web.security.LoginInfo;
import springtest.sug.web.validator.BoardValidator;


@Controller
@RequestMapping("/bbsjdbc/edit/{seqNo}")
@SessionAttributes("user")
public class BoardJdbcEditController {
	private BoardService boardService;
	private BoardValidator boardValidator; 
	private @Inject Provider<LoginInfo> loginInfoProvider;
	
	@Autowired
	public void init(BoardService boardService, BoardValidator boardValidator) {
		this.boardService = boardService;
		this.boardValidator = boardValidator;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
//		binder.setDisallowedFields("id", "logins");
	}
	
	@RequestMapping(method=RequestMethod.GET)									// 세션검증 필요 // 운영자와 글쓴이만 수정가능하도록.
	public String showform(@PathVariable int seqNo, ModelMap model) {
		model.addAttribute(this.boardService.get(seqNo));
		return "bbsjdbc/jdbcEdit";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String edit(@ModelAttribute @Valid Board board, BindingResult result, SessionStatus status) {
		this.boardValidator.validate(board, result);
		if (result.hasErrors()) {
			return "bbsjdbc/edit";
		}
		else {
			this.boardService.update(board);
			status.setComplete();
			return "redirect:../list";
		}
	}	
	
	
}


