package br.usp.ime.ccsl.proxy.roles;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.ws.Endpoint;

import br.usp.ime.ccsl.proxy.utils.clients.AsyncInvocationHandler;
import br.usp.ime.ccsl.proxy.utils.clients.SyncInvocationHandler;
import br.usp.ime.ccsl.proxy.webservices.AirportMaintenanceWS;
import br.usp.ime.ccsl.proxy.webservices.AirportMedicalWS;
import br.usp.ime.ccsl.proxy.webservices.AirportTechnicianWS;
import br.usp.ime.ccsl.proxy.webservices.logs.Logger4j;
import eu.choreos.vv.Item;

public class CentralDispatch {

	private ArrayList<URL> crew;

	public CentralDispatch() {
		//TODO: Rename to something like AvailableServices
		crew = new ArrayList<URL>();

		int indexOffset = 0;
		
		int ammountOfMedicalCrew = 2;//(int) (Math.random()*100);
		Logger4j.log("CENTRAL: We have "+ ammountOfMedicalCrew + " medics on site");

		for(int i = 0; i < ammountOfMedicalCrew; i++){
			AirportMedicalWS medic = new AirportMedicalWS(i);
			crew.add(medic.medic.serviceURL);
			Endpoint.publish(crew.get(i+indexOffset).toExternalForm(), medic);
		}

		indexOffset += ammountOfMedicalCrew;
		
		int ammountOfTechnicianCrew = 5; //(int) (Math.random()*100);
		
		System.out.println("We have "+ ammountOfTechnicianCrew + " technicians on site");

		for(int i = 0; i < ammountOfTechnicianCrew; i++){
			AirportTechnicianWS techie = new AirportTechnicianWS(i);
			crew.add(techie.technician.serviceURL);
			Endpoint.publish(crew.get(i+indexOffset).toExternalForm(), techie);
		}

		indexOffset += ammountOfTechnicianCrew;
		
		int ammountOfMaintenanceCrew = 2;//(int) (Math.random()*100);
		System.out.println("CENTRAL: We have "+ ammountOfMaintenanceCrew + " maintenance crews on site");

		for(int i = 0; i < ammountOfMedicalCrew; i++){
			AirportMaintenanceWS maintenance = new AirportMaintenanceWS(i);
			crew.add(maintenance.maintenance.serviceURL);
			Endpoint.publish(crew.get(i+indexOffset).toExternalForm(), maintenance);
		}
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
		Logger4j.log("CENTRAL: A new airplane just arrived at gate " + airplaneId);
		this.dispatchTechnician(airplaneId);

	}

	public void dealWithTechnicianSlip(int airplaneId, int crewId) {
		Logger4j.log("CENTRAL: Technician "+ crewId + " slip reported. Taking Actions!");
		removeMemberFromCrew(AirportCrew.TECHNICIAN, crewId);
		dispatchMedicalCrew(airplaneId);
		dispatchMaintenanceCrew(AirportMaintenance.ICE, airplaneId);
		dispatchTechnician(airplaneId);
	}

	private synchronized void removeMemberFromCrew(int crewType, int crewId) {
		
		ArrayList<URL> originalList = (ArrayList<URL>) crew.clone();
		
		for (URL team : originalList) {
			if(team.toExternalForm().contentEquals("http://localhost:9010/technician/"+crewId))
				crew.remove(team);
		}

		Logger4j.log("CENTRAL: Technician member was injured and it is not fit for work anymore");


	}

	private void dispatchTechnician(int airplaneId) {
		
		Logger4j.log("CENTRAL: Technician dispatch");
		
		for (URL team : crew) {

			if(getCrewTypeFromURL(team) == AirportCrew.TECHNICIAN){

				Item response = SyncInvocationHandler.invoke(team, "isInjured", (new String[] {}));

				SyncInvocationHandler.iterateOverChildrenAndPrint(response);

				if("false".contentEquals(response.getChildren().get(0).getContent())){
					Logger4j.log("Dispatching crew "+team.toExternalForm());
					AsyncInvocationHandler invoke = new AsyncInvocationHandler(team, "inspectAirplane", (new String[] {""+airplaneId}));
					invoke.run();
					return;
				}
			}
		}

		Logger4j.log("CENTRAL: No technicians available!");
		System.exit(0);
	}

	private void dispatchMaintenanceCrew(int ice, int airplaneId) {
		for (URL team : crew) {

			if(getCrewTypeFromURL(team) == AirportCrew.MAINTENANCE){
				//((AirportMaintenance) team).dealWith(AirportMaintenance.ICE, airplaneId);
				AsyncInvocationHandler invoke = new AsyncInvocationHandler(team, "dealWith", new String[]{""+AirportCrew.MAINTENANCE, ""+airplaneId});
				invoke.run();
				return;
			}
		}
	}

	private void dispatchMedicalCrew(int airplaneId) {
		for (URL team : crew) {
			if(getCrewTypeFromURL(team)== AirportCrew.MEDIC){
				AsyncInvocationHandler invoke = new AsyncInvocationHandler(team, "firstAid", new String[]{""+airplaneId, ""+AirportMedical.TECHNICIAN});
				invoke.run();
				
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
		String name = "";
		switch(personnel){
			case AirportCrew.TECHNICIAN:
				name = "Technic";
				break;
			case AirportCrew.MEDIC:
				name = "Medic";
				break;
			case AirportCrew.MAINTENANCE:
				name = "Maintenance";
				break;
		}
		Logger4j.log("CENTRAL: " + name + "Crew ( Id " + crewId + " ) arrived on site " + airplaneId);
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
