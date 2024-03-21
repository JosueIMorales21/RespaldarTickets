package org.example;

import org.example.LogConfig.LogConfig;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.FindFiles.FindFiles.result;
import static org.example.LogConfig.LogConfig.loadConfig;

public class Main {

    private static final Logger logger = Logger.getLogger(LogConfig.class.getName());

    public static void main(String[] args) {

        loadConfig();
        try {
            result();
        } catch(IOException e) {
            logger.log(Level.SEVERE, "Error al ejecutar la aplicación: ", e);
        }
    }
}