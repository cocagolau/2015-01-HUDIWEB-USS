package uss.controllers.methods;

import java.io.UnsupportedEncodingException;

import uss.mapper.annotation.HttpMethod;
import uss.mapper.dispatch.support.Http;

public class Methods {

	@HttpMethod("kr")
	public void encording(Http http){
		try {
			http.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
