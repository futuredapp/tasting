package com.thefuntasty.tasting;

public class TastingException extends RuntimeException {
	public TastingException(String message) {
		super(message);
	}

	public TastingException(String message, Throwable cause) {
		super(message, cause);
	}

	public TastingException(Throwable cause) {
		super(cause);
	}
}
