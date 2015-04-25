package uss.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.jdbc.core.support.JdbcDaoSupport;

import uss.model.Relation;
import uss.model.User;

public class RelationDao extends JdbcDaoSupport implements Dao<Relation> {

	public List<User> getFriends(Integer userId) {
		String sql = "SELECT * FROM Relation inner join User on Relation.Relation_friendId = User.User_id WHERE Relation.Relation_id = ?";
		List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql, userId);
		List<User> users = new ArrayList<User>();
		list.forEach(e -> {
			User user = new User((Integer) e.get("User_id"), e.get("User_stringId").toString(), e.get("User_name").toString(), e.get("User_email")
					.toString(), e.get("User_password").toString(), e.get("User_company").toString(), e.get("User_phoneNumber").toString(), e.get(
					"User_profile").toString(), e.get("User_cover").toString());
			users.add(user);
		});
		return users;
	}

	@Override
	public void insert(Relation object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Relation object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Relation object) {
		// TODO Auto-generated method stub

	}

	@Override
	public Relation find(Object... keys) {
		// TODO Auto-generated method stub
		return null;
	}

}
