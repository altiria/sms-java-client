![](http://static.altiria.com/wp-content/themes/altiria/images/logo-altiria.png)

## ¡Atención! Este proyecto aún se encuentra en desarrollo. Pronto se publicará la versión final para su uso.


# Altiria, cliente SMS Java

 ![](https://img.shields.io/badge/version-0.9.0-blue.svg)

Altiria SMS Java es un cliente que simplifica al máximo la integración de nuestro API para Java utilizando el gestor de dependencias **maven**. Por el momento, esta librería abarca las funciones más básicas:
- **Envíos de SMS individuales** con las siguientes características:
  - sencillos
  - concatenados
  - certificación de entrega con o sin identificador
  - certificado digital de entrega
  - uso de remitente
  - seleccionar codificación
- **Consultas de crédito**

## Requisitos

- Java: ^1.7
- [maven](https://maven.apache.org/)

## Instalación

La forma recomendada de instalar el cliente Altiria para Java es a través de maven .

### Editando el fichero composer.json y actualizando el proyecto

En este caso, añadir el siguiente fragmento al fichero composer.json.

```xml
<dependency>
	<groupId>org.altiria.sms.api</groupId>
	<artifactId>sms-api</artifactId>
	<version>1.0</version>
</dependency>
```

## Ejemplos de uso

### Envío de SMS

A continuación se describen cada una de las posibilidades de uso de la librería para realizar envíos de SMS.

#### Ejemplo básico

Se trata de la opción más sencilla para realizar un envío de SMS.

```java
try {
    AltiriaClient client = new AltiriaClient("miusuario@email.com", "contraseña");
    client.sendSms("346XXXXXXXX", "Mensaje de prueba");
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

#### Ejemplo básico con timeout personalizado

Permite fijar el tiempo de respuesta en milisegundos. Si se supera se lanzará una **ConnectionException**.
Por defecto el tiempo de respuesta es de 10 segundos, pero puede ser ajustado entre 1 y 30 segundos.

```java
try {
    AltiriaClient client = new AltiriaClient("miusuario@email.com", "contraseña", 5000);
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
    AltiriaClient client = new AltiriaClient("miusuario@email.com", "contraseña");
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
    AltiriaClient client = new AltiriaClient("miusuario@email.com", "contraseña");
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

A continuación se describen cada una de las posibilidades de uso de la librería para consultar el crédito.

#### Ejemplo básico

Este ejemplo no incluye los parámetros opcionales.

```java
try {
    AltiriaClient client = new AltiriaClient("miusuario@email.com", "contraseña");
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

#### Ejemplo con todos los parámetros

Este ejemplo incluye los parámetros opcionales.

```java
try {
    AltiriaClient client = new AltiriaClient("miusuario@email.com", "contraseña", 5000);
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

