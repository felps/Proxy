package br.usp.ime.ccsl.proxy.choreography;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Endpoint;

import br.usp.ime.ccsl.proxy.technician.webservices.CentralDispatchWS;
import br.usp.ime.ccsl.proxy.utils.clients.SyncInvocationHandler;
import br.usp.ime.ccsl.proxy.utils.logs.Logger4j;

public class EnactementSOA {

	public static void main(String[] args) throws MalformedURLException {
		Logger4j.log("ENACTEMENT: New Enactment");
		
		CentralDispatchWS central = new CentralDispatchWS();
		Endpoint.publish("http://localhost:9010/central", central);
		
		SyncInvocationHandler.invoke(new URL ( "http://localhost:9010/central"), "airplaneArrival", new String[] {"43"});
	} 
}
