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
		e.setSuperclass(clazz);						// clazz �� ��ӹ޾� ����Ŭ������ ����� Proxy ����.
		e.setCallback(new MethodInterceptor() {		// intercept // �������� �޼ҵ带 ����Ͽ� ���Ͻ� �޼ҵ尡 "this" �޼ҵ带 ��ȯ(call)�Ѵ�. 
			private T entity;
			public Object intercept(Object obj, Method method, Object[] args,	// obj - "this", the enhanced object 
																				// method - intercepted Method
																				// args - argument array; primitive types are wrapped
					MethodProxy proxy) throws Throwable {						// proxy - used to invoke super (non-intercepted method)
																					// ; may be called as many times as needed						
				if (method.getName().equals("getId")) {		// method //  clazz�� ����־� �������� ����Ŭ������ �޼ҵ尡 �ȴ�. 
					return id;
				}
				else {
					if (entity == null) {
						entity = dao.get(id);				// �Ѱܹ��� dao �� ���ؼ� �׷��� get
					}
					return proxy.invoke(entity, args);		// �������� �޼ҵ带 ��ȯ�Ͽ� ���� Ÿ���� �ٸ� ������Ʈ�� ��ȯ.  
				}
			}
		});
		
		return (T)e.create();
	}
}
