package lib.database;

import static org.junit.Assert.assertFalse;

import java.util.Date;

import lib.mapping.exception.RegexNotMatches;

import org.junit.Test;

import uss.model.database.Matching;
import uss.model.database.User;

public class DAOTest {
	DAO dao = new DAO();

	@Test
	public void insertTest() throws RegexNotMatches {
		User user = new User();
		user.setGender(0);
		user.setStringId("zerohouse");
		dao.insert(user);
	}

	@Test
	public void updateTest() throws RegexNotMatches {
		Matching matching = new Matching();
		matching.setMale(1);
		matching.setFemale(2);
		matching.setDate(new Date());
		dao.update(matching);
		
		
		User user = new User();
		user.setGender(0);
		user.setStringId("as");
		user.setNickName("asdf");
		assertFalse(dao.update(user));
	}
	
	@Test
	public void fillTest() throws RegexNotMatches {
		User user = new User();
		user.setStringId("as");
		dao.fill(user);
		System.out.println(user);
	}
	
	
	@Test
	public void selectTest() throws RegexNotMatches {
		User user = new User();
		user.setGender(0);
		user.setId(1);
		user.setNickName("abc");
		System.out.println(dao.getRecordByClass(User.class, 3));
	}

	@Test
	public void selectListTest() {
		System.out.println(dao.getRecordsByClass(User.class));
	}

}
