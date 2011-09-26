package br.usp.ime.ccsl.proxy.webservices;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.ccsl.proxy.roles.AirportMaintenance;
import br.usp.ime.ccsl.proxy.roles.AirportMedical;
import br.usp.ime.ccsl.proxy.utils.clients.SyncInvocationHandler;
import br.usp.ime.ccsl.proxy.webservices.logs.Logger4j;

@WebService
public class AirportMedicalWS {

	public AirportMedical medic;

	public AirportMedicalWS(int newCrewId) {
		medic = new AirportMedical(newCrewId);
	}

	@WebMethod
	public void firstAid(int airplaneId, int injuredPersonnel){
		medic.firstAid(airplaneId, injuredPersonnel);
	}
	
}
