package com.altiria.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.altiria.app.exception.AltiriaGwException;
import com.altiria.app.exception.JsonException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class TestAltiriaSmsJavaClientSendSmsAuth {

  //configurable parameters
  public String login = "user@mydomain.com";
  public String password = "mypassword";
  //set to null if there is no sender
  public String sender = "mySender";
  public String destination = "346XXXXXXXX";

  private static final Logger log = LogManager.getLogger(TestAltiriaSmsJavaClientSendSmsAuth.class);

  /**
   * The login parameter is missed.
   */
  @Test
  public void testErrorNoLogin() {
    log.debug("Enter testErrorNoLogin");

    try {
      String message = "Lorem Ipsum is simply dummy text";

      AltiriaClient client = new AltiriaClient(null, password);
      client.sendSms(destination, message);
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
      String message = "Lorem Ipsum is simply dummy text";

      AltiriaClient client = new AltiriaClient(login, null);
      client.sendSms(destination, message);
      fail("JsonException should have been thrown");

    } catch (JsonException je) {
      assertEquals("PASSWORD_NOT_NULL", je.getMessage());
    } catch (Exception e) {
      fail("Error: " + e.getMessage());
    }
  }

  /**
   * The destination parameter is missed.
   */
  @Test
  public void testErrorNoDestination() {
    log.debug("Enter testErrorNoDestination");

    try {
      String message = "Lorem Ipsum is simply dummy text";

      AltiriaClient client = new AltiriaClient(login, null);
      client.sendSms(null, message);
      fail("AltiriaGwException should have been thrown");

    } catch (AltiriaGwException ae) {
      assertEquals("INVALID_DESTINATION", ae.getMessage());
      assertEquals("015", ae.getStatus());
    } catch (Exception e) {
      fail("Error: " + e.getMessage());
    }
  }

  /**
   * The message parameter is missed.
   */
  @Test
  public void testErrorNoMessage() {
    log.debug("Enter testErrorNoMessage");

    try {
      AltiriaClient client = new AltiriaClient(login, null);
      client.sendSms(destination, null);
      fail("AltiriaGwException should have been thrown");

    } catch (AltiriaGwException ae) {
      assertEquals("EMPTY_MESSAGE", ae.getMessage());
      assertEquals("017", ae.getStatus());
    } catch (Exception e) {
      fail("Error: " + e.getMessage());
    }
  }

}
