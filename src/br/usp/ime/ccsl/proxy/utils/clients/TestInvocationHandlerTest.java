package br.usp.ime.ccsl.proxy.utils.clients;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Test;

import eu.choreos.vv.Item;

public class TestInvocationHandlerTest {

	@Test
	public void testInvoke() throws Exception {
		Item response = 
			SyncInvocationHandler.invoke(new URL ("http://localhost:9010/hello?wsdl"), "greet", new String[] {"Felps"}) ;
		assertEquals("greetResponse", response.getName());
		//assertEquals(1, response.getChildrenCount());
		assertEquals("Hello Felps! Nice to meet you", response.getChild("return").getContent());
	}

}
