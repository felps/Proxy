package br.usp.ime.ccsl.proxy.utils.clients;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.xmlbeans.XmlException;

import eu.choreos.vv.Item;
import eu.choreos.vv.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;
import eu.choreos.*;

import br.usp.ime.ccsl.proxy.utils.clients.DynamicInvoker;

public class InvocationHandler {

	public static HashMap invoke(URL serviceLocation, String operationName, String[] args) {
		WSClient client;
		Item response;
		
		try {
			client = new WSClient(serviceLocation.toExternalForm());
			response = client.request(operationName, args);
			iterateOverChildrenAndPrint(response);
			
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
		
		return null;
	}

	public static void voidInvoke(URL serviceLocation, String operationName, String[] args){
		invoke(serviceLocation, operationName, args);
	}
	private static void iterateOverChildrenAndPrint(Item root) {
		if(root.getChildrenCount()!=0){
				try {
					for (Item item : root.getChildAsList(root.getName())) {
						iterateOverChildrenAndPrint(item);
					}
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				System.out.println(root.getName());
		}
	}
}
