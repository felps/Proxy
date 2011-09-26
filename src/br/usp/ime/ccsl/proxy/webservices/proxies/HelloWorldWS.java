package br.usp.ime.ccsl.proxy.webservices.proxies;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class HelloWorldWS {

	public static void main(String[] args) {
		HelloWorldWS serverImplementation = new HelloWorldWS();
		Endpoint.publish("http://localhost:9010/hello", serverImplementation);
	}
	
	@WebMethod
	public String greet(@WebParam String name){
		return "Hello " + name + "! Nice to meet you";
	}
	
}
