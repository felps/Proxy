package br.usp.ime.ccsl.proxy.technician.proxies;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.ccsl.proxy.utils.clients.AsyncInvocationHandler;
import br.usp.ime.ccsl.proxy.utils.logs.Logger4j;

@WebService
public class CentralDispatchWSProxy extends WSProxy{

	@WebMethod
	public void airplaneArrival(int airplaneId){

		Logger4j.log("PROXY [CENTRAL]: Invoking airplaneArrival at "+currentWS);

		AsyncInvocationHandler invoke = new AsyncInvocationHandler(currentWS, "airplaneArrival", new String[] {""+airplaneId}); 
		invoke.run();
	}

	@WebMethod
	public void dealWithTechnicianSlip(int airplaneId, int crewId){

		Logger4j.log("PROXY [CENTRAL]: Invoking dealWithTechnicianSlip at "+currentWS);

		AsyncInvocationHandler invoke = new AsyncInvocationHandler(currentWS, "dealWithTechnicianSlip", new String[] {""+airplaneId, ""+crewId}); 
		invoke.run();
	}

	@WebMethod
	public void reportArrival(int personnel, int crewId, int airplaneId){

		Logger4j.log("PROXY [CENTRAL]: Invoking reportArrival at "+currentWS);

		AsyncInvocationHandler invoke = new AsyncInvocationHandler(currentWS, "reportArrival", new String[] {""+personnel, ""+crewId, ""+airplaneId}); 
		invoke.run();
	}

	@WebMethod
	public void reportTechnicianAided(int crewId){

		Logger4j.log("PROXY [CENTRAL]: Invoking reportTechnicianAided at "+currentWS);

		AsyncInvocationHandler invoke = new AsyncInvocationHandler(currentWS, "reportTechnicianAided", new String[] {""+crewId});
		invoke.run();
	}
}
