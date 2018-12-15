package org.apache.commons.logging.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Class Logger
 * 
 * @author LAKHYARI
 *
 */
public class Logger {

	// Enum pour les niveaux
	public enum Level {

		TRACE("TRACE", 1) , DEBUG("DEBUG", 2), INFO("INFO", 3), 
		WARN("WARN", 4), ERROR("ERROR", 5) , FATAL("FATAL", 6);

		private Level(String message, int level) {
		}
	}

	// Enum pour le mode d'affichage
	public enum Mode {
		CONSOLE, FILE, ALL
	}
	
	// -------------------------------------------------------------- Attributs
	final String configPath = "logger.properties";
	private Properties properties;
	private static Logger logger = null;
	private int niveaux;; // par defaut = 1 (Niveau DEBUG)
	private String logFilePath; // Chemin vers le fichier du loggeur
	private BufferedWriter file; // fichier loggeur
	private String loggerMode;
	private String logName;

	// -------------------------------------------------------------- Constructeur
	/**
	 * Constructor
	 */
	private Logger(String className) {
		this.logName = className ;
		this.initConfig();
	}
	// -------------------------------------------------------------- Méthodes
	
	/**
	 * Initialize the configuration file
	 */
	private void initConfig() {
		this.properties = new Properties();
			
			File file = new File(configPath);
			// En cas si le fichier de configuration n'existe pas , on le cree + le remplir
			if(!file.exists()) {
				try {
					file.createNewFile();
					this.setFileLogger("logFile");
					this.setLevel(1);
					this.setMode(Mode.ALL);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			FileInputStream in;					
			try {
				in = new FileInputStream(configPath);
				properties.load(in);
				in.close();
				this.niveaux = Integer.valueOf(this.properties.getProperty("loggerLevel"));
				this.logFilePath = this.properties.getProperty("loggerFilePath");
				this.loggerMode = this.properties.getProperty("loggerMode");
			} catch (Exception e ) {
				e.printStackTrace();
			} 
	}

	/**
	 * (Pseudo constructeur) Get Logger object
	 * 
	 * @return Logger
	 */
	public static synchronized Logger getInstance(String className) {
		if (logger == null) {
			logger = new Logger(className);
			return logger;
		}
		return logger;
	}

	/**
	 * Specify severity level for the logger (By default = 1)
	 * 
	 * @param i Severity Level (1 = TRACE, 2 = DEBUG,3 = INFO ,
	 *    4 = WARN , 5 = ERROR, 6 = FATAL)
	 */

	public void setLevel(int i) {
		try {
			String level = Integer.toString(i);
			properties.setProperty("loggerLevel", level);
			FileOutputStream out;
			out = new FileOutputStream(configPath);
			properties.store(out, "---config---");
			out.close();
			this.niveaux = Integer.valueOf(this.properties.getProperty("loggerLevel", level));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the current level
	 * 
	 * @return level
	 */
	public int getLevel() {
		return Integer.valueOf(this.properties.getProperty("loggerLevel"));
	}

	/**
	 * Set the mode of log display
	 * 
	 * @param mode Display Mode (Mode.CONSOLE | Mode.FILE | Mode.ALL )
	 */
	public void setMode(Mode mode) {
		FileOutputStream out;
		try {
			properties.setProperty("loggerMode", mode.name().toUpperCase());
			out = new FileOutputStream(configPath);
			properties.store(out, "---config---");
			out.close();
			this.loggerMode = this.properties.getProperty("loggerMode", mode.name().toUpperCase());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the current Mode
	 * 
	 * @return
	 */
	public String getMode() {
		return this.properties.getProperty("loggerMode");
	}

	/**
	 * Show the log message into the Console (write in the file also )
	 * 
	 * @param lvl        Log level (TRACE|DEBUG|INFO|WARN|ERROR|FATAL)
	 * @param message    log message
	 * @param className  the name of the class who use the logger
	 */
	private void showLog(Level lvl, String message ) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");
		Date date = new Date();
		String msg;
		
		msg = "[" + lvl.name() + "] [" + sdf.format(date) + "] (" + this.logName + ") " + message;
		if (this.loggerMode.equals("CONSOLE") || this.loggerMode.equals("ALL")) {
			if (lvl.name().equals(Level.ERROR.name()) ||lvl.name().equals(Level.FATAL.name()) )
				System.err.println(msg);
			else
				System.out.println(msg);
		}
		if (this.loggerMode.equals("FILE") || this.loggerMode.equals("ALL"))
			this.writeInFile(msg);
	}

	/**
	 * Write a text in the logFile
	 * 
	 * @param text Message to write into the logFile
	 */
	private void writeInFile(String text) {
		if (logFilePath != null)
			try {
				file = new BufferedWriter(new FileWriter(logFilePath, true));
				file.newLine();
				file.write(text);
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Show log message on the console
	 * 
	 * @param lvl     Severity level (DEBUG,INFO,WARN,ERROR)
	 * @param message Log message
	 * @throws IOException
	 */
	public void log(Level lvl, String message) {

		// Recupere les informations du Thread en cours d'execution (nomClass,num de
		// ligne )
		String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);

		switch (lvl) {
		
		case TRACE :
			if(niveaux <= 1)
				this.showLog(lvl, message);
			break;
			
		case DEBUG:
			if (niveaux <= 2)
				this.showLog(lvl, message);
			break;

		case INFO:
			if (niveaux <= 3)
				this.showLog(lvl, message );
			break;

		case WARN:
			if (niveaux <= 4)
				this.showLog(lvl, message );
			break;

		case ERROR:
			if (niveaux <= 5)
				this.showLog(lvl, message );
			break;
		
		case FATAL:
			if (niveaux <= 6)
				this.showLog(lvl, message );
			break;

		default:
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		}
	}

	/**
	 * Exception Logger
	 * 
	 * @param e Exception
	 * @throws IOException
	 */
	public void logException(Exception exc) {
		String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		this.showLog(Level.ERROR, exc.getMessage());
	}

	/**
	 * Set the path of the Logger File
	 * 
	 * @param filePath path to the LoggerFile
	 */
	public void setFileLogger(String filePath) {
		if (filePath == null || filePath.equals("")) {
			logFilePath = "logFile";
			return;
		}

		try {
			properties.setProperty("loggerFilePath", filePath);
			FileOutputStream out;
			out = new FileOutputStream(configPath);
			properties.store(out, "---config---");
			out.close();
			this.logFilePath = this.properties.getProperty("loggerFilePath", "1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the Logger File path
	 * 
	 * @return filepath
	 */
	public String getLogFilePath() {
		return this.logFilePath;
	}
}
