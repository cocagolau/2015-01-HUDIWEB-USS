package uss.database.objects;

import lib.database.DAO;

import org.junit.Test;

public class UserTest {

	@Test
	public void insertTest() {
		DAO dao = new DAO();
		User user = new User();
		user.setGender(0);
		dao.insert(user);
	}
	
	@Test
	public void updateTest() {
		DAO dao = new DAO();
		User user = new User();
		user.setGender(1);
		user.setId(2);
		user.setStringId("aa");
		user.setNickName("a");
		user.setPassword("abc");
		user.setProfile("gg");
		System.out.println(user);
		dao.update(user);
	}
	
	@Test
	public void deleteTest() {
		DAO dao = new DAO();
		User user = new User();
		user.setGender(1);
		user.setId(1);
		dao.delete(user);
	}
	
	@Test
	public void selectTeast() {
		DAO dao = new DAO();
		User user = dao.getRecord(User.class, "SELECT * FROM User WHERE User_id=?", 2);
		System.out.println(user);
	}
	
	@Test
	public void PostTest() {
		User user = new User();
		user.setGender(1);
		user.setId(4);
		user.setStringId("aa");
		user.setNickName("a");
		user.setPassword("abc");
		user.setProfile("gg");
		System.out.println(user);
	}
	

}
