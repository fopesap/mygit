package springbook.sug.web.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import springtest.sug.domain.Group;
import springtest.sug.service.GroupService;
import springtest.sug.web.converter.GroupConverter;

public class GroupConverterTest {
	@Test
	public void groupToString() {
		GroupConverter.GroupToString converter = new GroupConverter.GroupToString();
		assertThat(converter.convert(new Group(1, "")), is("1"));
		assertThat(converter.convert(new Group(10, "")), is("10"));
		assertThat(converter.convert(null), is(""));
	}
	
	@Test
	public void stringToGroup() {
		GroupConverter.StringToGroup converter = new GroupConverter.StringToGroup();
		GroupService groupService =  mock(GroupService.class);
		Group group1 = new Group(1, "");
		Group group10 = new Group(10, "");
		when(groupService.get(1)).thenReturn(group1);		// 1. groupService.get(1) ==> 호출되면  개체 group1 리턴
		when(groupService.get(10)).thenReturn(group10);		// 2. groupService.get(10) ==> 호출되면  개체 group10 리턴
		converter.setGroupService(groupService);
		
		assertThat(converter.convert("1"), is(group1));		// 3. 리턴되는 값과 비교하여 
		assertThat(converter.convert("10"), is(group10));	// 4. GroupConverter.StringToGroup converter 가 작동하는지 확인할수있다.
	}
}
