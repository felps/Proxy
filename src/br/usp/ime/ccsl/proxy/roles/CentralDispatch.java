package br.usp.ime.ccsl.proxy.roles;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import br.usp.ime.ccsl.proxy.utils.*;
import br.usp.ime.ccsl.proxy.utils.clients.InvocationHandler;

public class CentralDispatch {

	private ArrayList<URL> crew;

	public CentralDispatch() {
		crew = new ArrayList<URL>();

		int ammountOfMedicalCrew = (int) (Math.random()*100);
		System.out.println("We have "+ ammountOfMedicalCrew + " medics on site");

		for(int i = 0; i < ammountOfMedicalCrew; i++)
			crew.add((new AirportMedical(i)).serviceURL);

		int ammountOfTechnicianCrew = (int) (Math.random()*100);
		System.out.println("We have "+ ammountOfTechnicianCrew + " technicians on site");

		for(int i = 0; i < ammountOfTechnicianCrew; i++)
			crew.add((new AirportTechnician(i)).serviceURL);

		int ammountOfMaintenanceCrew = (int) (Math.random()*100);
		System.out.println("We have "+ ammountOfMaintenanceCrew + " maintenance crews on site");

		for(int i = 0; i < ammountOfMedicalCrew; i++)
			crew.add((new AirportMaintenance(i)).serviceURL);

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
		for (URL team : crew) {
			if(team.toExternalForm().contentEquals("http://localhost:9010/technician/"+crewId))
				crew.remove(team);
		}
		
		System.out.println("CENTRAL: Technician member was injured and it is not fit for work anymore");
		
		
	}
	
	private void dispatchTechnician(int airplaneId) {
		for (URL team : crew) {

			if(getCrewTypeFromURL(team) == AirportCrew.TECHNICIAN){
				HashMap map;
				System.out.println(team.toExternalForm());
				try {
					Thread.sleep(150000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				map = InvocationHandler.invoke(team, "isInjured", (new String[] {}));
				
				for (Object key : map.keySet()) {
					System.out.println(key.toString());
				}
			}
		}

		System.out.println("No technicians available!");
		System.exit(0);
	}

	private void dispatchMaintenanceCrew(int ice, int airplaneId) {
		for (URL team : crew) {

			if(getCrewTypeFromURL(team) == AirportCrew.MAINTENANCE){
				//((AirportMaintenance) team).dealWith(AirportMaintenance.ICE, airplaneId);
				return;
			}
		}
	}

	private void dispatchMedicalCrew(int airplaneId) {
		for (URL team : crew) {
			if(getCrewTypeFromURL(team)== AirportCrew.MEDIC){
				//((AirportMedical) team).firstAid(airplaneId, AirportMedical.TECHNICIAN);
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
	
	private int getCrewTypeFromURL(URL serviceURL){
		if (serviceURL.toExternalForm().contains("maintenance"))
			return AirportCrew.MAINTENANCE;
		if (serviceURL.toExternalForm().contains("technician"))
			return AirportCrew.TECHNICIAN;
		if (serviceURL.toExternalForm().contains("medical"))
			return AirportCrew.MEDIC;
		return -1;
	}
}
