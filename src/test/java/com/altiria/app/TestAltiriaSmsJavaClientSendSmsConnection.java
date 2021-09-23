package com.altiria.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.altiria.app.exception.ConnectionException;


public class TestAltiriaSmsJavaClientSendSmsConnection {

    //configurable parameters
    public String login = "user@altiria.com";
    public String password = "mypassword";
    public boolean debug = false;
    public String destination = "346XXXXXXXX";
    
    private static final Logger log = LogManager.getLogger(TestAltiriaSmsJavaClientSendSmsConnection.class);
    
    /**
     * Checks the connection timeout parameter.
     */
    @Test
    public void testErrorConnectionTimeout() {
    	if (debug)
    		log.debug("Enter testErrorConnectionTimeout");
    	
    	try {
    		String message = "Lorem Ipsum is simply dummy text";
            
            AltiriaClient client = new AltiriaClient(login, password);
            client.setDebug(debug);
            client.setConnectionTimeout(1);
            client.sendSms(destination, message);
            fail("ConnectionException should have been thrown");
            
		} catch (ConnectionException ce) {
			assertEquals("CONNECTION_TIMEOUT", ce.getMessage());
		} catch (Exception e) {
			fail("Error: "+e.getMessage());
		}
    }
    
    /**
     * Checks the timeout parameter. It is mandatory to add a sleep in the server.
     */
    @Test
    public void testErrorTimeoutConstructor() {
    	if (debug)
    		log.debug("Enter testErrorTimeoutConstructor");
    
    	try {
    		String message = "Lorem Ipsum is simply dummy text";
            
            AltiriaClient client = new AltiriaClient(login, password, 5000);
            client.setDebug(debug);
            client.sendSms(destination, message);
            fail("ConnectionException should have been thrown");
            
		} catch (ConnectionException ce) {
			assertEquals("RESPONSE_TIMEOUT", ce.getMessage());
		} catch (Exception e) {
			fail("Error: "+e.getMessage());
		}
    }
    
    /**
     * Checks the timeout parameter. It is mandatory to add a sleep in the server.
     */
    @Test
    public void testErrorTimeoutSetter() {
    	if (debug)
    		log.debug("Enter testErrorTimeoutSetter");
    
    	try {
    		String message = "Lorem Ipsum is simply dummy text";
            
            AltiriaClient client = new AltiriaClient(login, password);
            client.setDebug(debug);
            client.setTimeout(5000);
            client.sendSms(destination, message);
            fail("ConnectionException should have been thrown");
            
		} catch (ConnectionException ce) {
			assertEquals("RESPONSE_TIMEOUT", ce.getMessage());
		} catch (Exception e) {
			fail("Error: "+e.getMessage());
		}
    }
    
}
