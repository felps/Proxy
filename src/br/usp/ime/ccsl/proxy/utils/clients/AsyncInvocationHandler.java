package br.usp.ime.ccsl.proxy.utils.clients;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.apache.xmlbeans.XmlException;

import eu.choreos.vv.Item;
import eu.choreos.vv.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

public class AsyncInvocationHandler implements Runnable{
	private URL serviceLocation;
	private String operationName;
	private String[] args;

	public AsyncInvocationHandler(URL serviceLocation, String operationName, String[] args) {
		this.serviceLocation = serviceLocation;
		this.args = args;
		this.operationName = operationName;
	}
	@Override
	public void run(){
		WSClient client;

		try {
			client = new WSClient(serviceLocation.toExternalForm()+"?wsdl");
			client.request(operationName, args);
			return;

		} catch (WSDLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidOperationNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
