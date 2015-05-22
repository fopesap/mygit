package springtest.sug.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import springtest.sug.board.BBSItem;
import springtest.sug.board.BBSList;
import springtest.sug.board.BBSListServlet;
import springtest.sug.board.BBSPostServlet;
import springtest.sug.domain.User;
import springtest.sug.service.GroupService;
import springtest.sug.service.UserService;
import springtest.sug.web.security.LoginInfo;
import springtest.sug.web.security.SessionLoginInfo;
import springtest.sug.web.validator.UsernameValidator;

@Controller
@RequestMapping("/board")
@SessionAttributes("user")
public class BoardController {
	private GroupService groupService;
	private UserService userService;
	private UsernameValidator usernameValidator;
	private @Inject Provider<LoginInfo> loginInfoProvider;
	
	@Autowired
	public void init(GroupService groupService, UserService userService, UsernameValidator usernameValidator) {
		this.groupService = groupService;
		this.userService = userService;
		this.usernameValidator = usernameValidator;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id", "logins");
	}

	@ModelAttribute("currentUser")
	public User currentUser() {
		return loginInfoProvider.get().currentUser();
	}


	@RequestMapping("/bbs")													// board 
	public String board(ModelMap model) {
		model.addAttribute(this.userService.getAll());
		return "board/bbs";
	}
	
	@RequestMapping("/list")							 
	public String boardlist(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		BBSList list1 = new BBSList();
		model.addAttribute(this.userService.getAll());
		list1 = BBSListServlet.doGet1(request, response);
		request.setAttribute("BBS_LIST", list1);	
	
		return "board/list";
	}
	
	@RequestMapping("/view")							 
	public String boardview(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BBSItem itemview = new BBSItem();
		itemview.readDB(request, response);

		model.addAttribute(this.userService.getAll());
		request.setAttribute("bbsItem", itemview);	
		
		return "board/view";
	}
	
	@RequestMapping(value = "/input", method=RequestMethod.GET)
	public String form( ModelMap model) {
		model.addAttribute("user");

		return "board/input";
	}
	
	@RequestMapping(value = "/input", method=RequestMethod.POST)
//	public String input(@RequestParam String username, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception  { 
					// 폼에 파라메터 추가하여 받아와도 됨.	<input type="hidden" name="username" value="${currentUser.username}">
	public String input( ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception  { 

//		String username1 = (String) request.getAttribute("username");
		model.addAttribute(this.userService.getAll());
		User user = loginInfoProvider.get().currentUser();
		String username2 = user.getUsername();
	
		BBSPostServlet.post1(username2, request, response);
		
		return "redirect:../board/list";
	}

}





















/*
package springtest.sug.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import springtest.sug.board.BBSItem;
import springtest.sug.board.BBSList;
import springtest.sug.board.BBSListServlet;
import springtest.sug.board.BBSPostServlet;
import springtest.sug.domain.User;
import springtest.sug.service.GroupService;
import springtest.sug.service.UserService;
import springtest.sug.web.security.LoginInfo;
import springtest.sug.web.security.SessionLoginInfo;
import springtest.sug.web.validator.UsernameValidator;

@Controller
@RequestMapping("/board")
@SessionAttributes("user")
public class BoardController {
	private GroupService groupService;
	private UserService userService;
	private UsernameValidator usernameValidator;
	private @Inject Provider<LoginInfo> loginInfoProvider;
	
	@Autowired
	public void init(GroupService groupService, UserService userService, UsernameValidator usernameValidator) {
		this.groupService = groupService;
		this.userService = userService;
		this.usernameValidator = usernameValidator;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields("id", "logins");
	}

	@ModelAttribute("currentUser")
	public User currentUser() {
		return loginInfoProvider.get().currentUser();
	}


	@RequestMapping("/bbs")							// board 
	public String board(ModelMap model) {
		model.addAttribute(this.userService.getAll());
		return "board/bbs";
	}
	
	@RequestMapping("/list")							 
	public String boardlist(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
		BBSList list1 = new BBSList();
		model.addAttribute(this.userService.getAll());
		list1 = BBSListServlet.doGet1(request, response);
//		model.addAttribute(list1);
		request.setAttribute("BBS_LIST", list1);	
	
		return "board/list";
	}
	
	@RequestMapping("/view")							 
	public String boardview(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BBSItem itemview = new BBSItem();
		itemview.readDB(request, response);

		model.addAttribute(this.userService.getAll());
		request.setAttribute("bbsItem", itemview);	
		
		return "board/view";
	}
	
//	@RequestMapping(value = "/input/{id}", method=RequestMethod.GET)
	@RequestMapping(value = "/input", method=RequestMethod.GET)
	public String form( ModelMap model) {
//	public String form(@RequestParam int id, ModelMap model) {
//	public String form(@ModelAttribute User user, ModelMap model) {
//	public String form( ModelMap model) {

//		User user = new User();		
//		model.addAttribute("user");
		model.addAttribute("user");
//		model.addAttribute("user", this.userService.get(id));
	
//		model.addAttribute(this.userService.getAll());
//		model.addAttribute("username", this.user.getUsername());	

		return "board/input";
	}
	
	@RequestMapping(value = "/input", method=RequestMethod.POST)
//	public String input(@ModelAttribute User user, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
//	public String input(@ModelAttribute("currentUser") User user, @RequestParam(required=false) int id, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception  {
//	public String input(@RequestParam int id, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception  {
//	public String input(@RequestParam String username, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception  { 	// 				<input type="hidden" name="username" value="${currentUser.username}">
	public String input( ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception  { 
//		model.addAttribute("user", this.userService.get(id));
//		model.addAttribute("user");

		model.addAttribute(this.userService.getAll());
//		User user1 = (User)this.userService.get(id);		
//		String username = user.getUsername();
//		System.out.printf("username = ", username );
//		currentUser.name() ;
//		User user1 = currentUser();
//		String username = user1.getUsername();
//		currentUser.username;
//		String username2 
		User user = loginInfoProvider.get().currentUser();
		String username2 = user.getUsername();
		//(User)getModelAndView().getModel().get("user")).getEmail();
//		User user = (User) session.getAttribute("user");
//		String username = user.getUsername();
//		String username1 = (String) request.getAttribute("username");
				
		BBSPostServlet.post1(username2, request, response);
		
		return "redirect:../board/list";
		
		
	}

}

 
 
*/
