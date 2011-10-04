package br.usp.ime.ccsl.proxy.technician.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.ccsl.proxy.technician.roles.CentralDispatch;

@WebService
public class CentralDispatchWS {

	CentralDispatch central = new CentralDispatch();
	
	@WebMethod
	public void airplaneArrival(int airplaneId){
		central.airplaneArrival(airplaneId);
	}
	
	@WebMethod
	public void dealWithTechnicianSlip(int airplaneId, int crewId){
		central.dealWithTechnicianSlip(airplaneId, crewId);
	}
	
	@WebMethod
	public void reportArrival(int personnel, int crewId, int airplaneId){
		central.reportArrival(personnel, crewId, airplaneId);
	}
	
	@WebMethod
	public void reportTechnicianAided(int crewId){
		central.reportTechnicianAidedBy(crewId);
	}
	
}
