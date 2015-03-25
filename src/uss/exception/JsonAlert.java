package uss.exception;

import lib.mapping.dispatch.support.Http;
import lib.mapping.exception.HandleException;
import lib.mapping.view.Json;

public class JsonAlert extends HandleException {

	private static final long serialVersionUID = -8132512868280285543L;
	
	 private String errorMessage;

	public JsonAlert(String string) {
		errorMessage = string;
	}

	@Override
	public void handle(Http http) {
		http.setView(new Json(errorMessage));
	}

}
