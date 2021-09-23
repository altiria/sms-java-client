package com.altiria.app;

public class AltiriaModelTextMessage {
	
    private String destination;
    private String message;
    private String senderId;
    private boolean ack = false;
    private String idAck;
    private boolean concat = false;
    private boolean certDelivery = false;
    private String encoding;
    
    /**
     * Simple constructor.
     * @param destination phone destination number
     * @param message SMS text
     */
    public AltiriaModelTextMessage(String destination, String message) {
    	this.destination = destination;
    	this.message = message;
    }

    /**
     * This constructor includes the sender id parameter.
     * @param destination destination phone destination number
     * @param message SMS text
     * @param senderId sender id
     */
    public AltiriaModelTextMessage(String destination, String message, String senderId) {
    	this.destination = destination;
    	this.message = message;
    	this.senderId = senderId;
    }

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
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
	 * @return the senderId
	 */
	public String getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return the ack
	 */
	public boolean isAck() {
		return ack;
	}

	/**
	 * @param ack the ack to set
	 */
	public void setAck(boolean ack) {
		this.ack = ack;
	}

	/**
	 * @return the idAck
	 */
	public String getIdAck() {
		return idAck;
	}

	/**
	 * @param idAck the idAck to set
	 */
	public void setIdAck(String idAck) {
		this.idAck = idAck;
	}

	/**
	 * @return the concat
	 */
	public boolean isConcat() {
		return concat;
	}

	/**
	 * @param concat the concat to set
	 */
	public void setConcat(boolean concat) {
		this.concat = concat;
	}

	/**
	 * @return the certDelivery
	 */
	public boolean isCertDelivery() {
		return certDelivery;
	}

	/**
	 * @param certDelivery the certDelivery to set
	 */
	public void setCertDelivery(boolean certDelivery) {
		this.certDelivery = certDelivery;
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AltiriaModelTextMessage [destination=" + destination + ", message=" + message + ", senderId=" + senderId
				+ ", ack=" + ack + ", idAck=" + idAck + ", concat=" + concat + ", certDelivery=" + certDelivery
				+ ", encoding=" + encoding + "]";
	}
}
