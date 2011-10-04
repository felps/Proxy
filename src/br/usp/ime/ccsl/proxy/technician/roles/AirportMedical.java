package br.usp.ime.ccsl.proxy.technician.roles;

import br.usp.ime.ccsl.proxy.utils.logs.Logger4j;

public class AirportMedical extends AirportCrew{
	
	public AirportMedical(int newCrewId) {
		this.crewId = newCrewId;
		this.crewType = MEDIC;
		this.setURL();
	}

	public void firstAid(int airplaneId, int injuredPersonnel) {
		Logger4j.log("MEDICAL: Crew is on its way");
		waitRandomTimeBeforeEvent();
		reportArrival(MEDIC, crewId, airplaneId);
		aidTechnician(airplaneId);
		
	}

	private void aidTechnician(int airplaneId) {
		performFirstAid();
		reportTechnicianStatusToCentral();
		
	}

	private void reportTechnicianStatusToCentral() {
	//	EnactementWithoutWS.central.reportTechnicianAidedBy(this.crewId);
		
		
	}

	private void performFirstAid() {
		// Technician taken proper care. First aid applied
		Logger4j.log("MEDICAL: Technician taken proper care. First aid applied");
	}

}
