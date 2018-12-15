package org.apache.commons.logging.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;

/**
 *  Class Logger
 * @author M'ed Said Lakhyari
 *
 */
public class Logger implements Log {

	// Enum pour les niveaux 
	public enum Level {

		DEBUG("DEBUG", 1), INFO("INFO", 2), WARN("WARN", 3), ERROR("ERROR", 4);

		private Level(String message, int level) {
		}
	}
	// Enum pour le mode d'affichae
	public enum Mode {
		CONSOLE, FILE, ALL
	}

	private final String configPath = "src/logger.properties";
	private Properties properties;
	private static Logger logger = null;
	private int niveaux;; // par defaut = 1 (Niveau DEBUG)
	private String logFilePath; // Chemin vers le fichier du loggeur
	private BufferedWriter file; // fichier loggeur
	private String loggerMode;
	
	 /** The name of this simple log instance */
    protected volatile String logName = null;

	/**
	 * Constructor
	 */
	public Logger(String name ) {
		this.logName = name ; 
		this.initConfig();
	}

	private void initConfig() {
		this.properties = new Properties();
		try {
			File file = new File(configPath);
			if(!file.exists()) {
				file.createNewFile();
			}
			FileInputStream in = new FileInputStream(file);
			properties.load(in);
			in.close();
			this.niveaux = Integer.valueOf(this.properties.getProperty("loggerLevel", "1"));
			this.logFilePath = this.properties.getProperty("loggerPathFile", "src/logFile");
			this.loggerMode = this.properties.getProperty("loggerMode", "ALL");
		} catch (IOException e) {
			System.out.println("Unable to load config file.");
		}
	}

	/**
	 * (Pseudo constructeur) Get Logger object
	 * @return Logger
	 */
	public static synchronized Logger getInstance(String name) {
		if (logger == null) {
			logger = new Logger(name);
			logger.initConfig();
			return logger;
		}
		return logger;
	}

	/**
	 * Specify severity level for the logger (By default = 1)
	 * @param i Severity Level (1 = DEBUG , 2 = INFO , 3 = WARN , 4 = ERROR)
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
	 * @return level
	 */
	public int getLevel() {
		return Integer.valueOf(this.properties.getProperty("loggerLevel"));
	}

	/**
	 * Set the mode of log display
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
	 * @return
	 */
	public String getMode() {
		return this.properties.getProperty("loggerMode");
	}

	/**
	 * Show log message on the console
	 * @param lvl     Severity level (DEBUG,INFO,WARN,ERROR)
	 * @param message Log message
	 * @throws IOException
	 */
	public void log(Level lvl, String message) {

		String msg;
		// Recuperer la Date du Jour
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");
		Date date = new Date();

		// Recupere les informations du Thread en cours d'execution (nomClass , nom
		// Methode , num de ligne )
		String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

		switch (lvl) {

		case DEBUG:

			if (niveaux <= 1) {
				msg = "[" + sdf.format(date) + "] [DEBUG] (" + className + ".java:" + methodName + ":" + lineNumber
						+ ") " + message;
				if (this.loggerMode.equals("CONSOLE") || this.loggerMode.equals("ALL"))
					System.out.println(msg);

				if (this.loggerMode.equals("FILE") || this.loggerMode.equals("ALL")) {
					if (logFilePath != null)
						try {
							file = new BufferedWriter(new FileWriter(logFilePath, true));
							file.newLine();
							file.write(msg);
							file.close();

						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}
			break;

		case INFO:

			if (niveaux <= 2) {
				msg = "[" + sdf.format(date) + "] [INFO] (" + className + ".java:" + methodName + ":" + lineNumber
						+ ") " + message;
				if (this.loggerMode.equals("CONSOLE") || this.loggerMode.equals("ALL"))
					System.out.println(msg);

				if (this.loggerMode.equals("FILE") || this.loggerMode.equals("ALL")) {
					if (logFilePath != null)
						try {
							file = new BufferedWriter(new FileWriter(logFilePath, true));
							file.newLine();
							file.write(msg);
							file.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}
			break;

		case WARN:

			if (niveaux <= 3) {
				msg = "[" + sdf.format(date) + "] [WARN] (" + className + ".java:" + methodName + ":" + lineNumber
						+ ") " + message;
				if (this.loggerMode.equals("CONSOLE") || this.loggerMode.equals("ALL"))
					System.out.println(msg);

				if (this.loggerMode.equals("FILE") || this.loggerMode.equals("ALL")) {

					if (logFilePath != null)
						try {
							file = new BufferedWriter(new FileWriter(logFilePath, true));
							file.newLine();
							file.write(msg);
							file.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}
			break;

		case ERROR:

			if (niveaux <= 4) {
				msg = "[" + sdf.format(date) + "] [ERROR] (" + className + ".java:" + methodName + ":" + lineNumber
						+ ") " + message;
				if (this.loggerMode.equals("CONSOLE") || this.loggerMode.equals("ALL"))
					System.err.println(msg);

				if (this.loggerMode.equals("FILE") || this.loggerMode.equals("ALL")) {
					if (logFilePath != null)
						try {
							file = new BufferedWriter(new FileWriter(logFilePath, true));
							file.newLine();
							file.write(msg);
							file.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}
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
	 * @param e Exception
	 * @throws IOException
	 */
	public void logException(Exception exc) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS");
		Date date = new Date();
		String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

		String msg = "[" + sdf.format(date) + "] [ERROR] (" + className + ".java:" + methodName + ":" + lineNumber
				+ ") " + exc.getMessage();

		if (this.loggerMode.equals("CONSOLE") || this.loggerMode.equals("ALL"))
			System.err.println(msg);

		if (this.loggerMode.equals("FILE") || this.loggerMode.equals("ALL")) {
			if (logFilePath != null)
				try {
					file = new BufferedWriter(new FileWriter(logFilePath, true));
					file.newLine();
					file.write(msg);
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * Set the path of the Logger File
	 * @param filePath path to the LoggerFile
	 */
	public void setFileLogger(String filePath) {

		if (filePath == null || filePath.equals("")) {
			logFilePath = "loggerFile.txt";
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
	 * @return filepath
	 */
	public String getLogFilePath() {
		return this.logFilePath;
	}

	@Override
	public void debug(Object message) {
		this.log(Level.DEBUG, (String) message);
	}

	@Override
	public void debug(Object message, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(Object message) {
		// TODO Auto-generated method stub
		this.log(Level.ERROR, (String) message);
	}

	@Override
	public void error(Object message, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fatal(Object message) {
		// TODO Auto-generated method stub
		this.log(Level.ERROR, (String) message);

		
	}

	@Override
	public void fatal(Object message, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(Object message) {
		// TODO Auto-generated method stub
		this.log(Level.INFO, (String) message);
	}

	@Override
	public void info(Object message, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDebugEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isErrorEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFatalEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInfoEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTraceEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWarnEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void trace(Object message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trace(Object message, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(Object message) {
		// TODO Auto-generated method stub
		this.log(Level.WARN, (String) message);		
	}

	@Override
	public void warn(Object message, Throwable t) {
		// TODO Auto-generated method stub
		
	}
}
