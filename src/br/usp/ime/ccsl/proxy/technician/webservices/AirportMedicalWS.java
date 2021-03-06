package br.usp.ime.ccsl.proxy.technician.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ime.ccsl.proxy.technician.roles.AirportMedical;

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
