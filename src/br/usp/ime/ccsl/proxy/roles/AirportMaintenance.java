package br.usp.ime.ccsl.proxy.roles;

public class AirportMaintenance extends AirportCrew{
	public static final int ICE = 0;

	public AirportMaintenance(int newCrewId) {
		this.crewId = newCrewId;
		this.crewType = MAINTENANCE;
	}

	public void dealWith(int incidentCode, int airplaneId) {
	
		System.out.println("MAINTENANCE: Crew is on its way");
		waitRandomTimeBeforeEvent();
		reportArrival(MAINTENANCE, crewId, airplaneId);
		if(incidentCode == ICE)
			clearIce(airplaneId);

	}

	private void clearIce(int airplaneId) {
		// Ice Cleared from airpĺane
		System.out.println("MAINTENANCE: Ice cleared from gate "+ airplaneId);
	}
}
