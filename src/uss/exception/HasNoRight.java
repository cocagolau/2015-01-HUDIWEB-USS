package uss.exception;

import lib.mapping.dispatch.support.Http;
import lib.mapping.exception.HandleException;
import lib.mapping.view.Json;

public class HasNoRight extends HandleException {

	private static final long serialVersionUID = -8132512868280285543L;

	@Override
	public void handle(Http http) {
		http.setView(new Json("right need"));
	}

}
