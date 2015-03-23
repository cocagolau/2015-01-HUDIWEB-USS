package uss.controllers;

import lib.mapping.annotation.Mapping;
import lib.mapping.dispatch.support.Http;
import lib.mapping.view.Json;

public class UserController {

	@Mapping(value="/api/user", method="get")
	public void user(Http http){
		http.setView(new Json("user"));
	}
}
