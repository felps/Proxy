package br.usp.ime.ccsl.proxy.utils.clients;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class TestDynamicInvocation {

	@Test
	public void testInvoke() throws Exception {
		String[] args = new String[]{"http://localhost:9010/hello?wsdl", "greet", "IBM"};
		DynamicInvoker.main(args);
	}

	@Test
	public void testInvokeDirectly() throws Exception {
		HashMap map =
            DynamicInvoker.invokeMethod(
            	"http://localhost:9010/hello?wsdl",
                "greet",
                null,
                null,
                null,
                "soap",
                new String[]{"Felps"},
                3);
		System.out.println(">>"+map+"<<");
	}
}
