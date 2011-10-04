package br.usp.ime.ccsl.proxy.technician.proxies;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import br.usp.ime.ccsl.proxy.utils.clients.SyncInvocationHandler;
import br.usp.ime.ccsl.proxy.utils.logs.Logger4j;
import eu.choreos.vv.Item;

@WebService
public class AirportTechnicianWSProxy extends WSProxy{

	@WebMethod
	public void inspectAirplane(@WebParam int airplaneId){

		Logger4j.log("Proxy: Technician -- inspectAirplane");
		Logger4j.log("Proxy: Technician -- currentWs:" + currentWS.toExternalForm());

		SyncInvocationHandler.invoke(currentWS, "inspectAirplane", new String[] {""+airplaneId});
//		AsyncInvocationHandler invoke = new AsyncInvocationHandler(currentWS, "inspectAirplane", new String[] {""+airplaneId}); 
//		invoke.run();
	}

	@WebMethod
	public String isInjured(){
		try {
			Item response;
			response = SyncInvocationHandler.invoke(currentWS, "isInjured", new String[] {});
			return response.getChild("return").getContent();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		return "";
	}
}
