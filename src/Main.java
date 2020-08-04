/**
 * @author Daniel Itunu
 * This software is a configuration file parser used for reading certain
 * values using a key from a file based on environment passed from commandline.
 * This software has the following classes:
 * 1. Class Main
 * 	Class Main has the following methods:
 * 		i. main mathod
 * 		ii. isProduction()
 * 		iii. isStaging()
 * 		iv. isDevelopment()
 * 2. InnerClass ConfigParser
 * 	Inner Class ConfigParser has the following methods:
 * 		i. readDatabaseName()
 * 		ii. readApplicationName()
 * 		iii. get(String key)
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static String[] arguments; //String array of environments(production,staging or development)

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub=
		arguments = args;
		//based on environment call configParser
		if (isProduction()) {
			ConfigParser config = new ConfigParser();
			System.out.println(config.get("dbname"));
			System.out.println(config.get("application.name"));
		}
		if (isStaging()) {
			ConfigParser config = new ConfigParser();
			System.out.println(config.get("dbname"));
			System.out.println(config.get("application.name"));
		}
		if (isDevelopment()) {
			ConfigParser config = new ConfigParser();
			System.out.println(config.get("dbname"));
			System.out.println(config.get("application.name"));
		}
	}

	/**
	 * production environment is achieved by passing no argument from commandline.
	 * @return true if production environment
	 */
	public static boolean isProduction() {
		return arguments.length == 0;
	}

	/**
	 * @return true if "staging" passed from commandline
	 */
	public static boolean isStaging() {
		return arguments.length == 1 && arguments[0].equals("staging");
	}

	/**
	 * @return true if "development" passed from commandline
	 */
	public static boolean isDevelopment() {
		return arguments.length == 1 && arguments[0].equals("development");
	}
}


/**
 * Inner class ConfigParser.
 * Responsible for reading data from file based on environment passed from args in main.
 */
class ConfigParser {
	private String filename; //name of config file
	private final Map<String, String> map = new HashMap<>();

	/**
	 * parameter constructor
	 * @param filename: name of file to read data from
	 */
	public ConfigParser(String filename) {
		this.filename = filename;
	}

	/**
	 * Default and empty constructor. It sets default filenames when called based on environment from main.
	 */
	public ConfigParser() {
		if (Main.isProduction()) {
			setFilename("config.txt");
		}
		if (Main.isStaging()) {
			setFilename("config.txt.staging");
		}
		if (Main.isDevelopment()) {
			setFilename("config.txt.dev");
		}
	}

	/**
	 * Gets name of file.
	 * @return filename: name of file to read from
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Sets name of file.
	 * @param filename: set name of file to read from
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}


	/**
	 * Reads the config file for database name.
	 * @throws IOException if problem with reading file
	 */
	public void readDatabaseName() throws IOException {
		Pattern pattern = Pattern.compile("dbname=\\w+"); //create pattern to match database name
		BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/user/Downloads/Task-Two-master/src/" + getFilename())); //using most enhanced character-based reader, BufferReader
		String line = bufferedReader.readLine();
		ArrayList<String> dbnameLines = new ArrayList(); //store every line that "dbname=" appears into an arraylist in order to obtain first dbname occurrence
		String dbnameLine; //a single line where "dbname=" appears
		String[] lineArray; //obtain string array after spliting any line "dbname=" appears in
		String databaseName; //database name result
		while (line != null) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				dbnameLine = matcher.group();
				dbnameLines.add(dbnameLine);
			}
			line = bufferedReader.readLine();
		}
		lineArray = dbnameLines.get(0).split("=");
		databaseName = lineArray[1];
		bufferedReader.close();
		map.put("dbname", databaseName);
	}

	/**
	 * Reads the config file for application name.
	 * @throws IOException if problem with reading file
	 * @return application name
	 */
	public void readApplicationName() throws IOException {
		Pattern pattern = Pattern.compile("^d?^b?name=\\w+"); //create pattern to match application name
		BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/user/Downloads/Task-Two-master/src/" + getFilename()));
		String line= bufferedReader.readLine();
		ArrayList<String> appNameLines = new ArrayList(); //store every line that application name appears into an arraylist in order to obtain first dbname occurrence
		String appNameLine; //a single line where application name appears
		String[] lineArray; //obtain string array after spliting any line application name appears in
		String applicationName; //application name result
		while (line != null) {
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()){
				appNameLine = matcher.group();
				appNameLines.add(appNameLine);
			}
			line = bufferedReader.readLine();
		}
		lineArray = appNameLines.get(0).split("=");
		applicationName = lineArray[1];
		bufferedReader.close();
		map.put("application.name", applicationName);
	}

	/**
	 * Gets the value of database name and application name based on key passed.
	 * @throws IOException if problem with reading file
	 * @return value based on key passed, if key not found, this returns "no such key found"
	 */
	public String get(String key) throws IOException {
		readDatabaseName();
		readApplicationName();
		if(key.equals("dbname")){
			return map.get(key);
		}
		if(key.equals("application.name")){
			return map.get(key);
		}
		return "no such key found";
	}
}
