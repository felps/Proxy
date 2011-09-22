package br.usp.ime.ccsl.proxy.roles;

import br.usp.ime.ccsl.proxy.webservices.logs.Logger4j;

public class AirportMaintenance extends AirportCrew{
	public static final int ICE = 0;

	public AirportMaintenance(int newCrewId) {
		this.crewId = newCrewId;
		this.crewType = MAINTENANCE;
		this.setURL();
	}

	public void dealWith(int incidentCode, int airplaneId) {
	
		Logger4j.log("MAINTENANCE: Crew is on its way");
		waitRandomTimeBeforeEvent();
		reportArrival(MAINTENANCE, crewId, airplaneId);
		if(incidentCode == ICE)
			clearIce(airplaneId);

	}

	private void clearIce(int airplaneId) {
		// Ice Cleared from airpĺane
		Logger4j.log("MAINTENANCE: Ice cleared from gate "+ airplaneId);
	}
}
