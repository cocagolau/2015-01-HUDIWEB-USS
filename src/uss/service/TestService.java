package uss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uss.dao.TestDao;
import uss.model.TestResult;

@Service
public class TestService {
	
	@Autowired
	private TestDao testDao;

	public TestResult find(String stringId) {
		
		return testDao.find(stringId);
	}

}
