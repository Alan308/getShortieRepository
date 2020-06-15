package com.alanco.url.dbutilitytests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alanco.url.dbutility.DBConnector;
import com.alanco.url.dbutility.URLRepository;
import com.datastax.driver.core.Session;

/**Test class which will test the following operations on the Cassandra
 * Database. 
 * - Delete Existing Schema if exists
 * - Create new Keyspace
 * - Create new url table
 * - Create new urlcounter table
 * - Display schema members*/
public class DBSchemaBuild {

	private static final Logger LOG = LoggerFactory.getLogger(ShortCodeGenerationTest.class);
	private static String keySpace = "getShortie";

	public static void main(String[] args) throws Exception {
		
		LOG.info("Attempting DB connection.");
		DBConnector conn = new DBConnector();
		
		try {

			@SuppressWarnings("resource")
			Session sn = conn.getSession(keySpace);
			LOG.info("Session created");
			
			URLRepository ur = new URLRepository(sn);

			// Delete tables and keyspace if they already exist.
			LOG.info("Attempting to drop keyspace and tables.");
			ur.deleteUrlTable();
			ur.deleteCounterTable();
			ur.deleteKeyspace();
			
			// Create the database schema.
			LOG.info("Attempting to create the database schema.");
			ur.createSchema();
			conn.urlSchema();

		}
		catch (Exception e){
			
			LOG.error("The last statement did not run!", e);
		}
		finally {
			System.out.println("Closing the connection.");
			conn.close();
			LOG.info("Please delete your table after verifying the presence of the data in portal or from CQL");
		}
	}
}