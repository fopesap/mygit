package springtest.sug.service;

import java.util.List;

import springtest.sug.domain.Group;

public interface GroupService extends GenericService<Group> {
	List<Group> getAll();
}
