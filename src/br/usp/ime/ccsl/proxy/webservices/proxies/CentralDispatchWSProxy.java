package br.usp.ime.ccsl.proxy.webservices.proxies;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.ccsl.proxy.roles.CentralDispatch;
import br.usp.ime.ccsl.proxy.utils.clients.AsyncInvocationHandler;
import br.usp.ime.ccsl.proxy.webservices.logs.Logger4j;

@WebService
public class CentralDispatchWSProxy {

	private String currentWS;
	
	@WebMethod
	public void airplaneArrival(int airplaneId){
		
		Logger4j.log("PROXY [CENTRAL]: Invoking airplaneArrival at "+currentWS);
		
		try {
			AsyncInvocationHandler invoke = new AsyncInvocationHandler(new URL(currentWS), "airplaneArrival", new String[] {""+airplaneId}); 
			invoke.run();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@WebMethod
	public void dealWithTechnicianSlip(int airplaneId, int crewId){
		
		Logger4j.log("PROXY [CENTRAL]: Invoking dealWithTechnicianSlip at "+currentWS);
		
		try {
			AsyncInvocationHandler invoke = new AsyncInvocationHandler(new URL(currentWS), "dealWithTechnicianSlip", new String[] {""+airplaneId, ""+crewId}); 
			invoke.run();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@WebMethod
	public void reportArrival(int personnel, int crewId, int airplaneId){
		
		Logger4j.log("PROXY [CENTRAL]: Invoking reportArrival at "+currentWS);
		
		try {
			AsyncInvocationHandler invoke = new AsyncInvocationHandler(new URL(currentWS), "reportArrival", new String[] {""+personnel, ""+crewId, ""+airplaneId}); 
			invoke.run();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@WebMethod
	public void reportTechnicianAided(int crewId){
		
		Logger4j.log("PROXY [CENTRAL]: Invoking reportTechnicianAided at "+currentWS);
		
		try {
			AsyncInvocationHandler invoke = new AsyncInvocationHandler(new URL(currentWS), "reportTechnicianAided", new String[] {""+crewId}); 
			invoke.run();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@WebMethod
	public void useService(String url){
		
		Logger4j.log("PROXY [CENTRAL]: changing usage from " + currentWS + " to WS at "+currentWS);
		
		try {
			new URL(url);
			currentWS = url;
			Logger4j.log("PROXY: Changed current  Maintenance WS to "+url);
		} catch (MalformedURLException e) {
			Logger4j.log("ERROR: Tried to include a service using a malformed URL " + url);
			e.printStackTrace();
		}
	}
}
