package br.usp.ime.ccsl.proxy.technician.roles;

import java.net.MalformedURLException;
import java.net.URL;

import br.usp.ime.ccsl.proxy.utils.clients.AsyncInvocationHandler;
import br.usp.ime.ccsl.proxy.utils.logs.Logger4j;

public class AirportTechnician extends AirportCrew{

	public int airplaneUnderInspectionId;
	public String injured = "false";
	
	public AirportTechnician(int newTechnicianId) {
		this.crewId = newTechnicianId;
		this.crewType = TECHNICIAN;
		this.setURL();
	}
	
	/*
	 * A technician inspects airplanes at the airport
	 */

	public void inspectAirplane(int airplaneId) {
		
		Logger4j.log("TECHNICIAN: Tech crew is on its way!");
		waitRandomTimeBeforeEvent();
		
		this.reportArrival(TECHNICIAN, crewId, airplaneId);
		this.airplaneUnderInspectionId = airplaneId;
		this.logActions();
	}		
	
	public String isInjured(){
		return this.injured;
	}
	
	private void logActions() {
		waitRandomTimeBeforeEvent();
		this.identifySlip();
		return;
		
	}

	private void identifySlip() {
		Logger4j.log("TECHNICIAN: WOOPS!!! Slippery!");
		this.injured = "true";
		this.reportToCentral(SLIP, airplaneUnderInspectionId);
	}

	private void reportToCentral(int incidentCode, int airplaneId) {
		try {
			AsyncInvocationHandler invocation = new AsyncInvocationHandler(new URL("http://localhost:9011/central"), "dealWithTechnicianSlip", new String[] {""+crewId, ""+airplaneId});
			invocation.run();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		
	}
	
}
