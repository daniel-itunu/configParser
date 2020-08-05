/**
 * @author Daniel Itunu
 * This software is a configuration file parser used for reading certain
 * values using a key from a file based on environment passed from commandline.
 * This software has the following classes:
 * 1. Class Main
 * Class Main has the following methods:
 * i. main mathod
 * ii. isProduction()
 * iii. isStaging()
 * iv. isDevelopment()
 * 2. InnerClass ConfigParser
 * Inner Class ConfigParser has the following methods:
 * i. readData()
 * ii. get(String key)
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static String[] arguments; //String array of environments(production,staging or development)

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub=
        arguments = args;
        if (arguments.length == 1 && !(arguments[0].toLowerCase().equals("staging") ||
                arguments[0].toLowerCase().equals("development"))) {
            System.err.println("unrecognised environment, specify required environment");
        } else {
            //test code here
            ConfigParser config = new ConfigParser();
            System.out.println(config.get("dbname"));
            System.out.println(config.get("application.name"));
            System.out.println(config.get("application.host"));
            System.out.println(config.get("application.port"));
            System.out.println(config.get("application.context-url"));
            System.out.println(config.get("application.mode"));
            System.out.println(config.get("application.theme"));
            System.out.println(config.get("application.pipeline"));
        }
    }

    /**
     * production environment is achieved by passing no argument from commandline.
     *
     * @return true if production environment
     */
    public static boolean isProduction() {
        return arguments.length == 0;
    }

    /**
     * @return true if "staging" passed from commandline
     */
    public static boolean isStaging() {
        return arguments.length == 1 && arguments[0].toLowerCase().equals("staging");
    }

    /**
     * @return true if "development" passed from commandline
     */
    public static boolean isDevelopment() {
        return arguments.length == 1 && arguments[0].toLowerCase().equals("development");
    }
}

/**
 * Inner class ConfigParser.
 * Responsible for reading data from file based on environment passed from args in main.
 */
class ConfigParser {
    private final Map<String, String> map = new HashMap<>();
    private String filename; //name of config file

    /**
     * parameter constructor
     *
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
     *
     * @return filename: name of file to read from
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets name of file.
     *
     * @param filename: set name of file to read from
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }


    /**
     * Reads the config file for database name.
     *
     * @throws IOException if problem with reading file
     */
    public void readData() throws IOException {
        //get relative path
        File file = new File("./");
        String path = file.getAbsolutePath().replace(".", "");
        String filepath = "";
        if (path.endsWith("src/")) {
            filepath = path;
        } else {
            filepath = path + "/src/";
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath + getFilename())); //using most enhanced character-based reader, BufferReader
        String line = bufferedReader.readLine(); //individual line
        List<String> lines = new ArrayList<>(); //list of lines
        while (line != null) {
            lines.add(line);
            line = bufferedReader.readLine();
        }
        for (String value : lines) { //loop over list of lines
            if (value.contains("=")) {
                String[] keyValue = value.split("=");
                if (keyValue[0].equals("name")) { //append "application."
                    map.putIfAbsent("application." + keyValue[0], keyValue[1]);
                }
                if (keyValue[0].equals("host")) {
                    map.putIfAbsent("application." + keyValue[0], keyValue[1]);
                }
                if (keyValue[0].equals("port")) {
                    map.putIfAbsent("application." + keyValue[0], keyValue[1]);
                }
                if (keyValue[0].equals("context-url")) {
                    map.putIfAbsent("application." + keyValue[0], keyValue[1]);
                }
                if (keyValue[0].equals("mode")) {
                    map.putIfAbsent("application." + keyValue[0], keyValue[1]);
                }
                if (keyValue[0].equals("theme")) {
                    map.putIfAbsent("application." + keyValue[0], keyValue[1]);
                }
                if (keyValue[0].equals("pipeline")) {
                    map.putIfAbsent("application." + keyValue[0], keyValue[1]);
                }
                map.putIfAbsent(keyValue[0], keyValue[1]);
            }
        }
    }

    /**
     * Gets the value of database name and application name based on key passed.
     *
     * @return value based on key passed, if key not found, this returns "no such key found"
     * @throws IOException if problem with reading file
     */
    public String get(String key) throws IOException {
        readData();
        return map.get(key);
    }
}
