package uss.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class RelationDaoTest {

	@Autowired
	private RelationDao rdao;

	@Test
	public void test() {
		System.out.println(rdao.getFriends(1));
	}

}
