package com.alanco.url.dbutility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//Reads the configurations from the application.properties file.

public class DBConfigs {
	
	private static final Logger LOG = LoggerFactory.getLogger(DBConfigs.class);
    private static String FILE = "application.properties";
    private static Properties props = null;
    
    private void readProperties() throws IOException {
    	
    	InputStream iS = getClass().getClassLoader().getResourceAsStream(FILE);
    	if(iS == null) {
    		
    		LOG.error("{} was not found!", FILE);
    		return;
    	}
    	props = new Properties();
    	props.load(iS);
    }
    
    public String getProperty(String propertyName) throws IOException {
    	
    	if(props == null) {
    		readProperties();
    	}
    	return props.getProperty(propertyName);
    }
}
