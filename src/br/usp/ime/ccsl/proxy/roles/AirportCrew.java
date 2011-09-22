package br.usp.ime.ccsl.proxy.roles;

import java.net.MalformedURLException;
import java.net.URL;

import br.usp.ime.ccsl.proxy.utils.clients.AsyncInvocationHandler;

public class AirportCrew {
	/*
	 * Personnel Types
	 */
	public static final int TECHNICIAN = 0;
	public static final int MEDIC = 1;
	public static final int MAINTENANCE = 2;

	/*
	 * Incident Types
	 */
	public static final int SLIP = 1;
	
	/*
	 * Crew Identification
	 */
	public int crewId;
	public int crewType;
	public URL serviceURL;

	/*
	 * Parties’ phones inform central system of their arrival
	 */
	public void setURL() {
		String serviceURL;
		
		switch (crewType) {
		case MAINTENANCE: 
			serviceURL = "http://localhost:9010/maintenance/" + crewId;
			break;
				
		case TECHNICIAN: 
			serviceURL = "http://localhost:9010/technician/" + crewId;
			break;
				     
		case MEDIC:
			serviceURL = "http://localhost:9010/medical/" + crewId;
			break;
		
		default:	 
			System.out.println("ERROR: Unknown CrewType");
			return;
		}
		
		try {
			this.serviceURL = new URL(serviceURL);
		} catch (MalformedURLException e) {
			System.out.println("Error: Malformed URL:" + serviceURL);
			e.printStackTrace();
		}
	}
	
	protected void reportArrival(int personnel, int crewId, int airplaneId) {
		//EnactementWithoutWS.central.reportArrival(personnel, crewId, airplaneId);
		try {
			AsyncInvocationHandler invoke;
			invoke = new AsyncInvocationHandler(new URL("http://localhost:9010/central"), "reportArrival", (new String[] {""+personnel, ""+crewId, ""+airplaneId}));
			invoke.run();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	protected void waitRandomTimeBeforeEvent() {
		int timeBeforeEvent = (int) Math.abs(Math.random()*1000);
		try {
			Thread.sleep(timeBeforeEvent);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
