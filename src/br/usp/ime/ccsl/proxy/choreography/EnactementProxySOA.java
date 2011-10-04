package br.usp.ime.ccsl.proxy.choreography;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Endpoint;

import br.usp.ime.ccsl.proxy.technician.proxies.AirportMaintenanceWSProxy;
import br.usp.ime.ccsl.proxy.technician.proxies.AirportMedicalWSProxy;
import br.usp.ime.ccsl.proxy.technician.proxies.AirportTechnicianWSProxy;
import br.usp.ime.ccsl.proxy.technician.proxies.CentralDispatchWSProxy;
import br.usp.ime.ccsl.proxy.technician.webservices.AirportMaintenanceWS;
import br.usp.ime.ccsl.proxy.technician.webservices.AirportMedicalWS;
import br.usp.ime.ccsl.proxy.technician.webservices.AirportTechnicianWS;
import br.usp.ime.ccsl.proxy.technician.webservices.CentralDispatchWS;
import br.usp.ime.ccsl.proxy.utils.clients.SyncInvocationHandler;
import br.usp.ime.ccsl.proxy.utils.logs.Logger4j;

public class EnactementProxySOA {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		Logger4j.log("ENACTEMENT: New Enactment using proxy");


		createCentralDispatchServices();


		createAirportMedicalServices();


		createAirportMaintenanceServices();


		createAirportTechnicianServices(3);
		
		System.out.println("Done creating services...");
		
		Thread.sleep(150);

		SyncInvocationHandler.invoke(new URL ( "http://localhost:9011/central"), "airplaneArrival", new String[] {"43"});
	}

	private static void createAirportTechnicianServices(int quantity) {
		AirportTechnicianWSProxy proxy = new AirportTechnicianWSProxy();
		Endpoint.publish("http://localhost:9011/technician", proxy);

		for (int i=0; i < quantity; i++){
			AirportTechnicianWS techie = new AirportTechnicianWS(i);
			Endpoint.publish("http://localhost:9010/technician/"+i, techie);
			proxy.addService("http://localhost:9010/technician/"+i);
		}
		
		proxy.setURL("http://localhost:9011/technician");
		proxy.useService("http://localhost:9010/technician/0");
	}

	private static void createAirportMaintenanceServices() {
		AirportMaintenanceWSProxy proxy = new AirportMaintenanceWSProxy();
		Endpoint.publish("http://localhost:9011/maintenance", proxy);

		AirportMaintenanceWS maintenance = new AirportMaintenanceWS(0);
		Endpoint.publish("http://localhost:9010/maintenance/0", maintenance);
		
		proxy.setURL("http://localhost:9011/maintenance");
		proxy.useService("http://localhost:9010/maintenance/0");
	}

	private static void createAirportMedicalServices() {
		AirportMedicalWSProxy proxy = new AirportMedicalWSProxy();
		Endpoint.publish("http://localhost:9011/medical", proxy);

		AirportMedicalWS medical = new AirportMedicalWS(0);
		Endpoint.publish("http://localhost:9010/medical/0", medical);

		proxy.setURL("http://localhost:9011/medical");
		proxy.useService("http://localhost:9010/medical/0");
	}

	private static void createCentralDispatchServices() {
		CentralDispatchWSProxy proxy = new CentralDispatchWSProxy();
		Endpoint.publish("http://localhost:9011/central", proxy);

		CentralDispatchWS central = new CentralDispatchWS();
		Endpoint.publish("http://localhost:9010/central/0", central);

		proxy.setURL("http://localhost:9011/central");
		proxy.useService("http://localhost:9010/central/0");
	} 
}
