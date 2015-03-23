package lib.mapping.exception;

import lib.mapping.dispatch.support.Http;

public abstract class HandleException extends Exception {
	private static final long serialVersionUID = 4834668651316833922L;

	public abstract void handle(Http http);
}
