import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigParserTest {

    @org.junit.jupiter.api.Test
    void getFilename() {
        ConfigParser configParser = new ConfigParser("config.txt");
            assertEquals("config.txt", configParser.getFilename());
    }

    @org.junit.jupiter.api.Test
    void setFilename() {
    }

    @org.junit.jupiter.api.Test
    void readData() {

    }

    @org.junit.jupiter.api.Test
    void get() throws IOException {
        ConfigParser configParser = new ConfigParser("config.txt");
        assertEquals("sq04_db",configParser.get("dbname"));
        assertEquals("fintek",configParser.get("application.name"));
    }
}