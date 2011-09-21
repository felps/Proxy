package br.usp.ime.ccsl.proxy.utils.clients;
import java.net.URL;
import java.util.HashMap;

import br.usp.ime.ccsl.proxy.utils.clients.DynamicInvoker;

public class InvocationHandler {

	public static HashMap invoke(URL serviceLocation, String operationName, String[] args) {
		try {
			return DynamicInvoker.invokeMethod(serviceLocation.toExternalForm()+"?wsdl", operationName, null, null, null, "soap", args, 0);
		} catch (Exception e) {
			System.out.println("ERROR: Could not invoke method on service.");
			e.printStackTrace();
		}
		return null;

	}
}
