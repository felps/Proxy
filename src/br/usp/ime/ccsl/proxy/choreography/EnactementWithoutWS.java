package br.usp.ime.ccsl.proxy.choreography;

import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import br.usp.ime.ccsl.proxy.roles.CentralDispatch;
import br.usp.ime.ccsl.proxy.webservices.AirportTechnicianWS;

public class EnactementWithoutWS {

	public static CentralDispatch central =  new CentralDispatch();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testeWS();
		central.airplaneArrival((int) Math.random()*50);
	}
	
	private static void testeWS(){
		AirportTechnicianWS technicianService1 = new AirportTechnicianWS(1);
		Endpoint.publish("http://localhost:9010/technician1", technicianService1);
		
		
	}

}
