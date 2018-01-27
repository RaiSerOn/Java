package ru.sfedu.shopproject.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration utility. Allows to get configuration properties from the
 * default configuration file
 */
public class ConfigurationUtil {

    private static Logger logger = Logger.getLogger(ConfigurationUtil.class);
    private static String DEFAULT_CONFIG_PATH = "C:\\Users\\Max\\Documents\\NetBeansProjects\\mavenproject1\\src\\main\\resources\\config.properties";
//    private static String DEFAULT_CONFIG_PATH = "src/main/resources/config.properties";
    private static final Properties configuration = new Properties();

    public static String getDefaultConfigPath() {
        return DEFAULT_CONFIG_PATH;
    }

    /**
     * Hides default constructor
     */
    private ConfigurationUtil() {
    }

    
    private static Properties getConfiguration() throws IOException {
        if(configuration.isEmpty()){
            loadConfiguration();
        }
        return configuration;
    }

    /**
     * Loads configuration from <code>DEFAULT_CONFIG_PATH</code>
     * @throws IOException In case of the configuration file read failure
     */
    private static void loadConfiguration() throws IOException{
        FileInputStream in = new FileInputStream(DEFAULT_CONFIG_PATH);
        try {
            configuration.load(in);
        } catch (IOException ex) {
            throw new IOException(ex);
        } finally{
            in.close();
        }
    }
    /**
     * Gets configuration entry value
     * @param key Entry key
     * @return Entry value by key
     * @throws IOException In case of the configuration file read failure
     */
    public static String getConfigurationEntry(String key) throws IOException{
        return getConfiguration().getProperty(key);
    }

    public static void setConfigPath (String config_path){
        DEFAULT_CONFIG_PATH = config_path;
    }

}
