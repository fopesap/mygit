package springtest.sug.web;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import springtest.sug.domain.Login;
import springtest.sug.service.UserService;
import springtest.sug.web.security.LoginInfo;
import springtest.sug.web.validator.LoginValidator;

@Controller
@RequestMapping("/login")
@SessionAttributes("login") 
public class LoginController {
	private LoginValidator loginValidator;
	private UserService userService;
	private Provider<LoginInfo> loginInfoProvider;

	@Inject 	
	public void setLoginInfoProvider(Provider<LoginInfo> loginInfoProvider) {
		this.loginInfoProvider = loginInfoProvider;
	}

	@Autowired
	public void init(LoginValidator loginValidator, UserService userService) {
		this.loginValidator = loginValidator;		// @@ Valdator
		this.userService = userService;
	}

	@RequestMapping(method=RequestMethod.GET)
	public String showform(ModelMap model) {
		model.addAttribute(new Login());
		return "login";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String login(@ModelAttribute @Valid Login login, BindingResult result, SessionStatus status) {
			// @ModelAttribute + @Valid // 모델을 바인딩하는 바인더가,   등록된 @@ Valdator//loginValidator 를 이용해 모델을 검증하고
			// 자동으로 결과를 BindingResult 에 넣어서 전달.
		if (result.hasErrors()) return "login";
		
		this.loginValidator.validate(login, result);
		if (result.hasErrors()) {
			return "login";
		}
		else {
			userService.login(loginInfoProvider.get().currentUser());
			status.setComplete();
			return "redirect:user/list";
		}
	}
}
