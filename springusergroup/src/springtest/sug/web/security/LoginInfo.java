package springtest.sug.web.security;

import java.util.Date;

import springtest.sug.domain.User;

public interface LoginInfo {
	void save(User user);
	void remove();
	boolean isLoggedIn();
	User currentUser();
	Date getLoginTime();
}
