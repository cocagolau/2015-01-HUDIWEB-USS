package uss.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import uss.model.User;

public class UserDao extends JdbcDaoSupport implements Dao<User> {

	@Override
	public void insert(User obj) {
		String sql = "INSERT INTO User Values(null, ?, ?, ? ,?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql, obj.getParameters());
	}

	@Override
	public void update(User Object) {
		
	}

	@Override
	public User find(Object... stringId) {
		return null;
	}

	@Override
	public void delete(Object... keys) {
		// TODO Auto-generated method stub

	}

}
