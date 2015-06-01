/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.football;

/**
 *
 * @author pocket
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    public static Properties properties;
    public static boolean isTodMode = false;

    public static void loadConfig() {
        properties = new Properties();
        String dir = System.getProperty("user.dir");
        String file = "conf/configuration.conf";
        InputStream i;
        try {
            i = new FileInputStream(dir + "/" + file);
            properties.load(i);
            System.out.println(file + " config loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("Config file (" + file + ") not found program terminate.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Config file (" + file + ") not found program terminate : "
                    + e.getMessage());
            System.exit(1);
        }
    }

    public static String getString( String key ) {
        return properties.getProperty(key);
    }

    public static int getInt( String key ) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static double getDouble( String key ) {
        return Double.parseDouble(properties.getProperty(key));
    }

    public static float getFloat( String key ) {
        return Float.parseFloat(properties.getProperty(key));
    }
}

