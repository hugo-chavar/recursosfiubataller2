package model;

import connection.Parser;


public class StringEscapeUtils {
	
	public static String escapeSpecialCharacters(String line) {
		
		if (line != null) {
			for (int index = 0; index < Parser.SPECIAL_CHARACTERS.length(); index++) {
				char specialChar = Parser.SPECIAL_CHARACTERS.charAt(index);
				int line_idx = line.indexOf(specialChar);
				while (line_idx > -1) {
					line = line.substring(0, line_idx) + "\\" + line.substring(line_idx, line.length());
					int old_idx = line_idx;
					line_idx = line.substring(line_idx + 2, line.length()).indexOf(specialChar);
					if (line_idx > -1) {
						line_idx += old_idx + 2;
					}
				}
			}
		}
		
		return line;
		
	}
	
	public static String removeSpecialCharacters(String line) {
		
		if (line != null) {
			int line_idx = line.indexOf("\\");
			while (line_idx > -1) {
				line = line.substring(0, line_idx) + line.substring(line_idx + 1, line.length());
				line_idx = line.indexOf("\\");
			}
		}
		
		return line;
		
	}
	
	public static String[] ignoreSpecialCharactersInSplit(String[] splitted, String specialChar) {
		
		int i = 0;
		int j = 0;
		while (i < splitted.length) {
			if (splitted[i].endsWith("\\")) {
				splitted[i] = splitted[i].substring(0, splitted[i].length()-1);
				splitted[i] = splitted[i].concat(specialChar + splitted[i+1]);
				splitted[i+1] = null;
				i++;
				j++;
			}
			i++;
		}
		
		String[] newSplitted = new String[i-j];
		j = 0;
		for (i = 0; i < splitted.length; i++) {
			if (splitted[i] != null) {
				newSplitted[j] = splitted[i];
				j++;
			}
		}
		
		return newSplitted;
		
	}

}
