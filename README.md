# This is an attempt to Grails 3

It is ~~*almost*~~ working, ~~I'm just having trouble getting it to process objects into JSON.~~

# Problem #1 - FIXED.

When trying to process a non simple type (eg a custom class), like below:

```java
	@GET
	@Produces(['application/json', 'application/xml'])
	@Path("/1")
	TestResult test1() {
	  TestResult testResult = new TestResult();
	  testResult.setFirstName("Donald");
	   testResult.setLastName("Jackson");
	   return testResult
	}
```	
I current get the Exception:

	ERROR com.sun.jersey.spi.container.ContainerResponse - A message body writer for Java class sample3.TestResult, and Java type class sample3.TestResult, and MIME media type application/json was not found.
	The registered message body writers compatible with the MIME media type are:
	application/json ->
	  com.sun.jersey.json.impl.provider.entity.JSONJAXBElementProvider$App
	  com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider$App
	  com.sun.jersey.json.impl.provider.entity.JSONListElementProvider$App
	*/* ->
	  com.sun.jersey.core.impl.provider.entity.FormProvider
	  com.sun.jersey.core.impl.provider.entity.StringProvider
	  com.sun.jersey.core.impl.provider.entity.ByteArrayProvider
	  com.sun.jersey.core.impl.provider.entity.FileProvider
	  com.sun.jersey.core.impl.provider.entity.InputStreamProvider
	  com.sun.jersey.core.impl.provider.entity.DataSourceProvider
	  com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider$General
	  com.sun.jersey.core.impl.provider.entity.ReaderProvider
	  com.sun.jersey.core.impl.provider.entity.DocumentProvider
	  com.sun.jersey.core.impl.provider.entity.StreamingOutputProvider
	  com.sun.jersey.core.impl.provider.entity.SourceProvider$SourceWriter
	  com.sun.jersey.server.impl.template.ViewableMessageBodyWriter
	  com.sun.jersey.json.impl.provider.entity.JSONJAXBElementProvider$General
	  com.sun.jersey.core.impl.provider.entity.XMLRootElementProvider$General
	  com.sun.jersey.core.impl.provider.entity.XMLListElementProvider$General
	  com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider$General
	  com.sun.jersey.json.impl.provider.entity.JSONListElementProvider$General

- 
- 
**Fixed using jackson-jaxrs providers**

# Problem #2 

Integration tests do not work (need to be refactored for Grails 3).

# Problem #3

create-resource scripts don't work, you will need to create resources manually for now.

#Grails JAX-RS Plugin

A [Grails](http://grails.org) plugin that supports the development of RESTful web services based on the [Java API for RESTful Web Services](http://jcp.org/en/jsr/detail?id=311) (JSR 311: JAX-RS).

It is targeted at developers who want to structure the web service layer of an application in a JSR 311 compatible way but still want to continue to use Grails' powerful features such as GORM, automated XML and JSON marshalling, Grails services, Grails filters and so on.
This plugin is an alternative to Grails' built-in mechanism for implementing RESTful web services. 

At the moment, plugin users may choose between [Jersey](https://jersey.dev.java.net) and [Restlet](http://www.restlet.org) as JAX-RS implementations. 
Both implementations are packaged with the plugin. 
Support for Restlet was added in version 0.2 of the plugin in order to support deployments to [Google App Engine](http://code.google.com/appengine).

Learn more at the [project wiki](https://github.com/krasserm/grails-jaxrs/wiki).
