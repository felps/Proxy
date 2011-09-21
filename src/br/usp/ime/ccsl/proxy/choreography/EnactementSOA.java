package br.usp.ime.ccsl.proxy.choreography;

import javax.xml.ws.Endpoint;

import br.usp.ime.ccsl.proxy.webservices.CentralDispatchWS;

public class EnactementSOA {

	public static void main(String[] args) {
		CentralDispatchWS central = new CentralDispatchWS();
		Endpoint.publish("http://localhost:9010/central", central);
		
	} 
}
