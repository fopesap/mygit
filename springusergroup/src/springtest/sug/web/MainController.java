package springtest.sug.web;

import javax.inject.Inject;
import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;







import springtest.sug.domain.User;
import springtest.sug.service.UserService;
import springtest.sug.web.security.LoginInfo;

@Controller
@RequestMapping
public class MainController {
	private @Inject Provider<LoginInfo> loginInfoProvider;
	
	@Autowired 
	private UserService userService;
	
	@RequestMapping("/welcome")
	public void welcome() {
	}

/*	
	@RequestMapping("/welcome")
	public String welcome(ModelMap model) {
		model.addAttribute(this.userService.getAll());
		return "/welcome";
	}
*/	
	
/*	
	@RequestMapping("/welcome/{id}")
	public String welcome(@PathVariable int id, ModelMap model) {
		model.addAttribute(this.userService.get(id));
		model.addAttribute("id", id);
		return "/welcome";
	}
*/	
	
	
	
	
//	@RequestMapping("/list")
//	public String list(ModelMap model) {
//		model.addAttribute(this.userService.getAll());
//		return "user/list";
//	}
	
	
	
	@RequestMapping("/logout")
	public String logout() {
		loginInfoProvider.get().remove();
		return "redirect:login";
	}
	
	@RequestMapping("/accessdenied")
	public String notlogin() {
		return "/accessdenied";
	}
}
