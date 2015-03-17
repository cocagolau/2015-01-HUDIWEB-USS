package uss.controllers;

import lib.database.DAO;
import lib.mapping.annotation.Mapping;
import lib.mapping.dispatch.support.Http;
import lib.mapping.view.Json;

public class TestController {

	@Mapping(value = "/index", before = "kr")
	public void test(Http http, DAO dao) {
		http.write("가나다라");
		http.setView(new Json());
	}
	
	@Mapping(value = "/index2", before = "kr")
	public void test(Http http) {
		http.write("가나다라");
		http.setView(new Json());
	}
	
	@Mapping(value = "/index3", before = "kr")
	public void test() {
	}


}
