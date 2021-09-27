package com.altiria.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.altiria.app.exception.AltiriaGwException;

public class TestAltiriaSmsJavaClientGetCreditHttp {

  //configurable parameters
  public String login = "user@mydomain.com";
  public String password = "mypassword";

  private static final Logger log = LogManager.getLogger(TestAltiriaSmsJavaClientGetCreditHttp.class);

  /**
   * Basic case.
   */
  @Test
  public void testOk() {
    log.debug("Enter testOk");

    try {
      AltiriaClient client = new AltiriaClient(login, password);
      String credit = client.getCredit();

      //Check your credit here        
      //assertEquals("100.00", credit);

    } catch (Exception e) {
      fail("Error: " + e.getMessage());
    }
  }

  /**
   * Invalid credentials.
   */
  @Test
  public void testErrorInvalidCredentials() {
    log.debug("Enter testErrorInvalidCredentials");

    try {
      AltiriaClient client = new AltiriaClient("unknown", password);
      client.getCredit();
      fail("AltiriaGwException should have been thrown");

    } catch (AltiriaGwException ae) {
      assertEquals("AUTHENTICATION_ERROR", ae.getMessage());
      assertEquals("020", ae.getStatus());
    } catch (Exception e) {
      fail("Error: " + e.getMessage());
    }
  }

}
