package uss.controllers;

import lib.database.DAO;
import lib.mapping.annotation.Mapping;
import lib.mapping.dispatch.support.Http;
import lib.mapping.view.Json;
import lib.mapping.view.Jsp;

public class TestController {

	@Mapping(value = "/index", before = "kr", method = "GET")
	public void test(Http http, DAO dao) {
		http.write("가나다라");
		Jsp jsp = new Jsp("index.jsp");
		jsp.put("abc", "abc");
		http.setView(jsp);
	}

	@Mapping(value = "/{}", before = "kr")
	public void test(Http http) {
		http.write("가나다라");
		http.write(http.getUriVariable(0));
		http.setView(new Json());
	}

	@Mapping(value = "/index3", before = "kr")
	public void test() {
	}

}
