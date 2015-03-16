package uss.controllers.methods;

import java.io.UnsupportedEncodingException;

import lib.mapping.annotation.HttpMethod;
import lib.mapping.dispatch.support.Http;

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
