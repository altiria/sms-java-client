package com.altiria.app;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.ConnectTimeoutException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.HttpEntities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.altiria.app.exception.AltiriaGwException;
import com.altiria.app.exception.ConnectionException;
import com.altiria.app.exception.GeneralAltiriaException;
import com.altiria.app.exception.JsonException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

public class AltiriaClient {

  private Gson gson;
  private String login;
  private String passwd;
  private boolean debug = false;

  // connection timeout values are defined here
  private int connectionTimeout = 3000;
  private final static int MAX_CONNECTION_TIMEOUT = 10000;
  private final static int MIN_CONNECTION_TIMEOUT = 1000;
  // response timeout values are defined here
  private int timeout = 10000;
  private final static int MAX_TIMEOUT = 30000;
  private final static int MIN_TIMEOUT = 1000;

  // API URL
  private String urlBase = "https://www.altiria.net:8443/apirest/ws";

  // Library name/version
  private String source = "lib-java-maven-1_0";

  private static final Logger log = LogManager.getLogger(AltiriaClient.class);

  /**
   * Simple constructor
   * @param login user login
   * @param password user password
   */
  public AltiriaClient(String login, String password) {
    this.login = login;
    this.passwd = password;
    gson = new GsonBuilder().create();
  }

  /**
   * This constructor includes response timeout.
   * @param login user login
   * @param password user password
   * @param timeout milliseconds for response timeout
   */
  public AltiriaClient(String login, String password, int timeout) {
    this.login = login;
    this.passwd = password;
    this.setTimeout(timeout);
    gson = new GsonBuilder().create();
  }

  /**
   * Show debugging logs.
   * @param debug if it is true, then debugging logs are shown.
   */
  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  /**
   * Set the response timeout.
   * @param connectionTimeout milliseconds for connection timeout
   */
  public void setConnectionTimeout(int connectionTimeout) {
    if (connectionTimeout > MAX_CONNECTION_TIMEOUT)
      this.connectionTimeout = 3000;
    else if (connectionTimeout < MIN_CONNECTION_TIMEOUT)
      this.connectionTimeout = 3000;
    else
      this.connectionTimeout = connectionTimeout;
  }

  /**
   * Set the response timeout.
   * @param timeout milliseconds for response timeout
   */
  public void setTimeout(int timeout) {
    if (timeout > MAX_TIMEOUT)
      this.timeout = 10000;
    else if (timeout < MIN_TIMEOUT)
      this.timeout = 10000;
    else
      this.timeout = timeout;
  }

  /**
   * Send a SMS.
   * @param destination destination phone number
   * @param message SMS message
   */
  public String sendSms(String destination, String message) throws Exception {
    AltiriaModelTextMessage textMessage = new AltiriaModelTextMessage(destination, message);
    return sendSms(textMessage);
  }

  /**
   * Send a SMS.
   * @param destination destination phone number
   * @param message SMS message
   * @param senderId sender
   */
  public String sendSms(String destination, String message, String senderId) throws Exception {
    AltiriaModelTextMessage textMessage = new AltiriaModelTextMessage(destination, message, senderId);
    return sendSms(textMessage);
  }

  /**
   * Send a SMS.
   * @param textMessage this object contains the SMS data. See AltiriaModelTextMessage class.
   * @return Json response.
   * @throws Exception
   */
  public String sendSms(AltiriaModelTextMessage textMessage) throws Exception {
    if (debug)
      log.debug("Altiria-sendSms CMD: " + textMessage.toString());

    String jsonResponse = null;
    try {
      JsonObject jsonObject = new JsonObject();
      JsonObject credentialsJsonObject = new JsonObject();
      JsonObject messageJsonObject = new JsonObject();
      JsonArray destinationsJsonArray = null;

      if (textMessage.getDestination() == null || textMessage.getDestination().trim().isEmpty()) {
        if (this.debug)
          log.error("ERROR: The destination parameter is mandatory");
        throw new AltiriaGwException("INVALID_DESTINATION", "015");
      } else {
        destinationsJsonArray = new JsonArray();
        destinationsJsonArray.add(new JsonPrimitive(new String(textMessage.getDestination())));
      }

      if (textMessage.getMessage() == null || textMessage.getMessage().trim().isEmpty()) {
        if (this.debug)
          log.error("ERROR: The message parameter is mandatory");
        throw new AltiriaGwException("EMPTY_MESSAGE", "017");
      } else
        messageJsonObject.addProperty("msg", textMessage.getMessage());

      if (textMessage.getSenderId() != null && !textMessage.getSenderId().trim().isEmpty())
        messageJsonObject.addProperty("senderId", textMessage.getSenderId());

      if (textMessage.isAck()) {
        messageJsonObject.addProperty("ack", true);
        if (textMessage.getIdAck() != null && !textMessage.getIdAck().trim().isEmpty())
          messageJsonObject.addProperty("idAck", textMessage.getIdAck());
      }

      if (textMessage.isConcat())
        messageJsonObject.addProperty("concat", true);

      if (textMessage.isCertDelivery())
        messageJsonObject.addProperty("certDelivery", true);

      if (textMessage.getEncoding() != null && textMessage.getEncoding().equals("unicode"))
        messageJsonObject.addProperty("encoding", "unicode");

      credentialsJsonObject.addProperty("login", this.login);
      credentialsJsonObject.addProperty("passwd", this.passwd);

      jsonObject.add("credentials", credentialsJsonObject);
      jsonObject.add("destination", destinationsJsonArray);
      jsonObject.add("message", messageJsonObject);
      jsonObject.addProperty("source", source);

      RequestConfig config = RequestConfig.custom()
        .setConnectTimeout(this.connectionTimeout, TimeUnit.MILLISECONDS)
        .setResponseTimeout(this.timeout, TimeUnit.MILLISECONDS)
        .build();

      HttpClientBuilder builder = HttpClientBuilder.create();
      builder.setDefaultRequestConfig(config);
      CloseableHttpClient httpClient = builder.build();

      HttpPost request = new HttpPost(this.urlBase + "/sendSms");
      final HttpEntity[] entity = {
        HttpEntities.create(
          jsonObject.toString(),
          ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
      };
      request.setEntity(entity[0]);

      CloseableHttpResponse httpResponse = null;
      try {
        httpResponse = httpClient.execute(request);
        jsonResponse = EntityUtils.toString(httpResponse.getEntity());

        if (debug) {
          log.debug("HTTP status:" + httpResponse.getCode());
          log.debug("HTTP body:" + jsonResponse);
        }

        LinkedTreeMap < String, Object > mapBody = gson.fromJson(jsonResponse, new TypeToken < LinkedTreeMap < String, Object >> () {}.getType());

        if (httpResponse.getCode() != 200) {
          if (debug)
            log.error("ERROR: Invalid request: " + jsonResponse);
          String errorMsg = (String) mapBody.get("error");
          throw new JsonException(errorMsg);
        } else {
          String status = (String) mapBody.get("status");
          if (!status.equals("000")) {
            String errorMsg = getStatus(status);
            if (debug)
              log.error("ERROR: Invalid parameter. Error message: " + errorMsg + ", Status: " + status);
            throw new AltiriaGwException(errorMsg, status);
          }
        }
      } catch (ConnectTimeoutException cte) {
        if (debug)
          log.error("ERROR: Connection timeout");
        throw new ConnectionException("CONNECTION_TIMEOUT");
      } catch (SocketTimeoutException ste) {
        if (debug)
          log.error("ERROR: Response timeout");
        throw new ConnectionException("RESPONSE_TIMEOUT");
      } finally {
        try {
          if (httpResponse != null)
            httpResponse.close();
          if (httpClient != null)
            httpClient.close();
        } catch (IOException ioe) {
          log.error("ERROR closing resources");
        }
      }
    } catch (GeneralAltiriaException e) {
      throw e;
    } catch (Exception e) {
      if (this.debug)
        log.error("ERROR: Unexpected error: " + e.getMessage());
      throw new AltiriaGwException("GENERAL_ERROR", "001");
    }
    return jsonResponse;
  }

  /**
   * Get the user credit.
   * @return credit
   * @throws Exception
   */
  public String getCredit() throws Exception {
    if (debug)
      log.debug("Altiria-getCredit CMD");

    String credit = null;
    try {
      JsonObject jsonObject = new JsonObject();

      JsonObject credentialsJsonObject = new JsonObject();
      credentialsJsonObject.addProperty("login", this.login);
      credentialsJsonObject.addProperty("passwd", this.passwd);

      jsonObject.add("credentials", credentialsJsonObject);
      jsonObject.addProperty("source", source);

      RequestConfig config = RequestConfig.custom()
        .setConnectTimeout(this.connectionTimeout, TimeUnit.MILLISECONDS)
        .setResponseTimeout(this.timeout, TimeUnit.MILLISECONDS)
        .build();

      HttpClientBuilder builder = HttpClientBuilder.create();
      builder.setDefaultRequestConfig(config);
      CloseableHttpClient httpClient = builder.build();

      HttpPost request = new HttpPost(this.urlBase + "/getCredit");
      final HttpEntity[] entity = {
        HttpEntities.create(
          jsonObject.toString(),
          ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
      };
      request.setEntity(entity[0]);

      CloseableHttpResponse httpResponse = null;
      try {
        httpResponse = httpClient.execute(request);
        String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
        if (debug) {
          log.debug("HTTP status:" + httpResponse.getCode());
          log.debug("HTTP body:" + jsonResponse);
        }
        LinkedTreeMap < String, Object > mapBody = gson.fromJson(jsonResponse, new TypeToken < LinkedTreeMap < String, Object >> () {}.getType());
        if (httpResponse.getCode() != 200) {
          if (debug)
            log.error("ERROR: Invalid request: " + jsonResponse);
          String errorMsg = (String) mapBody.get("error");
          throw new JsonException(errorMsg);
        } else {
          String status = (String) mapBody.get("status");
          if (!status.equals("000")) {
            String errorMsg = getStatus(status);
            if (debug)
              log.error("ERROR: Invalid parameter. Error message: " + errorMsg + ", Status: " + status);
            throw new AltiriaGwException(errorMsg, status);
          } else
            credit = (String) mapBody.get("credit");
        }
      } catch (ConnectTimeoutException cte) {
        if (debug)
          log.error("ERROR: Connection timeout");
        throw new ConnectionException("CONNECTION_TIMEOUT");
      } catch (SocketTimeoutException ste) {
        if (debug)
          log.error("ERROR: Response timeout");
        throw new ConnectionException("RESPONSE_TIMEOUT");
      } finally {
        try {
          if (httpResponse != null)
            httpResponse.close();
          if (httpClient != null)
            httpClient.close();
        } catch (IOException ioe) {
          log.error("ERROR closing resources");
        }
      }
    } catch (GeneralAltiriaException e) {
      throw e;
    } catch (Exception e) {
      if (this.debug)
        log.error("ERROR: Unexpected error: " + e.getMessage());
      throw new AltiriaGwException("GENERAL_ERROR", "001");
    }
    return credit;
  }

  /**
   * Provide the status message through the status code.
   * @param status status code
   * @return status message
   */
  public String getStatus(String status) {
    String errorMessage = "GENERAL_ERROR";
    if (status.equals("001")) {
      errorMessage = "INTERNAL_SERVER_ERROR";
    } else if (status.equals("002")) {
      errorMessage = "SSL_PORT_ERROR";
    } else if (status.equals("010")) {
      errorMessage = "DESTINATION_FORMAT_ERROR";
    } else if (status.equals("013")) {
      errorMessage = "MESSAGE_IS_TOO_LONG";
    } else if (status.equals("014")) {
      errorMessage = "INVALID_HTTP_REQUEST_ENCODING";
    } else if (status.equals("015")) {
      errorMessage = "INVALID_DESTINATION";
    } else if (status.equals("016")) {
      errorMessage = "DUPLICATED_DESTINATION";
    } else if (status.equals("017")) {
      errorMessage = "EMPTY_MESSAGE";
    } else if (status.equals("018")) {
      errorMessage = "TOO_MANY_DESTINATIONS";
    } else if (status.equals("019")) {
      errorMessage = "TOO_MANY_MESSAGES";
    } else if (status.equals("020")) {
      errorMessage = "AUTHENTICATION_ERROR";
    } else if (status.equals("022")) {
      errorMessage = "INVALID_SENDER";
    } else if (status.equals("030")) {
      errorMessage = "URL_AND_MESSAGE_ARE_TOO_LONG";
    } else if (status.equals("031")) {
      errorMessage = "INVALID_URL_LENGTH";
    } else if (status.equals("032")) {
      errorMessage = "INVALID_URL_CHARS";
    } else if (status.equals("033")) {
      errorMessage = "INVALID_DESTINATION_SMS_PORT";
    } else if (status.equals("034")) {
      errorMessage = "INVALID_ORIGIN_SMS_PORT";
    } else if (status.equals("035")) {
      errorMessage = "INVALID_LANDING";
    } else if (status.equals("036")) {
      errorMessage = "LANDING_NOT_EXISTS";
    } else if (status.equals("037")) {
      errorMessage = "TOO_MANY_LANDINGS";
    } else if (status.equals("038")) {
      errorMessage = "SYNTAX_LANDING_ERROR";
    } else if (status.equals("039")) {
      errorMessage = "SYNTAX_WEB_PARAMS_ERROR";
    }
    return errorMessage;
  }

}
