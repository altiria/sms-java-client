package com.altiria.app.exception;

public class ConnectionException extends GeneralAltiriaException {

	private String message;

	/**
	 * Constructor.
	 * @param message error message
	 */
	public ConnectionException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConnectionException [message=" + message + "]";
	}
}
