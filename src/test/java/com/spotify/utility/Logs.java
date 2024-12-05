package com.spotify.utility;

/**
 * @author Mankgethwa Matabane 
 * 
 *This is logs class
 */

import org.apache.log4j.Logger;

public class Logs {
	
	// Initialize Log4j logs
	public static Logger Log = Logger.getLogger(Logs.class.getName());

	public static void startTestCase(String name){		  
		 Log.info("====================================="+name+" TEST STARTED=========================================");
		 }
	
	public static void endTestCase(String name){
		Log.info("====================================="+name+" TEST ENDED=========================================");
		 }
	
	// methods to be called when perfoming an action or testg  

	 public static void info(String message) {

			Log.info(message);

			}

	 public static void warn(String message) {

	    Log.warn(message);

		}

	 public static void error(String message) {

	    Log.error(message);

		}

	 public static void fatal(String message) {

	    Log.fatal(message);

		}

	 public static void debug(String message) {

	    Log.debug(message);

		}
	 
	 
	
}

