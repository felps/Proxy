package br.usp.ime.ccsl.proxy.utils.logs;

import org.apache.log4j.Logger;


public class Logger4j {

	public static Logger logger = Logger.getLogger(Logger4j.class) ;
	
	public static void log(String msg){
		logger.info(msg);
	}
	
}
