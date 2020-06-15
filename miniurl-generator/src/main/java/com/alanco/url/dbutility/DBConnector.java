package com.alanco.url.dbutility;

import java.util.Iterator;
import com.datastax.driver.core.*;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.TableMetadata;

//Connects to Cassandra database.
public class DBConnector {

		private Cluster cluster;
		private DBConfigs conf = new DBConfigs();
		private String connPoint = "127.0.0.1";
		private int port = 9042;
		
		public Session getSession() {
			
			try {

				this.cluster = Cluster.builder()
	                    .addContactPoint(this.connPoint)
	                    .withPort(this.port)
	                    .build();
				
				return this.cluster.connect();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return null;
		}
		
		//Can be used to speed up connection when keyspace already exists.
		public Session getSession(String keyspace) {
			
			try {
				
				this.cluster = Cluster.builder()
	                    .addContactPoint(connPoint)
	                    .withPort(port)
	                    .build();
				
				return this.cluster.connect(keyspace);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return null;
		}
		
		public Cluster getCluster() {
	        return this.cluster;
	    }

	    public void close() {
	        this.cluster.close();
	    }
	    
	    //Reads database connection details from application.properties.
		private void readConnectDetails() throws Exception {
			
			this.connPoint = this.conf.getProperty("Cassandra.Host");
			this.port = Integer.parseInt(this.conf.getProperty("Cassandra.Port"));
		}

		//Gets a list of tables and their columns in the keyspace.
		public Metadata urlSchema() {
			Metadata metadata = this.cluster.getMetadata();
			Iterator<TableMetadata> tm = metadata.getKeyspace("getShortie").getTables().iterator();
			while(tm.hasNext()){
				
			    TableMetadata t = tm.next();
			    String tableName = t.getName();
			    System.out.println("Table: '" + tableName + "' exists in the getShortie keyspace.");
			    Iterator<ColumnMetadata> cm = metadata.getKeyspace("getShortie").getTable(tableName).getColumns().iterator();
			    
			    while(cm.hasNext()){
			    	
			    	ColumnMetadata c = cm.next();
			    	String columnName = c.getName();
			    	System.out.println("The table " + tableName + " includes column: " + columnName + ".");
			    }	
			}
			return null;
		}	    
}