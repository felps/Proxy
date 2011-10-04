package br.usp.ime.ccsl.proxy.technician.proxies;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.ccsl.proxy.technician.roles.AirportMaintenance;
import br.usp.ime.ccsl.proxy.utils.clients.SyncInvocationHandler;

@WebService
public class AirportMaintenanceWSProxy extends WSProxy {

	@WebMethod
	public void dealWith(int incidentCode, int airplaneId){
		SyncInvocationHandler.invoke(currentWS, "dealWith", new String[] {""+incidentCode, ""+airplaneId});
	}
	
	
}
