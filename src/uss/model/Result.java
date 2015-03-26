package uss.model;

public class Result {

	private boolean success;
	private String message;

	public Result(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	@Override
	public String toString() {
		return "Result [success=" + success + ", message=" + message + "]";
	}

}
