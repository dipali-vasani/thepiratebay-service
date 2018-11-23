package com.pirate.util;

/**
 * The Class CustomException.
 */
public class CustomException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new custom exception.
	 *
	 * @param message
	 *            the message
	 */
	public CustomException(String message) {
		super(message);
	}
}
