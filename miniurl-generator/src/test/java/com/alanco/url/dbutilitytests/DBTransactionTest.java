package com.alanco.url.dbutilitytests;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alanco.url.dbutility.DBConnector;
import com.alanco.url.dbutility.URLRepository;
import com.alanco.url.utility.IDGenerator;
import com.alanco.url.utility.URLCleaner;
import com.alanco.url.utility.URLCruncher;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

/**
 * Test class which will test the following operations on the Cassandra Database.
 * - Insert 5 Rows to the url table using PreparedStatement
 * - Update the urlcounter table
 * - Select the shortcode of each new row by longname using PreparedStatement
 * - Select all data from each table
 * - Select a row from a table
 */
public class DBTransactionTest {

private static final Logger LOG = LoggerFactory.getLogger(ShortCodeGenerationTest.class);
	
	static IDGenerator idg = new IDGenerator();
	static URLCruncher uC = new URLCruncher();
	static String cDate = LocalDate.now().toString();
	
	public static void main(String[] args) throws Exception {
		
		DBConnector conn = new DBConnector();
		System.out.println("DB connection made");
		Session sn = conn.getSession();
		System.out.println("Session created");
		
		int id1 = idg.getID();
		int id2 = idg.getID();
		int id3 = idg.getID();
		int id4 = idg.getID();
		int id5 = idg.getID();
		System.out.println("id1: " + id1 + ", id2: " + id2 + ", id3: " + id3 + ", id4: " + id4 + ", id5: " + id5);
		
		String url1 = URLCleaner.urlClean("https://www.oracle.com/ie/index.html");
		String url2 = URLCleaner.urlClean("https://www.linkedin.com/company/oracle");
		String url3 = URLCleaner.urlClean("https://www.netsuite.com/portal/home.shtml");
		String url4 = URLCleaner.urlClean("https://wsr.pearsonvue.com/vouchers/pricelist/oracle.asp");
		String url5 = URLCleaner.urlClean("https://github.com/oracle/netsuite-suitecloud-sdk/blob/master/LICENSE.txt");
		System.out.println("url1: " + url1 + ", \nurl2: " + url2 + ", \nurl3: " + url3 + ", \nurl4: " + url4 + ", \nurl5: " + url5);
		
		String sC1 = URLCruncher.idToShortie(id1);
		String sC2 = URLCruncher.idToShortie(id2);
		String sC3 = URLCruncher.idToShortie(id3);
		String sC4 = URLCruncher.idToShortie(id4);
		String sC5 = URLCruncher.idToShortie(id5);
		System.out.println("sC1: " + sC1 + ", \nsC2: " + sC2 + ", \nsC3: " + sC3 + ", \nsC4: " + sC4 + ", \nsC5: " + sC5);
		
		try {
			URLRepository ur = new URLRepository(sn);
			
			//Insert rows into url table
            PreparedStatement prepedInsert = ur.urlInsertPrepared();
            ur.insertUrl(prepedInsert, id1, url1, cDate, cDate, sC1);
            ur.insertUrl(prepedInsert, id2, url2, cDate, cDate, sC2);
            ur.insertUrl(prepedInsert, id3, url3, cDate, cDate, sC3);
            ur.insertUrl(prepedInsert, id4, url4, cDate, cDate, sC4);
            ur.insertUrl(prepedInsert, id5, url5, cDate, cDate, sC5);
			
            //Update the urlcounter table
            ur.updateUrlCounter(id1);
            ur.updateUrlCounter(id2);
            ur.updateUrlCounter(id3);
            ur.updateUrlCounter(id4);
            ur.updateUrlCounter(id5);
            
            LOG.info("Use prepared statement to select the shortcode using the longname in the url table.");
            PreparedStatement shortSelect = ur.urlSelectShortPrepared();
            String code1 = ur.selectShortcode(shortSelect, url1);
            System.out.println("code1: " + code1);
            String code2 = ur.selectShortcode(shortSelect, url2);
            System.out.println("code1: " + code2);
            String code3 = ur.selectShortcode(shortSelect, url3);
            System.out.println("code1: " + code3);
            String code4 = ur.selectShortcode(shortSelect, url4);
            System.out.println("code1: " + code4);
            String code5 = ur.selectShortcode(shortSelect, url5);
            System.out.println("code1: " + code5);
            
            LOG.info("Select all records in the url table.");
            ur.selectAllUrls();
            
            LOG.info("Select a url record by longname url4");
            ur.selectLongURL(url4);
            
            LOG.info("Select a url record by shortcode sC4");
            ur.selectShortURL(sC4);
            
            LOG.info("Select all records in the urlcounter table.");
            ur.selectAllUrlCounters();
            
            LOG.info("Select a urlcounter record by id id4 which is related to url4");
            ur.selectFromCounter(id4);
            
		} finally {
			System.out.println("Closing the connection.");
			conn.close();
            LOG.info("Please delete your table after verifying the presence of the data in portal or from CQL");
        }
	}
}
