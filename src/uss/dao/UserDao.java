package uss.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import uss.model.User;

public class UserDao extends JdbcDaoSupport implements Dao<User> {

	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("uss.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
	}

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
		String sql = "SELECT * FROM User WHERE User_stringId = ?";
		RowMapper<User> rowMapper = new RowMapper<User>() {

			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new User(rs.getInt("User_id"), rs.getString("User_stringId"), rs.getString("User_name"), rs.getString("User_email"),
						rs.getString("User_password"), rs.getString("User_company"), rs.getString("User_phoneNumber"), rs.getString("User_profile"),
						rs.getString("User_cover"));
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
