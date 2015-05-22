package springtest.sug.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import springtest.sug.domain.User;
import springtest.sug.service.UserService;

@Component
public class UsernameValidator implements Validator {
	@Autowired 
	private UserService userService;

	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		User formUser = (User)target;
		User user = this.userService.findUser(formUser.getUsername());
		if (user != null && user.getId() != formUser.getId()) errors.rejectValue("username", "duplicateUsername");		
				// 아이디가 다른데 이름이 같은 user 가 있으면 errors 리젝트.
	}

}
