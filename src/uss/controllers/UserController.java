package uss.controllers;

import lib.mapping.annotation.HttpMethod;
import lib.mapping.annotation.Mapping;
import lib.mapping.dispatch.support.Http;
import lib.mapping.view.Json;
import uss.database.objects.User;
import uss.exception.LoggonNeeded;

public class UserController {

	@Mapping(value = "/api/user", method = "GET", before = "loginCheck")
	public void user(Http http) {
		http.setView(new Json("user"));
	}

	@HttpMethod
	public void loginCheck(Http http) throws LoggonNeeded {
		User user = http.getSessionAttribute(User.class, "user");
		if (user == null)
			throw new LoggonNeeded();
	}
}
