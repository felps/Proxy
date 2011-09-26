package br.usp.ime.ccsl.proxy.webservices;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import br.usp.ime.ccsl.proxy.roles.AirportTechnician;
import br.usp.ime.ccsl.proxy.utils.clients.SyncInvocationHandler;
import br.usp.ime.ccsl.proxy.webservices.logs.Logger4j;
import eu.choreos.vv.Item;

@WebService
public class AirportTechnicianWS {
	
	public AirportTechnician technician;
	
	public AirportTechnicianWS(int newTechnicianId) {
		technician = new AirportTechnician(newTechnicianId);
	}

	@WebMethod
	public void inspectAirplane(@WebParam int airplaneId){
		technician.inspectAirplane(airplaneId);
	}
	
	@WebMethod
	public String isInjured(){
		return technician.isInjured();
	}
	
}
