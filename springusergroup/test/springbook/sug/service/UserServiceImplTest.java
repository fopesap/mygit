package springbook.sug.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import springtest.sug.dao.UserDao;
import springtest.sug.domain.Group;
import springtest.sug.domain.Type;
import springtest.sug.domain.User;
import springtest.sug.service.UserServiceImpl;

public class UserServiceImplTest {
	private UserServiceImpl userServiceImpl;
	private UserDao mockUserDao;
	private User user1;
	
	@Before
	public void before() {
		userServiceImpl = new UserServiceImpl();
		mockUserDao = mock(UserDao.class);
		userServiceImpl.setUserDao(mockUserDao);
		user1 = new User(0, "name1", "username1", "password1", Type.ADMIN, new Group(1, "group1"), null, null, 1);
	}
	
	@Test 
	public void add() {
		userServiceImpl.add(user1);
		assertThat(user1.getCreated(), is(notNullValue()));
		assertThat(user1.getModified(), is(notNullValue()));
		verify(mockUserDao).add(user1);
	}
	
	@Test
	public void update() {
		userServiceImpl.update(user1);
		assertThat(user1.getModified(), is(notNullValue()));
		verify(mockUserDao).update(user1);			// 1. mockedDao 객체의 update() 메서드 호출 중에서 
													// 2. user1 객체를 인자로 전달받는 호출이 있었는지 여부를 확인.
	}
}



