package com.altiria.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.altiria.app.exception.AltiriaGwException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

/**
 * Running this test suite supposes a consumption of credits (between 3 and 5 credits).
 */
public class TestAltiriaSmsJavaClientSendSmsHttp {

  //configurable parameters
  public String login = "user@mydomain.com";
  public String password = "mypassword";
  //set to null if there is no sender
  public String sender = "mySender";
  public String destination = "346XXXXXXXX";

  private static Gson gson = null;
  private static final Logger log = LogManager.getLogger(TestAltiriaSmsJavaClientSendSmsHttp.class);

  @BeforeClass
  public static void init() {
    gson = new GsonBuilder().create();
  }

  /**
   * Only mandatory parameters are sent.
   */
  @SuppressWarnings("all")
  @Test
  public void testOkMandatoryParams() {
    log.debug("Enter testOkMandatoryParams");

    try {
      String message = "Lorem Ipsum is simply dummy text";

      AltiriaClient client = new AltiriaClient(login, password);
      String json = client.sendSms(destination, message);

      LinkedTreeMap < String, Object > mapBody = gson.fromJson(json, new TypeToken < LinkedTreeMap < String, Object >> () {}.getType());
      assertEquals("000", (String) mapBody.get("status"));
      assertEquals(destination, ((List < LinkedTreeMap < String, String >> ) mapBody.get("details")).get(0).get("destination"));
      assertEquals("000", ((List < LinkedTreeMap < String, String >> ) mapBody.get("details")).get(0).get("status"));

    } catch (Exception e) {
      fail("Error: " + e.getMessage());
    }
  }

  /**
   * All params are sent.
   * Features:
   * - sender
   * - delivery confirmation with identifier
   * - concatenated
   * - set unicode encoding
   * - request delivery certificate
   */
  @SuppressWarnings("all")
  @Test
  public void testOkAllParams() {
    log.debug("Enter testOkAllParams");

    try {
      String message = "Lorem Ipsum is simply dummy text of the printing and typesetting industry \u20AC";
      String idAck = "myAlias";
      String encoding = "unicode";

      AltiriaClient client = new AltiriaClient(login, password);

      AltiriaModelTextMessage textMessage = new AltiriaModelTextMessage(destination, message, sender);

      // You can also assign the sender here
      //textMessage.setSenderId(sender);

      // Need to configure a callback URL to use it. Contact comercial@altiria.com. 
      //textMessage.setAck(true);
      //textMessage.setIdAck(idAck);

      textMessage.setConcat(true);
      textMessage.setEncoding(encoding);

      // If it is uncommented, additional credit will be consumed.
      //textMessage.setCertDelivery(true);

      String json = client.sendSms(textMessage);

      LinkedTreeMap < String, Object > mapBody = gson.fromJson(json, new TypeToken < LinkedTreeMap < String, Object >> () {}.getType());
      assertEquals("000", (String) mapBody.get("status"));

      assertEquals(destination + "(0)", ((List < LinkedTreeMap < String, String >> ) mapBody.get("details")).get(0).get("destination"));
      assertEquals("000", ((List < LinkedTreeMap < String, String >> ) mapBody.get("details")).get(0).get("status"));

      //Uncomment if idAck is used.
      //assertEquals(idAck, ((List<LinkedTreeMap<String,String>>) mapBody.get("details")).get(0).get("idAck"));

      assertEquals(destination + "(1)", ((List < LinkedTreeMap < String, String >> ) mapBody.get("details")).get(1).get("destination"));
      assertEquals("000", ((List < LinkedTreeMap < String, String >> ) mapBody.get("details")).get(1).get("status"));

      //Uncomment if idAck is used.
      //assertEquals(idAck, ((List<LinkedTreeMap<String,String>>) mapBody.get("details")).get(1).get("idAck"));

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
      String message = "Lorem Ipsum is simply dummy text";

      AltiriaClient client = new AltiriaClient("unknown", password);
      client.sendSms(destination, message);
      fail("AltiriaGwException should have been thrown");

    } catch (AltiriaGwException ae) {
      assertEquals("AUTHENTICATION_ERROR", ae.getMessage());
      assertEquals("020", ae.getStatus());
    } catch (Exception e) {
      fail("Error: " + e.getMessage());
    }
  }

  /**
   * The destination parameter is invalid.
   */
  @Test
  public void testErrorInvalidDestination() {
    log.debug("Enter testErrorInvalidDestination");

    try {
      String message = "Lorem Ipsum is simply dummy text";
      String invalidDestination = "invalid";

      AltiriaClient client = new AltiriaClient(login, password);
      client.sendSms(invalidDestination, message);
      fail("AltiriaGwException should have been thrown");

    } catch (AltiriaGwException ae) {
      assertEquals("INVALID_DESTINATION", ae.getMessage());
      assertEquals("015", ae.getStatus());
    } catch (Exception e) {
      fail("Error: " + e.getMessage());
    }
  }

  /**
   * The message parameter is empty.
   */
  @Test
  public void testErrorEmptyMessage() {
    log.debug("Enter testErrorEmptyMessage");

    try {
      String message = "";

      AltiriaClient client = new AltiriaClient(login, password);
      client.sendSms(destination, message);
      fail("AltiriaGwException should have been thrown");

    } catch (AltiriaGwException ae) {
      assertEquals("EMPTY_MESSAGE", ae.getMessage());
      assertEquals("017", ae.getStatus());
    } catch (Exception e) {
      fail("Error: " + e.getMessage());
    }
  }

}
