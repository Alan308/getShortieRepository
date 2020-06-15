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
import java.util.Scanner;

/**
 * Test class which will complete the following operations to generate a short
 * url which may or may not already exist in the Database. - Checks if a
 * shortcode already exists for the entered url. - Scanner used to - Returns the
 * existing short url - Or - Creates a new short url, stores it in the database
 * and returns it for the user - Returns statistics on how many times this short
 * url has been provided
 */
public class ShortCodeGenerationTest {

	private static final Logger LOG = LoggerFactory.getLogger(ShortCodeGenerationTest.class);

	static IDGenerator idg = new IDGenerator();
	static URLCruncher uC = new URLCruncher();
	static String cDate = LocalDate.now().toString();

	public static void main(String[] args) throws Exception {

		DBConnector conn = new DBConnector();
		System.out.println("DB connection made");
		Session sn = conn.getSession();
		System.out.println("Session created");

		String longURL = "";
		String shortURl = "";
		String stats = "";
		String url1 = "";
		
		try {
			@SuppressWarnings("resource")
			Scanner userIn = new Scanner(System.in);
			System.out.println("Please enter a valid url to be shortened, on the next line: (Max 150 characters)");
			String rawURL = userIn.nextLine();
			int urlLength = rawURL.length();
			if (urlLength < 1) {
				
				throw new Exception("Url is not valid.");
			}

			if (urlLength > 150) {
				System.out.println("rawURL: " + rawURL);
				rawURL = rawURL.substring(0, 150);

				longURL = rawURL;
			}
			url1 = URLCleaner.urlClean(longURL);

			URLRepository ur = new URLRepository(sn);

			LOG.info("Use prepared statement to select the shortcode using the longname in the url table.");
			PreparedStatement shortSelect = ur.urlSelectShortPrepared();
			String code1 = ur.selectShortcode(shortSelect, url1);
			System.out.println("code1: " + code1);

			if (code1 != null) {

				shortURl = ("https://shortie.com/" + code1);

				LOG.info("Use prepared statement to select the shortcode using the longname in the url table.");
				PreparedStatement idSelect = ur.urlSelectIdPrepared();
				int id1 = ur.selectID(idSelect, url1);
				System.out.println("id1: " + id1);

				ur.updateUrlCounter(id1);
				long count = ur.selectFromCounter(id1);

				stats = ("Your original url was: " + urlLength + "charachters in length.\nYour short url has "
						+ shortURl.length() + " characters.\nThis short url has been provided " + count + " times.");

			} else {

				int id1 = idg.getID();
				String createdDate = cDate;
				String lastUsedDate = cDate;
				String sC1 = URLCruncher.idToShortie(id1);

				// Insert a new row into the url table
				PreparedStatement preparedStatement = ur.urlInsertPrepared();
				ur.insertUrl(preparedStatement, id1, url1, createdDate, lastUsedDate, sC1);

				// Update the urlcounter table
				ur.updateUrlCounter(id1);

				shortURl = ("https://shortie.com/" + sC1);
				stats = ("Your original url was: " + urlLength + " charachters in length.\nYour short url has "
						+ shortURl.length()
						+ " characters.\nThis is the first time that this short url has been provided.");
			}
			System.out.println("-------- Result --------\n");
			System.out.println("Your short url is: " + shortURl);
			System.out.println(stats);
			System.out.println("\n-------- Result --------");

		} 
		catch(Exception e) {
			
		}
		finally {
			LOG.info("Closing the connection.");
			conn.close();
		}
	}
}
