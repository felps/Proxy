package br.usp.ime.ccsl.proxy.webservices.proxies;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import eu.choreos.vv.Item;

import br.usp.ime.ccsl.proxy.roles.AirportTechnician;
import br.usp.ime.ccsl.proxy.utils.clients.AsyncInvocationHandler;
import br.usp.ime.ccsl.proxy.utils.clients.SyncInvocationHandler;
import br.usp.ime.ccsl.proxy.webservices.logs.Logger4j;

@WebService
public class AirportTechnicianWSProxy {

	private String currentWS;

	@WebMethod
	public void inspectAirplane(@WebParam int airplaneId){
		try {
			AsyncInvocationHandler invoke = new AsyncInvocationHandler(new URL(currentWS), "inspectAirplane", new String[] {""+airplaneId}); 
			invoke.run();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@WebMethod
	public String isInjured(){
		try {
			Item response;
			response = SyncInvocationHandler.invoke(new URL(currentWS), "isInjured", new String[] {});
			return response.getChild("return").getContent();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		return "";
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
