package br.usp.ime.ccsl.proxy.utils.clients;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class TestInvocationHandlerTest {

	@Test
	public void testInvoke() throws MalformedURLException {
		InvocationHandler.voidInvoke(new URL ("http://localhost:9010/hello?wsdl"), "greet", new String[] {"Felps"}) ;
	}

}
