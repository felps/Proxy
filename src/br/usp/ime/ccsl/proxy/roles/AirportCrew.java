package br.usp.ime.ccsl.proxy.roles;

import br.usp.ime.ccsl.proxy.choreography.Enactement;

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

	/*
	 * Parties’ phones inform central system of their arrival
	 */
	
	protected void reportArrival(int personnel, int crewId, int airplaneId) {
		Enactement.central.reportArrival(personnel, crewId, airplaneId);
		
	}

	protected void waitRandomTimeBeforeEvent() {
		int timeBeforeEvent = (int) Math.abs(Math.random()*10000);
		try {
			Thread.sleep(timeBeforeEvent);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
