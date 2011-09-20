package br.usp.ime.ccsl.proxy.roles;

import java.util.ArrayList;

public class CentralDispatch {

	private ArrayList<AirportCrew> crew;

	public CentralDispatch() {
		crew = new ArrayList<AirportCrew>();

		int ammountOfMedicalCrew = (int) (Math.random()*100);
		System.out.println("We have "+ ammountOfMedicalCrew + " medics on site");

		for(int i = 0; i < ammountOfMedicalCrew; i++)
			crew.add(new AirportMedical(i));

		int ammountOfTechnicianCrew = (int) (Math.random()*100);
		System.out.println("We have "+ ammountOfTechnicianCrew + " technicians on site");

		for(int i = 0; i < ammountOfTechnicianCrew; i++)
			crew.add(new AirportTechnician(i));

		int ammountOfMaintenanceCrew = (int) (Math.random()*100);
		System.out.println("We have "+ ammountOfMaintenanceCrew + " maintenance crews on site");

		for(int i = 0; i < ammountOfMedicalCrew; i++)
			crew.add(new AirportMaintenance(i));

	}
	/*
	 * The weather outside is freezing, and the floor is icy in some places.
	 * While inspecting the plane, technician slips.
	 * A wearable accelerometer detects the fall and reports to the central system
	 */

	/*	What must be done:
	 * 		Send medical crew to check on him 
	 * 		(The airport insurance policy requires this)
	 * 		Send a crew to remove the ice
	 * 		Call someone to replace him
	 */

	public void airplaneArrival(int airplaneId){
		// A new airplane just arrived at gate 43
		System.out.println("A new airplane just arrived at gate " + airplaneId);
		this.dispatchTechnician(airplaneId);

	}

	public void dealWithTechnicianSlip(int airplaneId, int crewId) {
		System.out.println("CENTRAL: Technician "+ crewId + " slip reported. Taking Actions!");
		removeMemberFromCrew(AirportCrew.TECHNICIAN, crewId);
		dispatchMedicalCrew(airplaneId);
		dispatchMaintenanceCrew(AirportMaintenance.ICE, airplaneId);
		dispatchTechnician(airplaneId);
	}

	private void removeMemberFromCrew(int crewType, int crewId) {
		for (AirportCrew team : crew) {

			if(team.crewType == crewType && team.crewId == crewId){
				((AirportTechnician) team).injured = true;
			}
		}
		
		System.out.println("CENTRAL: Technician member was injured and it is not fit for work anymore");
		
		
	}
	
	private void dispatchTechnician(int airplaneId) {
		for (AirportCrew team : crew) {

			if(team.crewType == AirportCrew.TECHNICIAN){
				if(!((AirportTechnician) team).injured){
					((AirportTechnician) team).inspectAirplane(airplaneId);
					return;
				}
			}
		}

		System.out.println("No technicians available!");
		System.exit(0);
	}

	private void dispatchMaintenanceCrew(int ice, int airplaneId) {
		for (AirportCrew team : crew) {

			if(team.crewType == AirportCrew.MAINTENANCE){
				((AirportMaintenance) team).dealWith(AirportMaintenance.ICE, airplaneId);
				return;
			}
		}
	}

	private void dispatchMedicalCrew(int airplaneId) {
		for (AirportCrew team : crew) {
			if(team.crewType == AirportCrew.MEDIC){
				((AirportMedical) team).firstAid(airplaneId, AirportMedical.TECHNICIAN);
				return;
			}
		}
	}

	public void reportTechnicianAidedBy(int crewId) {
		// TODO Auto-generated method stub

	}

	public void reportArrival(int personnel, int crewId, int airplaneId) {
		// TODO Auto-generated method stub
		System.out.println("Crew of type " + personnel + " Id " + crewId + " arrived on site " + airplaneId);;
	}
}
