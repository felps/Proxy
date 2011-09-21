package br.usp.ime.ccsl.proxy.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.ccsl.proxy.roles.AirportMedical;

@WebService
public class AirportMedicalWS {

	private AirportMedical medic;
	
	public AirportMedicalWS(int newCrewId) {
		medic = new AirportMedical(newCrewId);
	}
	
	@WebMethod
	public void firstAid(int airplaneId, int injuredPersonnel){
		medic.firstAid(airplaneId, injuredPersonnel);
	}
	
}
