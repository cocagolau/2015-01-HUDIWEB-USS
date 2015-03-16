package uss.controllers;

import java.io.IOException;

import lib.database.DAO;
import lib.mapping.annotation.Mapping;
import lib.mapping.dispatch.support.Http;

public class TestController {

	@Mapping(value = "/index", before = "kr")
	public void test(Http http, DAO db) throws IOException {
		http.getResp().getWriter().write("가나다라");
	}

}
