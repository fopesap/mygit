package springtest.sug.dao;

import springtest.sug.domain.User;

public interface UserDao extends GenericDao<User> {
	User findUser(String username);
}