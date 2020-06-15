package com.alanco.url.dbutility;

import java.util.List;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*Provides methods to Create and Drop required Keyspace and Tables.
Also provides methods to Insert Into and Select From Tables as required.*/
public class URLRepository {

	private static final Logger LOG = LoggerFactory.getLogger(URLRepository.class);
	private Session session;

	public URLRepository(Session session) {
		this.session = session;
	}

	//Create the getShortie keyspace, the url table and urlCounter table.
	public void createSchema() {

		LOG.info("Creating required database schema.");
		final String k = "CREATE KEYSPACE IF NOT EXISTS getShortie WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 1 }";
		final String u = "CREATE TABLE IF NOT EXISTS getShortie.url(id int PRIMARY KEY, longname text, createddate text, lastusedate text, "
						+ "shortcode text);";
		final String uC = "CREATE TABLE IF NOT EXISTS getShortie.urlcounter(id int PRIMARY KEY, usage counter);";
		
		this.session.execute(k);
		this.session.execute("USE getShortie");
		this.session.execute(u);
		this.session.execute(uC);
	}
	
	//Use the getShortie keyspace.
	public void useKeyspace() {
		
		this.session.execute("USE getShortie");
		LOG.info("Using getShortie keyspace.");
	}
	
	//Delete Keyspace.
	public void deleteKeyspace() {
        
		final String query = "DROP KEYSPACE getShortie;";
        this.session.execute(query);
        LOG.info("Keyspace: getShortie was deleted successfully.");
    }

	//Delete url table.
    public void deleteUrlTable() {
        final String query = "DROP TABLE IF EXISTS getShortie.url;";
        this.session.execute(query);
    }
    
    //Delete urlCounter table.
    public void deleteCounterTable() {
        final String query = "DROP TABLE IF EXISTS getShortie.urlcounter;";
        this.session.execute(query);
    }
    
    /**Insert a row into url table
	*@Param id id
	*@param name longname
	*@param cdate createddate
	*@param ldate lastusedate
	*@param code shortcode*/
    public void insertLongUrl(int id, String name, String cdate, String ldate, String code) {
    	
    	final String query = 
    			"INSERT INTO getShortie.url (id, longname, createddate, lastusedate, shortcode) VALUES (" +
    			id + ", '" + name + "', '" + cdate + "', '" + ldate + "', '" + code + "');";
    	this.session.execute(query);
    }
    
    /**Insert a row into urlCounter table
     * @param id id
     * @param name longname
     * @param code shortcode*/
    public void insertUrl(PreparedStatement statement, int id, String name, String createddate,
    		String lastusedate, String code) {
        BoundStatement boundStatement = new BoundStatement(statement);
        this.session.execute(boundStatement.bind(id, name, createddate, lastusedate, code));
    }
    /**Create a PrepareStatement to insert a row to url table.
     * @return PreparedStatement*/
    public PreparedStatement urlInsertPrepared() {
        final String insertStatement = "INSERT INTO getShortie.url (id, longname, createddate, lastusedate, shortcode) VALUES (?,?,?,?,?)";
        return this.session.prepare(insertStatement);
    }
    
    /**Select a shortcode from the url table usint the longname
     * @param name longname*/
    public String selectShortcode(PreparedStatement statement, String name) {
        BoundStatement boundStatement = new BoundStatement(statement);
        ResultSet rs = this.session.execute(boundStatement.bind(name));
        Row r = rs.one();
        String sc;
        if(rs.one() != null) {
        	sc = r.getString("shortcode");
        }
        else {
        	sc = null;
        }
		return sc;
    }
    
    /**Select an id from the url table usint the longname
     * @param name longname*/
    public int selectID(PreparedStatement statement, String name) {
        BoundStatement boundStatement = new BoundStatement(statement);
        ResultSet rs = this.session.execute(boundStatement.bind(name));
        int id = rs.one().getInt(0);
        return id;
    }
    
    /**Create a PrepareStatement to insert a row to url table.
     * @return PreparedStatement*/
    public PreparedStatement urlSelectShortPrepared() {
        final String selectShortStatement = "SELECT shortcode FROM getShortie.url WHERE longname = ? ALLOW FILTERING";
        return this.session.prepare(selectShortStatement);
    }
    
    /**Create a PrepareStatement to insert a row to url table.
     * @return PreparedStatement*/
    public PreparedStatement urlSelectIdPrepared() {
        final String selectIdStatement = "SELECT id FROM getShortie.url WHERE longname = ? ALLOW FILTERING";
        return this.session.prepare(selectIdStatement);
    }
    
    /**Update the lastusedate column in the url table.
     * @param id id*/
    public void updateLastUseDate(int id, String cDate) {
    		final String query = "UPDATE getShortie.url SET lastusedate = " + cDate + " WHERE id = " + id + ";";
    		this.session.execute(query);
    }
    
    
    /**Update the urlCounter table.
     * @param id id*/
    public void updateUrlCounter(int id) {
    		final String query = "UPDATE getShortie.urlcounter SET usage = usage + 1 WHERE id = " + id + ";";
    		this.session.execute(query);
    }
    
    /**Select a row from url table.
  	 * @param longurl longname*/
  	public void selectLongURL(String longurl) {

  		final String query = ("SELECT * FROM getShortie.url where longname = '" + longurl + "' ALLOW FILTERING;");
  		Row row = this.session.execute(query).one();
  		LOG.info("Obtained row: {} | {} | {} | {} | {} ", row.getInt("id"), row.getString("longname"),
  				row.getString("createddate"), row.getString("lastusedate"), row.getString("shortcode"));
  	}
  	
  	/**Select a row from url table.
  	 * @param code shortcode*/
  	public void selectShortURL(String code) {

  		final String query = ("SELECT * FROM getShortie.url where shortcode = '" + code + "' ALLOW FILTERING;");
  		Row row = this.session.execute(query).one();
  		LOG.info("Obtained row: {} | {} | {} | {} | {} ", row.getInt("id"), row.getString("longname"),
  				row.getString("createddate"), row.getString("lastusedate"), row.getString("shortcode"));
  	}
    
  	//Select all rows from the url table.
  	public void selectAllUrls() {

  		final String query = "SELECT * FROM getShortie.url;";
  		List<Row> rows = this.session.execute(query).all();
  		for (Row row : rows) {
  			LOG.info("Obtained row: {} | {} | {} | {} | {} ", row.getInt("id"), row.getString("longname"),
  					row.getString("createddate"), row.getString("lastusedate"), row.getString("shortcode"));
  		}
  	}
  	
    /**Select the counter value from the urlcounter table.
     * @param id id*/
    public long selectFromCounter(int id) {
    	
    	final String query = "SELECT usage FROM getShortie.urlcounter WHERE id = " + id + ";";
    	Row row = this.session.execute(query).one();
		LOG.info("Obtained row: {} ", row.getLong("usage"));
		long count = row.getLong("usage");
        return count;
    }
    				
    //Select all rows from the urlcounter table.
  	public void selectAllUrlCounters() {

  		final String query = "SELECT * FROM getShortie.urlcounter;";
  		List<Row> rows = this.session.execute(query).all();
  		for (Row row : rows) {
  			LOG.info("Obtained row: {} | {} ", row.getInt("id"), row.getLong("usage"));
  		}
  	}
}
