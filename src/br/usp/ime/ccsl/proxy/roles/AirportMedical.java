package br.usp.ime.ccsl.proxy.roles;

import br.usp.ime.ccsl.proxy.choreography.EnactementWithoutWS;

public class AirportMedical extends AirportCrew{
	
	public AirportMedical(int newCrewId) {
		this.crewId = newCrewId;
		this.crewType = MEDIC;
		this.setURL();
	}

	public void firstAid(int airplaneId, int injuredPersonnel) {
		System.out.println("MEDICAL: Crew is on its way");
		waitRandomTimeBeforeEvent();
		reportArrival(MEDIC, crewId, airplaneId);
		aidTechnician(airplaneId);
		
	}

	private void aidTechnician(int airplaneId) {
		performFirstAid();
		reportTechnicianStatusToCentral();
		
	}

	private void reportTechnicianStatusToCentral() {
		EnactementWithoutWS.central.reportTechnicianAidedBy(this.crewId);
		
		
	}

	private void performFirstAid() {
		// Technician taken proper care. First aid applied
		System.out.println("MEDICAL: Technician taken proper care. First aid applied");
	}

}
