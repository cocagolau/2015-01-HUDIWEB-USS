package lib.database;

import org.junit.Test;

import uss.model.database.User;

public class DAOTest {
	DAO dao = new DAO();

	@Test
	public void insertTest() {
		User user = new User();
		user.setGender(0);
		dao.insert(user);
	}

	@Test
	public void updateTest() {
		User user = new User();
		user.setGender(0);
		user.setId(1);
		user.setNickName("abc");
		dao.update(user);
	}

	@Test
	public void selectTest() {
		User user = new User();
		user.setGender(0);
		user.setId(1);
		user.setNickName("abc");
		System.out.println(dao.getRecordByClass(User.class, 1));
	}

	@Test
	public void selectListTest() {
		System.out.println(dao.getRecordsByClass(User.class));
	}

}
