package edu.uga.miage.m1;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public class Main {
	

	public static void main(String[] args) {
		
		Log myLog = LogFactory.getLog(Main.class);
		
		Date t0 = new Date();
		myLog.info("The program is started at " + t0);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			myLog.error("Program interrupted", e);
		}
		long t1 = System.currentTimeMillis() - t0.getTime();
		myLog.info("The program is ended after " + t1 + " ms");
	}

}
