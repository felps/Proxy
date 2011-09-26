package br.usp.ime.ccsl.proxy.choreography;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Endpoint;

import br.usp.ime.ccsl.proxy.utils.clients.SyncInvocationHandler;
import br.usp.ime.ccsl.proxy.webservices.CentralDispatchWS;
import br.usp.ime.ccsl.proxy.webservices.logs.Logger4j;
import br.usp.ime.ccsl.proxy.webservices.proxies.CentralDispatchWSProxy;

public class EnactementProxySOA {

	public static void main(String[] args) throws MalformedURLException {

		Logger4j.log("ENACTEMENT: New Enactment using proxy");

		CentralDispatchWS central = new CentralDispatchWS();
		Endpoint.publish("http://localhost:9010/central/0", central);

		CentralDispatchWSProxy proxy = new CentralDispatchWSProxy();
		proxy.useService("http://localhost:9010/central/0");
		Endpoint.publish("http://localhost:9010/central", proxy);

		SyncInvocationHandler.invoke(new URL ( "http://localhost:9010/central/0"), "airplaneArrival", new String[] {"43"});
	} 
}
