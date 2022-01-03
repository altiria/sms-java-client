![](http://static.altiria.com/wp-content/themes/altiria/images/logo-altiria.png)

# Altiria, cliente envío de SMS con Java

 ![](https://img.shields.io/badge/version-1.0.1-blue.svg)

Altiria SMS Java es  el cliente de envío de SMS que simplifica al máximo la integración del API SMS con Java de Altiria utilizando el gestor de dependencias **maven**. 
- **Envíos de SMS individuales** con las siguientes características:
  - sencillos
  - concatenados
  - confirmación de entrega
  - remitente personalizado
- **Consultas de crédito**

## Uso

Es necesario tener una cuenta de envío con Altiria. Si todavía no tienes una,

[Regístrate para crear una cuenta de prueba](https://www.altiria.com/free-trial/)

[Documentación de la API](https://www.altiria.com/api-envio-sms/)

## Requisitos

- Java: ^1.7
- [maven](https://maven.apache.org/)

## Instalación

La forma recomendada de instalar el cliente Altiria para Java es a través de maven.

Es necesario añadir el objeto **repositories** como hijo del elemento **project** y el objeto **dependency** como hijo del elemento **dependencies**.

```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
    <groupId>com.github.altiria</groupId>
    <artifactId>sms-java-client</artifactId>
    <version>1.0</version>
</dependency>
```

**Nota**

La codificación del proyecto por defecto es ISO-8859-1. Según el proyecto que integre esta librería puede ser necesario ajustar el archivo pom.xml.

## Ejemplos de uso

### Envío de SMS

A continuación se describen cada una de las posibilidades de uso de la librería para realizar envíos de SMS.

#### Ejemplo básico

Se trata de la opción más sencilla para realizar un envío de SMS.

```java
try {
    //Personaliza las credenciales de acceso
    AltiriaClient client = new AltiriaClient("user@mydomain.com", "mypassword");
    client.sendSms("346XXXXXXXX", "Mensaje de prueba");
    System.out.println("¡Mensaje enviado!");
} catch (AltiriaGwException ae) {
	System.out.println("Mensaje no aceptado: "+ae.getMessage());
	System.out.println("Código de error: "+ae.getStatus());
} catch (Exception e) {
	System.out.println("Error inesperado: "+e.getMessage());
}
```

#### Ejemplo básico con timeout personalizado

Permite fijar el tiempo de respuesta en milisegundos. Si se supera se lanzará una **ConnectionException**.
Por defecto el tiempo de respuesta es de 10 segundos, pero puede ser ajustado entre 1 y 30 segundos.

```java
try {
    //Personaliza las credenciales de acceso
    AltiriaClient client = new AltiriaClient("user@mydomain.com", "mypassword", 5000);
    client.sendSms("346XXXXXXXX", "Mensaje de prueba");
    System.out.println("Mensaje enviado");
} catch (AltiriaGwException ae) {
	System.out.println("Mensaje no aceptado: "+ae.getMessage());
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
```

#### Ejemplo básico con remitente

Se trata de la opción más sencilla para realizar un envío de SMS añadiendo remitente.

```java
try {
    //Personaliza las credenciales de acceso
    AltiriaClient client = new AltiriaClient("user@mydomain.com", "mypassword");
    client.sendSms("346XXXXXXXX", "Mensaje de prueba", "miRemitente");
    System.out.println("¡Mensaje enviado!");
} catch (AltiriaGwException ae) {
	System.out.println("Mensaje no aceptado: "+ae.getMessage());
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
```
#### Ejemplo con todos los parámetros

Se muestra un ejemplo utilizando todo los parámetros mediante setters.

```java
try {
    //Personaliza las credenciales de acceso
    AltiriaClient client = new AltiriaClient("user@mydomain.com", "mypassword");
    client.setConnectionTimeout(1000);
    client.setTimeout(5000);
    AltiriaModelTextMessage textMessage = new AltiriaModelTextMessage("346XXXXXXXX", "Mensaje de prueba");
	textMessage.setSenderId("miRemitente");
	textMessage.setAck(true);
	textMessage.setIdAck("idAck");
	textMessage.setConcat(true);
	textMessage.setEncoding("unicode");
	textMessage.setCertDelivery(true);
    client.sendSms(textMessage);
    System.out.println("¡Mensaje enviado!");
} catch (AltiriaGwException ae) {
	System.out.println("Mensaje no aceptado: "+ae.getMessage());
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
```
### Consulta de crédito

Ejemplos de consulta del crédito de SMS en la cuenta de Altiria.

#### Ejemplo básico

```java
try {
    //Personaliza las credenciales de acceso
    AltiriaClient client = new AltiriaClient("user@mydomain.com", "mypassword");
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

```

#### Ejemplo con timeout

```java
try {
    //Personaliza las credenciales de acceso
    AltiriaClient client = new AltiriaClient("user@mydomain.com", "mypassword", 5000);
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
```

## Licencia

La licencia de esta librería es de tipo MIT. Para más información consultar el fichero de licencia.

## Ayuda

Utilizamos la sección de problemas de GitHub para tratar errores y valorar nuevas funciones.
Para cualquier problema durante la intergración contactar a través del email soporte@altiria.com.

