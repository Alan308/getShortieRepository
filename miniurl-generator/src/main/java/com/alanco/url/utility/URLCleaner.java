package com.alanco.url.utility;

public class URLCleaner {

	public static String urlClean(String url) {
		
		String rawURL = url;
		String cleanURL = rawURL.replaceAll("[^a-zA-Z0-9]", "");
		return cleanURL;
	}
}
