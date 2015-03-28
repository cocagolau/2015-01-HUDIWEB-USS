package uss.database.objects;

import lib.database.DAO;
import lib.mapping.exception.RegexNotMatches;

import org.junit.Test;

import uss.model.database.User;

public class UserTest {

	@Test
	public void insertTest() throws RegexNotMatches {
		DAO dao = new DAO();
		User user = new User();
		user.setGender(0);
		user.setStringId("abcASD@@F325d");
		dao.insert(user);
	}
	
	@Test
	public void updateTest() throws RegexNotMatches {
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
	public void deleteTest() throws RegexNotMatches {
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

}
