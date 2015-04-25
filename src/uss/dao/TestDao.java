package uss.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import uss.model.TestResult;

public class TestDao extends JdbcDaoSupport implements Dao<TestResult>{
	
	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("uss.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
	}

	
	@Override
	public void insert(TestResult testResult) {
		String sql = "INSERT INTO TestResult Values(?, ?)";
		getJdbcTemplate().update(sql, testResult.getStringId(), testResult.getResult());
	}
	
	@Override
	public TestResult find(Object... stringId) {
		String sql = "SELECT * FROM TestResult WHERE TestResult_stringId = ?";
		RowMapper<TestResult> rowMapper = new RowMapper<TestResult>() {
			
			public TestResult mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new TestResult(
						rs.getString("TestResult_stringId"),
						rs.getString("TestResult_result"));
			}
			
		};
		
		return getJdbcTemplate().queryForObject(sql, rowMapper, stringId);
	}
	
	@Override
	public void update(TestResult testResult) {
		String sql = "UPDATE TestResult SET TestResult_result = ? WHERE TestResult_stringId = ?";
		getJdbcTemplate().update(sql, testResult.getStringId(), testResult.getResult());
	}


	@Override
	public void delete(Object... keys) {
		// TODO Auto-generated method stub
		
	}


}
