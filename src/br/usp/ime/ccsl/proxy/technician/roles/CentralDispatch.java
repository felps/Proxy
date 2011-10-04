package br.usp.ime.ccsl.proxy.technician.roles;

import java.net.MalformedURLException;
import java.net.URL;

import br.usp.ime.ccsl.proxy.utils.clients.AsyncInvocationHandler;
import br.usp.ime.ccsl.proxy.utils.clients.SyncInvocationHandler;
import br.usp.ime.ccsl.proxy.utils.logs.Logger4j;

public class CentralDispatch {

	private URL technicianProxy, medicalProxy, maintenanceProxy;

	public CentralDispatch() {
		try {
			technicianProxy = new URL("http://localhost:9011/technician");
			medicalProxy = new URL("http://localhost:9011/medical");
			maintenanceProxy = new URL("http://localhost:9011/maintenance");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		Logger4j.log("CENTRAL: Technician member " +
				crewId + " was injured and it is not fit for work anymore");

		SyncInvocationHandler.invoke(technicianProxy, "reportServiceOffline", (new String[] {technicianProxy.toExternalForm()}));
	}

	private void dispatchTechnician(int airplaneId) {

		Logger4j.log("CENTRAL: Technician dispatch");

		AsyncInvocationHandler invoke = new AsyncInvocationHandler(technicianProxy, "inspectAirplane", (new String[] {""+airplaneId}));
		invoke.run();
	}

	private void dispatchMaintenanceCrew(int ice, int airplaneId) {
		Logger4j.log("CENTRAL: Maintenance dispatch");

		AsyncInvocationHandler invoke = new AsyncInvocationHandler(maintenanceProxy, "dealWith", new String[]{""+AirportCrew.MAINTENANCE, ""+airplaneId});
		invoke.run();
		return;
	}

	private void dispatchMedicalCrew(int airplaneId) {
		Logger4j.log("CENTRAL: Medical dispatch");

		AsyncInvocationHandler invoke = new AsyncInvocationHandler(medicalProxy, "firstAid", new String[]{""+airplaneId, ""+AirportMedical.TECHNICIAN});
		invoke.run();
		return;
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
}
