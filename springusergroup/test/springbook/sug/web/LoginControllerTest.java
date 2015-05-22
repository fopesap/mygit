package springbook.sug.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.inject.Inject;
import javax.inject.Provider;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.support.SessionStatus;

import springtest.sug.domain.Login;
import springtest.sug.service.UserService;
import springtest.sug.web.LoginController;
import springtest.sug.web.security.LoginInfo;
import springtest.sug.web.validator.LoginValidator;

/**
 * 컨트롤러 단위 테스트
 */
public class LoginControllerTest {
	LoginController loginController;
	LoginValidator loginValidator;
	UserService userService;
	BindingResult result;
	SessionStatus status;
	Login login;
	
	@Before
	public void before() {
		loginController = new LoginController();
		loginValidator = mock(LoginValidator.class);
		userService = mock(UserService.class);
		loginController.init(loginValidator, userService);
		result = mock(BindingResult.class);
		status = mock(SessionStatus.class);
		login = new Login();
	}
	
	@Test
	public void resultHasErrors() throws HttpSessionRequiredException {
		when(result.hasErrors()).thenReturn(true);
//		when(result.hasErrors()).thenReturn(false);

//=============================================================================//	
//		Provider<LoginInfo> provider = mock(Provider.class);
//		LoginInfo loginInfo = mock(LoginInfo.class); 
//		when(provider.get()).thenReturn(loginInfo);
//		loginController.setLoginInfoProvider(provider);
//=============================================================================//	
		
		String viewName = loginController.login(login, result, status);
		assertThat(viewName, is("login"));
		verify(loginValidator, times(0)).validate(login, result);
//		assertThat(viewName, is("redirect:user/list"));
//		verify(loginValidator, times(1)).validate(login, result);
	}
	
	@Test
	public void loginValidationFail() throws HttpSessionRequiredException {
//		when(result.hasErrors()).thenReturn(false, true, true);		// 처음에 false, 그 다음에(부터) true 반환
		when(result.hasErrors()).thenReturn(false, true, false );	// 처음에 false, 그 다음에 true, 그 다음에(부터) false 반환

//========================================================================//		
//		if (result.hasErrors())  
//			System.out.println(" result.hasErrors() = true " ) ;
//		else if (!(result.hasErrors())) System.out.println(" result.hasErrors() = false " )  ;
//========================================================================//		
		String viewName = loginController.login(login, result, status);				// 첫번째 result.hasErrors()  
		assertThat(viewName, is("login"));

		verify(loginValidator, times(1)).validate(login, result);					// 두번째 result.hasErrors()

//========================================================================//	
		Provider<LoginInfo> provider = mock(Provider.class);
		LoginInfo loginInfo = mock(LoginInfo.class); 
		when(provider.get()).thenReturn(loginInfo);
		loginController.setLoginInfoProvider(provider);
//========================================================================//	

		String viewName1 = loginController.login(login, result, status);			// 세번째 result.hasErrors() 
		assertThat(viewName1, is("redirect:user/list"));
	}
	
	@Test
	public void formSuccess() {
		when(result.hasErrors()).thenReturn(false);
		
		Provider<LoginInfo> provider = mock(Provider.class);
		LoginInfo loginInfo = mock(LoginInfo.class); 
		when(provider.get()).thenReturn(loginInfo);									// provider.get() 호출될때 mock LoginInfo 제공
		loginController.setLoginInfoProvider(provider);								// @Inject 대신 주입.
		
		String viewName = loginController.login(login, result, status);
		assertThat(viewName, is("redirect:user/list"));
		
		verify(status).setComplete();
		verify(loginInfo).currentUser();
		verify(userService).login(loginInfo.currentUser());
	}
}
