package com.example.poll.exception;

public class PollException extends Exception {

	private static final long serialVersionUID = 1L;

	public PollException() {
		super();
	}

	public PollException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PollException(String message, Throwable cause) {
		super(message, cause);
	}

	public PollException(String message) {
		super(message);
	}

	public PollException(Throwable cause) {
		super(cause);
	}

}
