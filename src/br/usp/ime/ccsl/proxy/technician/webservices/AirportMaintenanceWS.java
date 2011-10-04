package br.usp.ime.ccsl.proxy.technician.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.ccsl.proxy.technician.roles.AirportMaintenance;

@WebService
public class AirportMaintenanceWS {

	public AirportMaintenance maintenance;
	
	public AirportMaintenanceWS(int crewId) {
		maintenance = new AirportMaintenance(crewId);
	}
	
	@WebMethod
	public void dealWith(int incidentCode, int airplaneId){
		maintenance.dealWith(incidentCode, airplaneId);
	}
}
