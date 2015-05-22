package springtest.sug.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

import springtest.sug.domain.Group;
import springtest.sug.domain.Type;
import springtest.sug.domain.User;
import springtest.sug.support.EntityProxyFactory;
import springtest.sug.support.MappedBeanPropertySqlParameterSource;

@Repository
public class UserDaoJdbc implements UserDao {
	@Autowired private EntityProxyFactory entityProxyFactory;
	@Autowired private GroupDao groupDao;
	private SimpleJdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert userInsert;
	private RowMapper<User> rowMapper = 
		new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setType(Type.valueOf(rs.getInt("type")));
				user.setGroup(UserDaoJdbc.this.entityProxyFactory.createProxy(
							Group.class, groupDao, rs.getInt("groupid")));
				user.setCreated(rs.getDate("created"));
				user.setModified(rs.getDate("modified"));
				user.setLogins(rs.getInt("logins"));
				
				return user;
			}
		};

	@Autowired
	public void init(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.userInsert = new SimpleJdbcInsert(dataSource)
						.withTableName("users")
						.usingGeneratedKeyColumns("id");		// @
	}

	public User add(User user) {
		int generatedId = this.userInsert.executeAndReturnKey(	// SimpleJdbcInsert / executeAndReturnKey 
																// Execute the insert using the values passed in and return the generated key. 
																// @ This requires that the name of the columns with auto generated keys have been specified
																// executeAndReturnKey 
																// �Ѱܹ��� user �μ�Ʈ�۾�(SimpleJdbcInsert)�� ����(execute)�ϰ� 
																// withTableName(���̺� ����)�� ������ũ����Ʈ
																// ���� �����ؼ� �����ϰ� �ǵ�������. 
				new UserBeanPropertySqlParameterSource(user)).intValue();
		user.setId(generatedId);
		return user;
	}
	
	public User update(User user) {
		int affected = jdbcTemplate.update(
				"update users set " +
				"name = :name, " +
				"username = :username, " + 
				"password = :password, " + 
				"type = :type, " + 
				"groupid = :groupid, " + 
				"created = :created, " + 
				"modified = :modified, " + 
				"logins = :logins " +
				"where id = :id",
				new UserBeanPropertySqlParameterSource(user));
		return user;
	}
	
	public void delete(int id) {
		this.jdbcTemplate.update("delete from users where id = ?", id);
	}
	
	public int deleteAll() {
		return this.jdbcTemplate.update("delete from users");
	}
	
	public User get(int id) {
		try {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?", 
				this.rowMapper, id);
		}
		catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<User> search(String name) {
		return this.jdbcTemplate.query("select * from users where name like ?", 
				this.rowMapper, "%" + name + "%");
	}
	
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id desc", 
				this.rowMapper);
	}

	public long count() {
		return this.jdbcTemplate.queryForLong("select count(0) from users");
	}
	
	private static class UserBeanPropertySqlParameterSource extends MappedBeanPropertySqlParameterSource {
		public UserBeanPropertySqlParameterSource(Object object) {
			super(object);
			map("type", "type.value");		// type    <= type.value ���� // insert into user(type, groupid) valuse(type.value, group.id)
			map("groupid", "group.id");		// groupid <= group.id   user ���̺��� groupid �� group.id �� �����Ѵ�. //  MapSqlParameterSource ����
											// ���� �� Ű���� ��ġ�ϴ� ġȯ�ڿ� ���� ���� �ڵ����� ���Եȴ�.
											// group �� ����Ʈ ������ ������� ����Ʈ �ڽ���. 
		}
	}

	public User findUser(String username) {
		try {
		return this.jdbcTemplate.queryForObject("select * from users where username = ?", 
				this.rowMapper, username);
		}
		catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
}
