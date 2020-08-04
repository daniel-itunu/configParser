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
 * 		i. readData()
 * 		ii. get(String key)
 */
import java.io.*;
import java.util.*;


public class Main {
	public static String[] arguments; //String array of environments(production,staging or development)

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub=
		arguments = args;
		//based on environment call configParser
		ConfigParser config = new ConfigParser();
		System.out.println(config.get("dbname"));
		System.out.println(config.get("application.name"));
		System.out.println(config.get("host"));
		System.out.println(config.get("mode"));
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
	public void readData() throws IOException {
		File file = new File("./"+getFilename());
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file)); //using most enhanced character-based reader, BufferReader
		String line = bufferedReader.readLine(); //individual line
		List<String> lines = new ArrayList<>(); //list of lines
		while (line!= null){
			lines.add(line);
			line = bufferedReader.readLine();
		}
		for(String value: lines){
			if(value.contains("=")){
				String[] keyValue = value.split("=");
				if(keyValue[0].equals("name")){
					map.putIfAbsent("application."+keyValue[0], keyValue[1]);
				}
				map.putIfAbsent(keyValue[0],keyValue[1]);
			}
		}
	}

	/**
	 * Gets the value of database name and application name based on key passed.
	 * @throws IOException if problem with reading file
	 * @return value based on key passed, if key not found, this returns "no such key found"
	 */
	public String get(String key) throws IOException {
		readData();
		return map.get(key);
	}
}
