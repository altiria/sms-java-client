package com.altiria.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.altiria.app.exception.ConnectionException;


public class TestAltiriaSmsJavaClientGetCreditConnection {

    //configurable parameters
    public String login = "user@altiria.com";
    public String password = "mypassword";
    public boolean debug = false;
    
    private static final Logger log = LogManager.getLogger(TestAltiriaSmsJavaClientGetCreditConnection.class);
    
    /**
     * Checks the connection timeout parameter.
     */
    @Test
    public void testErrorConnectionTimeout() {
    	if (debug)
    		log.debug("Enter testErrorConnectionTimeout");
    	
    	try {
            AltiriaClient client = new AltiriaClient(login, password);
            client.setDebug(debug);
            client.setConnectionTimeout(1);
            client.getCredit();
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
            AltiriaClient client = new AltiriaClient(login, password, 5000);
            client.setDebug(debug);
            client.getCredit();
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
            AltiriaClient client = new AltiriaClient(login, password);
            client.setDebug(debug);
            client.setTimeout(5000);
            client.getCredit();
            fail("ConnectionException should have been thrown");
            
		} catch (ConnectionException ce) {
			assertEquals("RESPONSE_TIMEOUT", ce.getMessage());
		} catch (Exception e) {
			fail("Error: "+e.getMessage());
		}
    }
    
}
