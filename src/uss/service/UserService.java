package uss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uss.dao.UserDao;
import uss.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public void insert(User user) {
		userDao.insert(user);
	}

}
