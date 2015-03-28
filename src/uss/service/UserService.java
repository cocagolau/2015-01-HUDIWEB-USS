package uss.service;

import uss.exception.JsonAlert;
import uss.model.database.User;

public class UserService {
	public static void login(User userFromDB, User loggedUser) throws JsonAlert {
		if (userFromDB == null)
			throw new JsonAlert("없는 아이디입니다.");
		if (!userFromDB.getPassword().equals(loggedUser.getPassword()))
			throw new JsonAlert("패스워드가 틀렸습니다.");
	}
}
