package com.altiria.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.altiria.app.exception.JsonException;

import java.net.ConnectException;
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
public class TestAltiriaSmsJavaClientSendSmsHttp2 {

    //configurable parameters
    public String login = "user@altiria.com";
    public String password = "mypassword";
    //set to null if there is no sender
    public String sender = "mySender";
    public boolean debug = false;
    public String destination = "346XXXXXXXX";
    
    private static Gson gson = null;
    private static final Logger log = LogManager.getLogger(TestAltiriaSmsJavaClientSendSmsHttp2.class);
    
    @BeforeClass
    public static void init(){
    	gson = new GsonBuilder().create();
    }
    
    /**
     * Only mandatory parameters are sent.
     */
    @SuppressWarnings("all")
    @Test
    public void test() {
    	if (debug)
    		log.debug("Enter testOkMandatoryParams");
    
    	
    	
    	
    	
try {
    AltiriaClient client = new AltiriaClient("miusuario@email.com", "contraseña", 5000);
    client.setDebug(true);
    client.setConnectionTimeout(1000);
    //client.setTimeout(5000); Se puede asignar aquí o en el constructor
    String credit = client.getCredit();
    System.out.println("Crédito disponible: "+credit);
} catch (AltiriaGwException ae) {
	System.out.println("Solicitud no aceptada: "+ae.getMessage());
	System.out.println("Código de error: "+ae.getStatus());
} catch (JsonException je) {
	System.out.println("Error en la petición: "+je.getMessage());
} catch (ConnectException ce) {
	if(ce.getMessage().contains("RESPONSE_TIMEOUT"))
		System.out.println("Tiempo de respuesta agotado: "+ce.getMessage());
	else
		System.out.println("Tiempo de conexión agotado: "+ce.getMessage());
} catch (Exception e) {
	System.out.println("Error inesperado: "+e.getMessage());
}




try {
    AltiriaClient client = new AltiriaClient(login, password);
    String credit = client.getCredit();
    System.out.println("Crédito disponible: "+credit);
} catch (AltiriaGwException ae) {
	System.out.println("Solicitud no aceptada: "+ae.getMessage());
	System.out.println("Código de error: "+ae.getStatus());
} catch (JsonException je) {
	System.out.println("Error en la petición: "+je.getMessage());
} catch (ConnectException ce) {
	if(ce.getMessage().contains("RESPONSE_TIMEOUT"))
		System.out.println("Tiempo de respuesta agotado: "+ce.getMessage());
	else
		System.out.println("Tiempo de conexión agotado: "+ce.getMessage());
} catch (Exception e) {
	System.out.println("Error inesperado: "+e.getMessage());
}



    	

    	
    	
    	
    	
    }
    
   
}
