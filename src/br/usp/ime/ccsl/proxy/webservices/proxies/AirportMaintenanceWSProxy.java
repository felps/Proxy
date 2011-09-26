package br.usp.ime.ccsl.proxy.webservices.proxies;

import java.lang.reflect.InvocationHandler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.usp.ime.ccsl.proxy.utils.clients.AsyncInvocationHandler;
import br.usp.ime.ccsl.proxy.utils.clients.SyncInvocationHandler;
import br.usp.ime.ccsl.proxy.webservices.logs.*;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.ccsl.proxy.roles.AirportMaintenance;

@WebService
public class AirportMaintenanceWSProxy {

	private List<String> availableServices = new ArrayList<String>();
	
	public AirportMaintenance maintenance;

	private String currentWS;
	
	@WebMethod
	public void dealWith(int incidentCode, int airplaneId){
		try {
			SyncInvocationHandler.invoke(new URL(currentWS), "dealWith", new String[] {""+incidentCode, ""+airplaneId});
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@WebMethod
	public void useService(String url){
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
