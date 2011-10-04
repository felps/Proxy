package br.usp.ime.ccsl.proxy.technician.proxies;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.jws.WebMethod;

import br.usp.ime.ccsl.proxy.utils.logs.Logger4j;

public class WSProxy {

	protected ArrayList<URL> availableWebServices;
	protected URL currentWS;
	private String endpoint;

	public WSProxy() {
		super();
		availableWebServices = new ArrayList<URL>();
	}

	public void setURL(String url) {
		this.endpoint = url;
	}

	@WebMethod
	public void useService(String url) {

		Logger4j.log("PROXY: changing usage from " + currentWS + " to WS at "+ url);

		try {
			currentWS = new URL(url);

			if(! availableWebServices.contains(currentWS))
				availableWebServices.add(currentWS);

			Logger4j.log("PROXY: Changed current WS provider to "+url);
		} catch (MalformedURLException e) {
			Logger4j.log("ERROR: Tried to include a service using a malformed URL " + url);
			e.printStackTrace();
		}
	}

	@WebMethod
	public void addService(String url) {


		try {
			availableWebServices.add(new URL(url));
			Logger4j.log("PROXY: adding service at "+ url+ "to pool of available Web Services");
			System.out.println("PROXY: adding service at "+ url+ "to pool of available Web Services");

			System.out.println("Array length: "+availableWebServices.size());

			for (URL ws : availableWebServices) {
				System.out.println(ws.toExternalForm());
			}

			Thread.sleep(1500);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@WebMethod
	public void reportServiceOffline(String url) {

		System.out.println("Array length: "+availableWebServices.size());

		for (URL ws : availableWebServices) {
			System.out.println(ws.toExternalForm());
		}

		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Logger4j.log("PROXY: Web Service Provider at " + url + " reported as offline");

		//		Logger4j.log("PROXY: CurrentWS:         " + currentWS.toExternalForm());
		//		Logger4j.log("PROXY: URL:               " + url);
		//		Logger4j.log("PROXY: equals CurrentWS?: " + url.contentEquals(currentWS.toExternalForm()));
		//		Logger4j.log("PROXY: equals endpoint?:  " + url.contentEquals(this.endpoint));
		//		
		//		Logger4j.log("PROXY: changing usage from " + url + " to WS at "+currentWS);

		if(url.contentEquals(currentWS.toExternalForm()) || url.contentEquals(this.endpoint)){

			String previous = currentWS.toExternalForm();

			if(availableWebServices.remove(currentWS)){
				currentWS = availableWebServices.get(((int) Math.random()) * availableWebServices.size());
				Logger4j.log("PROXY2: changing usage from " + previous + " to WS at "+ currentWS.toExternalForm());
			}
			else Logger4j.log("PROXY: could not remove WS at " + currentWS.toExternalForm());

			System.out.println("Array length: "+availableWebServices.size());

			for (URL ws : availableWebServices) {
				System.out.println(ws.toExternalForm());
			}

			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				if (availableWebServices.remove(new URL(url)))
				{
					Logger4j.log("WS at "+ url + " removed from available services pool");
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// If there are no more services available, shutdown the proxy
		if (availableWebServices.size() == 0){
			Logger4j.log("PROXY: No more services available, shutting down");
			System.exit(0);
		}
	}

	@WebMethod
	public String getCurrentWS() {
		return currentWS.toExternalForm();
	}
}