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

import springtest.sug.domain.Group;
import springtest.sug.domain.Type;
import springtest.sug.domain.User;
import springtest.sug.service.GroupService;
import springtest.sug.service.UserService;
import springtest.sug.web.security.LoginInfo;
import springtest.sug.web.validator.UsernameValidator;

@Controller
@RequestMapping("/user/edit/{id}")
@SessionAttributes("user")
public class UserEditController {
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
	
	@ModelAttribute					// ${groupList}
	public List<Group> groups() {
		return this.groupService.getAll(); 
	}

	@ModelAttribute					// ${typeList}
	public Type[] types() {	
		return Type.values(); 
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String showform(@PathVariable int id, ModelMap model) {
		model.addAttribute(this.userService.get(id));
		return "user/edit";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String edit(@ModelAttribute @Valid User user, BindingResult result, SessionStatus status) {
		this.usernameValidator.validate(user, result);
		if (result.hasErrors()) {
			return "user/edit";
		}
		else {
			this.userService.update(user);
			status.setComplete();
			return "redirect:../list";
		}
	}
}
