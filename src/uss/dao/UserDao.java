package uss.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import uss.model.User;

public class UserDao extends JdbcDaoSupport implements Dao<User> {

	@Override
	public void insert(User obj) {
		String sql = "INSERT INTO User Values(null, ?, ?, ? ,?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql, obj.getInsertParameters());
	}

	@Override
	public void update(User obj) {
		String sql = "UPDATE User SET User_name = ?, User_email = ?, User_password = ?, User_company = ?, User_phoneNumber = ?, User_profile = ?, User_cover = ? WHERE stringId = ?";
		getJdbcTemplate().update(sql, obj.getUpdateParameters());

	}

	@Override
	public User find(Object... stringId) {
		String sql = "SELECT * FROM User WHERE stringId = ?";
		RowMapper<User> rowMapper = new RowMapper<User>() {
			
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new User(
						rs.getInt("id"),
						rs.getString("stringId"),
						rs.getString("name"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("company"),
						rs.getString("phoneNumber"),
						rs.getString("profile"),
						rs.getString("cover"));
			}
		};
		return getJdbcTemplate().queryForObject(sql, rowMapper, stringId);
	}

	@Override
	public void delete(User obj) {
		String sql = "UPDATE User SET User_stringId = NULL, User_password = NULL WHERE stringId = ?";
		getJdbcTemplate().update(sql, obj.getStringId());
	}

}
