package com.example.poll.exception;

public class VoteException extends Exception {

	private static final long serialVersionUID = 1L;

	public VoteException() {
		super();
	}

	public VoteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public VoteException(String message, Throwable cause) {
		super(message, cause);
	}

	public VoteException(String message) {
		super(message);
	}

	public VoteException(Throwable cause) {
		super(cause);
	}

}
