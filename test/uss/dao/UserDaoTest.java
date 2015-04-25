package uss.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uss.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserDaoTest {

	@Autowired
	UserDao dao;

	@Test
	public void test() {
		System.out.println(dao);
		System.out.println(dao.find("uss"));
		
		User user = dao.find("uss");
		
		System.out.println(user);
		assertEquals(dao.find("uss").getPassword(), "password");
	}

}
