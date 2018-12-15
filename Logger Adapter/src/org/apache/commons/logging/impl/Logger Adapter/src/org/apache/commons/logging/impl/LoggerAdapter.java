package org.apache.commons.logging.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.logger.Logger;
import org.apache.commons.logging.logger.Logger.*;

public class LoggerAdapter implements Log {
	
	// -------------------------------------- Attributs

	private Logger logger = null ;
	
	//--------------------------------------- Constructeur
	
	public LoggerAdapter(String nom) {
		this.logger = Logger.getInstance(nom);
	}
	
	//--------------------------------------- Implementation des méthodes de "Log.class"
	
	@Override
	public void debug(Object message) {
		this.logger.log(Level.DEBUG,(String) message);
	}

	@Override
	public void debug(Object message, Throwable t) {
		this.logger.log(Level.DEBUG,(String) message+" <"+t.getMessage()+">");
	}

	@Override
	public void error(Object message) {
		this.logger.log(Level.ERROR,(String) message);
	}

	@Override
	public void error(Object message, Throwable t) {
		this.logger.log(Level.ERROR,(String) message+" <"+t.getMessage()+">");
	}

	@Override
	public void fatal(Object message) {
		this.logger.log(Level.FATAL, (String) message);
	}

	@Override
	public void fatal(Object message, Throwable t) {
		this.logger.log(Level.FATAL,(String) message+" <"+t.getMessage()+">");
	}

	@Override
	public void info(Object message) {
		this.logger.log(Level.INFO,(String) message);
	}

	@Override
	public void info(Object message, Throwable t) {
		this.logger.log(Level.INFO,(String) message+" <"+t.getMessage()+">");
	}

	@Override
	public boolean isDebugEnabled() {
		return this.logger.getLevel() <= 2;
	}

	@Override
	public boolean isErrorEnabled() {
		return this.logger.getLevel() <= 5;
	}

	@Override
	public boolean isFatalEnabled() {
		return this.logger.getLevel() <= 6;
	}

	@Override
	public boolean isInfoEnabled() {
		return this.logger.getLevel() <= 3;
	}

	@Override
	public boolean isTraceEnabled() {
		return this.logger.getLevel() <= 1;
	}

	@Override
	public boolean isWarnEnabled() {
		return this.logger.getLevel() <= 4;
	}

	@Override
	public void trace(Object message) {
		this.logger.log(Level.TRACE,(String) message);
	}

	@Override
	public void trace(Object message, Throwable t) {
		this.logger.log(Level.TRACE,(String) message+" <"+t.getMessage()+">");
		
	}

	@Override
	public void warn(Object message) {
		this.logger.log(Level.WARN, (String) message);
	}

	@Override
	public void warn(Object message, Throwable t) {
		this.logger.log(Level.WARN,(String) message+" <"+t.getMessage()+">");
	}

}
