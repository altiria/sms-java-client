package com.altiria.app.exception;

public class GeneralAltiriaException extends Exception {

	private String message;
	private String status;
	
	/**
	 * Basic constructor.
	 * @param message error message
	 */
	public GeneralAltiriaException(String message) {
		super(message);
		this.message = message;
	}
	
	/**
	 * Full constructor.
	 * @param message error message
	 * @param status status code
	 */
	public GeneralAltiriaException(String message, String status) {
		super(message);
		this.message = message;
		this.status = status;
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

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GeneralAltiriaException [message=" + message + ", status=" + status + "]";
	}
}
