package springbook.sug.support;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import springtest.sug.domain.Group;
import springtest.sug.domain.Type;
import springtest.sug.domain.User;
import springtest.sug.support.MappedBeanPropertySqlParameterSource;

public class MappedBeanPropertySqlParameterSourceTest {
	private User user;
	
	@Before
	public void before() {
		user = new User(1, "name", "username", "password", Type.GUEST, new Group(1, "group"),
				new GregorianCalendar(2000, 1-1, 10).getTime(),
				new GregorianCalendar(2000, 2-1, 20).getTime(), 100);	
	}
	
	@Test
	public void beanPropertySqlParameterSource() {
		SqlParameterSource sps = new BeanPropertySqlParameterSource(user);
		assertThat((String)sps.getValue("name"), is("name"));
		assertThat((Integer)sps.getValue("id"), is(1));
		assertThat((Integer)sps.getValue("type.value"), is(3));
		assertThat((String)sps.getValue("group.name"), is("group"));
	}
	
	@Test
	public void mapMethod() {
		SqlParameterSource sps = new MappedBeanPropertySqlParameterSource(user)
									.map("type","type.value").map("group", "group.name");
		checkSource(sps);
	}
	
	@Test
	public void propertyMapConstructorParam() {
		Map<String, String> propertyMap = new HashMap<String, String>();
		propertyMap.put("type","type.value");
		propertyMap.put("group", "group.name");
		SqlParameterSource sps = new MappedBeanPropertySqlParameterSource(user, propertyMap);
		checkSource(sps);
	}
	
	@Test
	public void getReadablePropertyNames() {
		BeanPropertySqlParameterSource sps = new MappedBeanPropertySqlParameterSource(user);

		Map<String, String> propertyMap = new HashMap<String, String>();
		propertyMap.put("type","type.value");
		propertyMap.put("group", "group.name");
		assertThat(sps.getReadablePropertyNames().length, is(10));
		BeanPropertySqlParameterSource mappedSps = new MappedBeanPropertySqlParameterSource(user, propertyMap);
				
		
		assertThat(mappedSps.getReadablePropertyNames().length, is(12));
		
//			System.out.print("mappedSps.getReadablePropertyNames().length = ");
//			System.out.println( mappedSps.getReadablePropertyNames().length );
	
		assertThat(mappedSps.getReadablePropertyNames()[11], is("group"));
		
//			System.out.print(" mappedSps.getReadablePropertyNames()[11] = ");
//			System.out.println( mappedSps.getReadablePropertyNames()[11] );
		
		assertThat(mappedSps.getReadablePropertyNames()[10], is("type"));

//			System.out.print(" mappedSps.getReadablePropertyNames()[10] = ");
//			System.out.println( mappedSps.getReadablePropertyNames()[10] );

			
			System.out.print(" mappedSps.getReadablePropertyNames()[0] = ");
			System.out.println( mappedSps.getReadablePropertyNames()[0] );
			System.out.print(" mappedSps.getReadablePropertyNames()[1] = ");
			System.out.println( mappedSps.getReadablePropertyNames()[1] );
			System.out.print(" mappedSps.getReadablePropertyNames()[2] = ");
			System.out.println( mappedSps.getReadablePropertyNames()[2] );
			System.out.print(" mappedSps.getReadablePropertyNames()[3] = ");
			System.out.println( mappedSps.getReadablePropertyNames()[3] );
			System.out.print(" mappedSps.getReadablePropertyNames()[4] = ");
			System.out.println( mappedSps.getReadablePropertyNames()[4] );
			System.out.print(" mappedSps.getReadablePropertyNames()[5] = ");
			System.out.println( mappedSps.getReadablePropertyNames()[5] );
			System.out.print(" mappedSps.getReadablePropertyNames()[6] = ");
			System.out.println( mappedSps.getReadablePropertyNames()[6] );
			System.out.print(" mappedSps.getReadablePropertyNames()[7] = ");
			System.out.println( mappedSps.getReadablePropertyNames()[7] );
			System.out.print(" mappedSps.getReadablePropertyNames()[8] = ");
			System.out.println( mappedSps.getReadablePropertyNames()[8] );
			System.out.print(" mappedSps.getReadablePropertyNames()[9] = ");
			System.out.println( mappedSps.getReadablePropertyNames()[9] );
			System.out.print(" mappedSps.getReadablePropertyNames()[10] = ");
			System.out.println( mappedSps.getReadablePropertyNames()[10] );
			System.out.println(" mappedSps.getReadablePropertyNames()[10] = ");

			System.out.println( "==========================================");
		
			System.out.print(" sps.getReadablePropertyNames()[0] = ");
			System.out.println( sps.getReadablePropertyNames()[0] );
			System.out.print(" sps.getReadablePropertyNames()[1] = ");
			System.out.println( sps.getReadablePropertyNames()[1] );
			System.out.print(" sps.getReadablePropertyNames()[2] = ");
			System.out.println( sps.getReadablePropertyNames()[2] );
			System.out.print(" sps.getReadablePropertyNames()[3] = ");
			System.out.println( sps.getReadablePropertyNames()[3] );
			System.out.print(" sps.getReadablePropertyNames()[4] = ");
			System.out.println( sps.getReadablePropertyNames()[4] );
			System.out.print(" sps.getReadablePropertyNames()[5] = ");
			System.out.println( sps.getReadablePropertyNames()[5] );
			System.out.print(" sps.getReadablePropertyNames()[6] = ");
			System.out.println( sps.getReadablePropertyNames()[6] );
			System.out.print(" sps.getReadablePropertyNames()[7] = ");
			System.out.println( sps.getReadablePropertyNames()[7] );
			System.out.print(" sps.getReadablePropertyNames()[8] = ");
			System.out.println( sps.getReadablePropertyNames()[8] );
			System.out.print(" sps.getReadablePropertyNames()[9] = ");
			System.out.println( sps.getReadablePropertyNames()[9] );

			System.out.println( "==========================================");

			System.out.print(" mappedSps.getValue(name) = ");
			System.out.println( mappedSps.getValue("name") );
			System.out.print(" mappedSps.getValue(modified) = ");
			System.out.println( mappedSps.getValue("modified") );
			System.out.print(" mappedSps.getValue(created) = ");
			System.out.println( mappedSps.getValue("created") );
			System.out.print(" sps.getValue(name) = ");
			System.out.println( sps.getValue("name") );
			
	}

	
	private void checkSource(SqlParameterSource sps) {
		assertThat((String)sps.getValue("name"), is("name"));
		assertThat((Integer)sps.getValue("id"), is(1));
		assertThat((Integer)sps.getValue("type"), is(3));
		assertThat((String)sps.getValue("group"), is("group"));
	}
}
