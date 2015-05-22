package springtest.sug.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import springtest.sug.boardjdbc.BBSJdbcList;
import springtest.sug.domain.Board;
import springtest.sug.domain.User;
import springtest.sug.service.BoardService;
import springtest.sug.service.GroupService;
import springtest.sug.service.UserService;
import springtest.sug.web.security.LoginInfo;
import springtest.sug.web.security.SessionLoginInfo;
import springtest.sug.web.validator.BoardValidator;
import springtest.sug.web.validator.UsernameValidator;



@Controller
@RequestMapping("/bbsjdbc")
@SessionAttributes("user")
public class BoardJdbcController {
	private UserService userService;
	private BoardService boardService;
	private BoardValidator boardValidator; 
	private @Inject Provider<LoginInfo> loginInfoProvider;
	
	@Autowired
	public void init(GroupService groupService, UserService userService, BoardService boardService, BoardValidator boardValidator) {
		this.userService = userService;
		this.boardService = boardService;
		this.boardValidator = boardValidator;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
//		binder.setRequiredFields("title","content");
	}

	@ModelAttribute("currentUser")
	public User currentUser() {
		return loginInfoProvider.get().currentUser();
	}

	@RequestMapping("/bbs")														// board 
	public String board(ModelMap model) {
		model.addAttribute(this.userService.getAll());
		return "bbsjdbc/jdbcbbs";
	}
	
	@RequestMapping("/list")							 
	public String boardList(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		BBSJdbcList listset =  new BBSJdbcList();

		model.addAttribute(this.boardService.jdbcBbsList(request, response));	// 게시판 리스트 모델에 전달.

		listset = this.boardService.boardListSet(request, response);			// 1. 게시판 리스트 중 페이지사이즈등 페이징정보를 받아서
		model.addAttribute("boardListSet", listset);							// 2. 모델에 전달.

//		request.setAttribute("boardListSet", listset);							// 리퀘스트로 전달.		
		
		return "bbsjdbc/jdbclist";
	}  
	
	@RequestMapping("/view")							 
	public String boardView(Board board, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		model.addAttribute(board);
		model.addAttribute(this.boardService.findBoard(request, response));
		
		return "bbsjdbc/jdbcview";
	}
	
	@RequestMapping(value = "/input", method=RequestMethod.GET)
	public String form( Board board, ModelMap model) {
		model.addAttribute(board);
		
		return "bbsjdbc/jdbcinput";
	}
	
	@RequestMapping(value = "/input", method=RequestMethod.POST)
	public String input(@ModelAttribute @Valid Board board, BindingResult result, ModelMap model, 
						SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws Exception  {
		User user = loginInfoProvider.get().currentUser();
		String username = user.getUsername();
		board.setWriter(username);

		this.boardValidator.validate(board, result);
		
		if (result.hasErrors()) {												// 오류가 있을때 리턴.
			return "redirect:../bbsjdbc/input";
		}
		else {
			this.boardService.add(board);			
			status.setComplete();
			return "redirect:../bbsjdbc/list";
		}
	}
	
	
	@RequestMapping("/delete/{seqNo}")								// 삭제시 세션및 권한여부 확인필요.
	public String delete(@PathVariable int seqNo) {
		this.boardService.delete(seqNo);
		return "bbsjdbc/jdbcDeleted";
	}	



}

















/*
 
 
 	
 	@RequestMapping(value = "/input", method=RequestMethod.GET)
	public String form(ModelMap model) {
		model.addAttribute("user");
		return "bbsjdbc/jdbcinput";
	}
	
	@RequestMapping(value = "/input", method=RequestMethod.POST)
	public String input( ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception  { 
		model.addAttribute(this.userService.getAll());
		User user = loginInfoProvider.get().currentUser();
		String username2 = user.getUsername();
		BBSPostDo.post1(username2, request, response);
		
		return "redirect:../bbsjdbc/jdbclist";
	}
 
 
 
 */

