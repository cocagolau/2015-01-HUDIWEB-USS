package lib.mapping.exception;

import lib.mapping.http.Http;

public abstract class HandleException extends Exception {
	private static final long serialVersionUID = 4834668651316833922L;

	public HandleException(String string) {
		super(string);
	}

	public abstract void handle(Http http);
}
