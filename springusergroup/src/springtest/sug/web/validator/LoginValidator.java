package springtest.sug.web.validator;

import javax.inject.Inject;
import javax.inject.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import springtest.sug.domain.Login;
import springtest.sug.domain.User;
import springtest.sug.service.UserService;
import springtest.sug.web.security.LoginInfo;

@Component
public class LoginValidator implements Validator {
	private UserService userService;
	
	@Inject
	private Provider<LoginInfo> loginInfoProvider;		// https://stackoverflow.com/questions/16435117/when-to-use-javax-inject-provider-in-spring

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public boolean supports(Class<?> clazz) {
		return Login.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		Login login = (Login)target;
		User user = userService.findUser(login.getUsername());
		if (user == null || !user.getPassword().equals(login.getPassword())) {
			errors.reject("invalidLogin", "Invalid Login");
		}
		else {
			LoginInfo loginInfo = loginInfoProvider.get();		// Provider<LoginInfo> �� ������ LoginInfo �ν��Ͻ� loginInfoProvider�� 
																// Ư���޼ҵ峪 ��� �޼ҵ� ��ȯ����// ~.get().~
			loginInfo.save(user);								// �׷��� save() �� �����ü��־���.
		}
	}
}
