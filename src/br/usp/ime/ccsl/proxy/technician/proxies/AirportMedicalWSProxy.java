package br.usp.ime.ccsl.proxy.technician.proxies;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.ccsl.proxy.utils.clients.SyncInvocationHandler;

@WebService
public class AirportMedicalWSProxy extends WSProxy {

	@WebMethod
	public void firstAid(int airplaneId, int injuredPersonnel){
		SyncInvocationHandler.invoke(currentWS, "firstAid", new String[] {""+airplaneId, ""+injuredPersonnel});
	}

}
