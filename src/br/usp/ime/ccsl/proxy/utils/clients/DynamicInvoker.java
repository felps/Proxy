/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2002 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:  
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "WSIF" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 2001, 2002, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package br.usp.ime.ccsl.proxy.utils.clients;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.wsdl.Definition;
import javax.wsdl.Input;
import javax.wsdl.Operation;
import javax.wsdl.Output;
import javax.wsdl.Part;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.xml.namespace.QName;

import org.apache.wsif.WSIFException;
import org.apache.wsif.WSIFMessage;
import org.apache.wsif.WSIFOperation;
import org.apache.wsif.WSIFPort;
import org.apache.wsif.WSIFService;
import org.apache.wsif.WSIFServiceFactory;
import org.apache.wsif.providers.soap.apacheaxis.WSIFDynamicProvider_ApacheAxis;
import org.apache.wsif.util.WSIFPluggableProviders;
import org.apache.wsif.util.WSIFUtils;

/**
 * This sample shows how to use WSIF for completely dynamic invocations
 * as it is completely stubless execution. This sample does not support
 * complex types (it could if there was defined a to encode complex
 * values as command line arguments).
 *
 * @author Sanjiva Weerawarana
 * @author Alekander Slominski
 */

public class DynamicInvoker {
    private static void usage() {
        System.err.println(
            "Usage: java "
                + DynamicInvoker.class.getName()
                + " wsdlLocation "
                + "operationName[(portName)]:[inputMessageName]:[outputMessageName] "
                + "[soap|axis] [argument1 ...]");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2)
            usage();

        String wsdlLocation = args.length > 0 ? args[0] : null;
        String operationKey = args.length > 1 ? args[1] : null;
        String portName = null;
        String operationName = null;
        String inputName = null;
        String outputName = null;

        StringTokenizer st = new StringTokenizer(operationKey, ":");
        int tokens = st.countTokens();
        int specType = 0;
        if (tokens == 2) {
            specType = operationKey.endsWith(":") ? 1 : 2;
        } else if (tokens != 1 && tokens != 3)
            usage();

        while (st.hasMoreTokens()) {
            if (operationName == null)
                operationName = st.nextToken();
            else if (inputName == null && specType != 2)
                inputName = st.nextToken();
            else if (outputName == null)
                outputName = st.nextToken();
            else
                break;
        }

        try {
            portName =
                operationName.substring(operationName.indexOf("(")+1, operationName.indexOf(")"));
            operationName = operationName.substring(0, operationName.indexOf("("));
        } catch (Exception ignored) {
        }

        String protocol = args.length > 2 ? args[2] : "";
        int shift = 2;
        if (protocol.equals("soap") || protocol.equals("axis"))
            shift = 3;

        HashMap map =
            invokeMethod(
                wsdlLocation,
                operationName,
                inputName,
                outputName,
                portName,
                protocol,
                args,
                shift);

        // print result
        System.out.println("Result:");
        for (Iterator it = map.keySet().iterator(); it.hasNext();) {
            String name = (String) it.next();
            System.out.println(name + "=" + map.get(name));
        }
        System.out.println("\nDone!");

    }

    public static HashMap invokeMethod(
        String wsdlLocation,
        String operationName,
        String inputName,
        String outputName,
        String portName,
        String protocol,
        String[] args,
        int argShift)
        throws Exception {

        String serviceNS = null;
        String serviceName = null;
        String portTypeNS = null;
        String portTypeName = null;

        // The default SOAP provider is the Apache SOAP provider. If Axis was specified
        // then change that
        if ("axis".equals(protocol)) {
            WSIFPluggableProviders.overrideDefaultProvider(
                "http://schemas.xmlsoap.org/wsdl/soap/",
                new WSIFDynamicProvider_ApacheAxis());
        }

        System.out.println("Reading WSDL document from '" + wsdlLocation + "'");
        Definition def = WSIFUtils.readWSDL(null, wsdlLocation);

        System.out.println("Preparing WSIF dynamic invocation");

        Service service = WSIFUtils.selectService(def, serviceNS, serviceName);

        Map portTypes = WSIFUtils.getAllItems(def, "PortType");
        // Really there should be a way to specify the portType
        // for now just try to find one with the portName 
        if (portTypes.size() > 1 && portName != null) {
        	for (Iterator i=portTypes.keySet().iterator(); i.hasNext(); ) {
        		QName qn = (QName) i.next();
        		if (portName.equals(qn.getLocalPart())) {
                  portTypeName = qn.getLocalPart();
                  portTypeNS = qn.getNamespaceURI();
                  break;
               }
            }
        }
        PortType portType = WSIFUtils.selectPortType(def, portTypeNS, portTypeName);

        WSIFServiceFactory factory = WSIFServiceFactory.newInstance();
        WSIFService dpf = factory.getService(def, service, portType);
        WSIFPort port = null;
        if (portName == null)
            port = dpf.getPort();
        else
            port = dpf.getPort(portName);

        if (inputName == null && outputName == null) {
            // retrieve list of operations
            List operationList = portType.getOperations();

            // try to find input and output names for the operation specified
            boolean found = false;
            for (Iterator i = operationList.iterator(); i.hasNext();) {
                Operation op = (Operation) i.next();
                String name = op.getName();
                if (!name.equals(operationName)) {
                    continue;
                }
                if (found) {
                    throw new RuntimeException(
                        "Operation '"
                            + operationName
                            + "' is overloaded. "
                            + "Please specify the operation in the form "
                            + "'operationName:inputMessageName:outputMesssageName' to distinguish it");
                }
                found = true;
                Input opInput = op.getInput();
                inputName = (opInput.getName() == null) ? null : opInput.getName();
                Output opOutput = op.getOutput();
                outputName = (opOutput.getName() == null) ? null : opOutput.getName();
            }
        }

        WSIFOperation operation =
            port.createOperation(operationName, inputName, outputName);
        WSIFMessage input = operation.createInputMessage();
        WSIFMessage output = operation.createOutputMessage();
        WSIFMessage fault = operation.createFaultMessage();

        // retrieve list of names and types for input and names for output
        List operationList = portType.getOperations();

        // find portType operation to prepare in/oout message w/ parts
        boolean found = false;
        String[] outNames = new String[0];
        Class[] outTypes = new Class[0];
        for (Iterator i = operationList.iterator(); i.hasNext();) {
            Operation op = (Operation) i.next();
            String name = op.getName();
            if (!name.equals(operationName)) {
                continue;
            }
            if (found) {
                throw new RuntimeException("overloaded operations are not supported in this sample");
            }
            found = true;

            //System.err.println("op = "+op);
            Input opInput = op.getInput();

            // first determine list of arguments
            String[] inNames = new String[0];
            Class[] inTypes = new Class[0];
            if (opInput != null) {
                List parts = opInput.getMessage().getOrderedParts(null);
                unWrapIfWrappedDocLit(parts, name, def);
                int count = parts.size();
                inNames = new String[count];
                inTypes = new Class[count];
                retrieveSignature(parts, inNames, inTypes);
            }
            // now prepare out parameters

            for (int pos = 0; pos < inNames.length; ++pos) {
                String arg = args[pos + argShift];
                Object value = null;
                Class c = inTypes[pos];
                if (c.equals(String.class)) {
                    value = arg;
                } else if (c.equals(Double.TYPE)) {
                    value = new Double(arg);
                } else if (c.equals(Float.TYPE)) {
                    value = new Float(arg);
                } else if (c.equals(Integer.TYPE)) {
                    value = new Integer(arg);
                } else if (c.equals(Boolean.TYPE)) {
                    value = new Boolean(arg);
                } else {
                    throw new RuntimeException("not know how to convert '" + arg + "' into " + c);
                }

                input.setObjectPart(inNames[pos], value);
            }

            Output opOutput = op.getOutput();
            if (opOutput != null) {
                List parts = opOutput.getMessage().getOrderedParts(null);
                unWrapIfWrappedDocLit(parts, name+"Response", def);
                int count = parts.size();
                outNames = new String[count];
                outTypes = new Class[count];
                retrieveSignature(parts, outNames, outTypes);
            }

        }
        if (!found) {
            throw new RuntimeException(
                "no operation "
                    + operationName
                    + " was found in port type "
                    + portType.getQName());
        }

        System.out.println("Executing operation " + operationName);
        operation.executeRequestResponseOperation(input, output, fault);

        HashMap map = new HashMap();
        for (int pos = 0; pos < outNames.length; ++pos) {
            String name = outNames[pos];
            map.put(name, output.getObjectPart(name));
        }

        return map;
    }

    private static void retrieveSignature(
        List parts,
        String[] names,
        Class[] types) {
        // get parts in correct order
        for (int i = 0; i < names.length; ++i) {
            Part part = (Part) parts.get(i);
            names[i] = part.getName();
            QName partType = part.getTypeName();
            if (partType == null) {
               partType = part.getElementName();
            }
            if (partType == null) {
                throw new RuntimeException(
                    "part " + names[i] + " must have type name declared");
            }
            // only limited number of types is supported
            // cheerfully ignoring schema namespace ...
            String s = partType.getLocalPart();
            if ("string".equals(s)) {
                types[i] = String.class;
            } else if ("double".equals(s)) {
                types[i] = Integer.TYPE;
            } else if ("float".equals(s)) {
                types[i] = Float.TYPE;
            } else if ("int".equals(s)) {
                types[i] = Integer.TYPE;
            } else if ("boolean".equals(s)) {
                types[i] = Boolean.TYPE;
            } else {
                throw new RuntimeException(
                    "part type " + partType + " not supported in this sample");
            }
        }
    }

	/**
	 * Unwraps the top level part if this a wrapped DocLit message.
	 */
	private static void unWrapIfWrappedDocLit(List parts, String operationName, Definition def) throws WSIFException {
		   Part p = WSIFUtils.getWrappedDocLiteralPart(parts, operationName);
		   if (p != null) {
			  List unWrappedParts = WSIFUtils.unWrapPart(p, def);
			  parts.remove(p);
			  parts.addAll(unWrappedParts);
		   }
	}

}
