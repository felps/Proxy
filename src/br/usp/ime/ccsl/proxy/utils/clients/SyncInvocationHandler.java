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

public class SyncInvocationHandler {

	public static Item invoke(URL serviceLocation, String operationName, String[] args) {
		WSClient client;
		Item response;

		try {
			client = new WSClient(serviceLocation.toExternalForm()+"?wsdl");
			response = client.request(operationName, args);
			return response;

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

	public void printAllData(Item node) {

		System.out.println(node.getName());

		if (node.getChildrenCount()>0){
			for (Item child : node.getChildren()) {
				System.out.println(child.getName());
			}
		}

	}
	
	public static void iterateOverChildrenAndPrint(Item root) {
		
		System.out.println(root.getName() + ": " + root.getContent());

		if(root.getChildrenCount()!=0){
				for (Item item : root.getChildren()) {
					iterateOverChildrenAndPrint(item);
				}
		}
	}
}
