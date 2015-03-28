package uss.exception;

import lib.mapping.exception.HandleException;
import lib.mapping.http.Http;
import lib.mapping.view.Json;

public class JsonAlert extends HandleException {

	private static final long serialVersionUID = -8132512868280285543L;
	
	private String errorMessage;

	public JsonAlert(String string) {
		errorMessage = string;
	}

	public void handle(Http http) {
		http.setView(new Json(new ErrorObj(errorMessage)));
	}
	
	private class ErrorObj {
		@SuppressWarnings("unused")
		private boolean error;
		@SuppressWarnings("unused")
		private String errorMessage;
		private ErrorObj(String errorMessage){
			error = true;
			this.errorMessage = errorMessage;
		}
	}

}
