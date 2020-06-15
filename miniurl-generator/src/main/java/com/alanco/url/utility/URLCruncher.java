package com.alanco.url.utility;

/*Converts an int value to a string of chars.
Also converts the string back to its original int value.*/
public class URLCruncher {

	//Convert an int value to a String.
	public static String idToShortie(int i) {
		
		char map[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray(); //Allowable chars.
		StringBuilder sb = new StringBuilder();
		while (i > 0) {
			
			sb.append(map[i % 62]);
			i = i / 62;
		}
		return sb.reverse().toString();
	}

	//Convert a String to an int value. The result will match the original int used to get the String.
	public static int shortieToId(String s) {
		
		int id = 0;
		for (int i = 0; i < s.length(); i++) {  
			
            if ('a' <= s.charAt(i) && s.charAt(i) <= 'z')  
            id = id * 62 + s.charAt(i) - 'a';  
            
            if ('A' <= s.charAt(i) && s.charAt(i) <= 'Z')  
            id = id * 62 + s.charAt(i) - 'A' + 26;  
            
            if ('0' <= s.charAt(i) && s.charAt(i) <= '9')  
            id = id * 62 + s.charAt(i) - '0' + 52;  
        }  
        return id;  
    }  
}