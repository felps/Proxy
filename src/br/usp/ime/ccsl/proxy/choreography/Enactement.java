package br.usp.ime.ccsl.proxy.choreography;

import br.usp.ime.ccsl.proxy.roles.CentralDispatch;

public class Enactement {

	public static CentralDispatch central =  new CentralDispatch();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		central.airplaneArrival((int) Math.random()*50);
	}

}
