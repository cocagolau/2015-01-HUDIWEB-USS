package uss.controllers;

import static org.junit.Assert.*;
import lib.database.DAO;
import lib.mapping.http.HttpForTest;
import lib.mapping.view.Json;

import org.junit.After;
import org.junit.Test;

import com.google.gson.Gson;

import uss.exception.JsonAlert;
import uss.model.database.User;

public class UserControllerTest {

	UserController uc = new UserController();
	HttpForTest http = new HttpForTest();
	DAO dao = new DAO();
	Gson gson = new Gson();

	@Test
	public void registerTest() throws JsonAlert {
		User user = new User();
		user.setStringId("zerohous@e");
		user.setPassword("");
		http.setParameter("user", gson.toJson(user));
		uc.register(http, dao);
	}

	@Test
	public void idCheckTest() {
		http.setParameter("stringId", "zerohouse");
		uc.idCheck(http, dao);
		Json json = (Json) http.getView();
		assertFalse((Boolean) json.getObject());
	}

	@Test
	public void login() throws JsonAlert {
		User user = new User();
		user.setStringId("zerohouse");
		user.setPassword("");
		http.setParameter("user", gson.toJson(user));
		uc.login(http, dao);
		assertTrue(http.getSessionAttribute(User.class, "user").getStringId().equals("zerohouse"));
	}

	@After
	public void after() {
		http.render();
	}

}
