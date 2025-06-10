package com.example.hellofx;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConfig {
    private static final String PROPERTIES_FILE = "C:\\Users\\gianl\\Desktop\\Università\\HelloFX\\src\\main\\java\\com\\example\\hellofx\\db.properties";

    private DbConfig() {}

    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(DbConfig.class.getName());
            logger.log(Level.SEVERE, "Errore durante la configurazione del DB", e);
        }
        return properties;
    }

}