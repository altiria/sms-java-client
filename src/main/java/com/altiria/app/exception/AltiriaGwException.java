package com.altiria.app.exception;

public class AltiriaGwException extends GeneralAltiriaException {

  private String message;
  private String status;

  /**
   * Constructor
   * @param message error message
   * @param status status code
   * @param status
   */
  public AltiriaGwException(String message, String status) {
    super(message);
    this.message = message;
    this.status = status;
  }

  /**
   * @return the message error message
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
   * @return the status status code
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
    return "AltiriaGwException [message=" + message + ", status=" + status + "]";
  }
}
