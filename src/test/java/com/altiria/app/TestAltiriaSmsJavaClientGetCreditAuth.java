package com.altiria.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.altiria.app.exception.JsonException;

public class TestAltiriaSmsJavaClientGetCreditAuth {

  //configurable parameters
  public String login = "user@mydomain.com";
  public String password = "mypassword";

  private static final Logger log = LogManager.getLogger(TestAltiriaSmsJavaClientGetCreditAuth.class);

  /**
   * The login parameter is missed.
   */
  @Test
  public void testErrorNoLogin() {
    log.debug("Enter testErrorNoLogin");

    try {
      AltiriaClient client = new AltiriaClient(null, password, false);
      client.getCredit();
      fail("JsonException should have been thrown");
    } catch (JsonException je) {
      assertEquals("LOGIN_NOT_NULL", je.getMessage());
    } catch (Exception e) {
      fail("Error: " + e.getMessage());
    }
  }

  /**
   * The password parameter is missed.
   */
  @Test
  public void testErrorNoPassword() {
    log.debug("Enter testErrorNoPassword");

    try {
      AltiriaClient client = new AltiriaClient(login, null, false);
      client.getCredit();
      fail("JsonException should have been thrown");
    } catch (JsonException je) {
      assertEquals("PASSWORD_NOT_NULL", je.getMessage());
    } catch (Exception e) {
      fail("Error: " + e.getMessage());
    }
  }

}
