package uss.controllers;

import java.io.IOException;

import uss.mapper.annotation.Mapping;
import uss.mapper.dispatch.support.Http;

public class TestController {

	@Mapping(value = "/index", before = "kr")
	public void test(Http http) throws IOException {
		http.getResp().getWriter().write("가나다라");
	}

}
