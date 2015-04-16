package uss.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uss.dao.TestDao;
import uss.model.TestResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class TestDaoTest {
	
	private static final Logger logger = LoggerFactory.getLogger(TestDaoTest.class);

	@Autowired
	private TestDao testDao;
	
	@Test
	public void find() {
		TestResult testResult = testDao.find("uss");
		logger.debug("testResult : {}", testResult.toString());
	}

}
