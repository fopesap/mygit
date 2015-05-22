package springtest.sug.support;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import springtest.sug.dao.GenericDao;

public class CglibEntityProxyFactory implements EntityProxyFactory {
	@SuppressWarnings("unchecked")
	public <T> T createProxy(Class<T> clazz, final GenericDao<T> dao, final int id) {
		Enhancer e = new Enhancer();
		e.setSuperclass(clazz);						// clazz 를 상속받아 슈퍼클래스로 만들며 Proxy 생성.
		e.setCallback(new MethodInterceptor() {		// intercept // 오리지날 메소드를 대신하여 프록시 메소드가 "this" 메소드를 소환(call)한다. 
			private T entity;
			public Object intercept(Object obj, Method method, Object[] args,	// obj - "this", the enhanced object 
																				// method - intercepted Method
																				// args - argument array; primitive types are wrapped
					MethodProxy proxy) throws Throwable {						// proxy - used to invoke super (non-intercepted method)
																					// ; may be called as many times as needed						
				if (method.getName().equals("getId")) {		// method //  clazz를 집어넣어 만들어놓은 슈퍼클래스의 메소드가 된다. 
					return id;
				}
				else {
					if (entity == null) {
						entity = dao.get(id);				// 넘겨받은 dao 를 통해서 그룹을 get
					}
					return proxy.invoke(entity, args);		// 오리지날 메소드를 변환하여 같은 타입의 다른 오브젝트로 반환.  
				}
			}
		});
		
		return (T)e.create();
	}
}
