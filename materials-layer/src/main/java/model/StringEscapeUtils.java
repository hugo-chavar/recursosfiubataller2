package model;

import connection.Parser;

public class StringEscapeUtils {

	public static String escapeSpecialCharacters(String line) {

		if (line != null) {
			for (int index = 0; index < Parser.SPECIAL_CHARACTERS.length(); index++) {
//				char specialChar = Parser.SPECIAL_CHARACTERS.charAt(index);
				String special = Parser.SPECIAL_CHARACTERS.substring(index, index + 1);
				line = line.replace(special, "\\" + special);
//				int line_idx = line.indexOf(specialChar);
//				while (line_idx > -1) {
//					line = line.substring(0, line_idx) + "\\" + line.substring(line_idx, line.length());
//					int old_idx = line_idx;
//					line_idx = line.substring(line_idx + 2, line.length()).indexOf(specialChar);
//					if (line_idx > -1) {
//						line_idx += old_idx + 2;
//					}
//				}
			}
		}

		return line;

	}

	public static String removeEscapers(String line) {

		if (line != null) {
			// esta variante no saca todas las barras que tiene el string, solo las q preceden
			// a un caracter especial
			for (int index = 0; index < Parser.SPECIAL_CHARACTERS.length(); index++) {
				String special = Parser.SPECIAL_CHARACTERS.substring(index, index + 1);
				line = line.replace("\\" + special, special);
			}
//			line = newLine;
//			int line_idx = line.indexOf("\\");
//			while (line_idx > -1) {
//				line = line.substring(0, line_idx) + line.substring(line_idx + 1, line.length());
//				line_idx = line.indexOf("\\");
//			}
		}

		return line;

	}

//	public static String[] ignoreSpecialCharactersInSplit(String[] splitted, String specialChar) {
//
//		int i = 0;
//		int j = 0;
//		while (i < splitted.length) {
//			if (splitted[i].endsWith("\\")) {
//				splitted[i] = splitted[i].substring(0, splitted[i].length() - 1);
//				splitted[i] = splitted[i].concat(specialChar + splitted[i + 1]);
//				splitted[i + 1] = null;
//				i++;
//				j++;
//			}
//			i++;
//		}
//
//		String[] newSplitted = new String[i - j];
//		j = 0;
//		for (i = 0; i < splitted.length; i++) {
//			if (splitted[i] != null) {
//				newSplitted[j] = splitted[i];
//				j++;
//			}
//		}
//
//		return newSplitted;
//
//	}
	
	public static String[] splitIgnoringEscaped(String input, char escapeChar) {
		//tal vez se pueda mejorar este metodo ver:
		//http://stackoverflow.com/questions/820172/how-to-split-a-comma-separated-string-while-ignoring-escaped-commas
		String regularExpressionSpecialChars = "/.*+?|()[]{}\\";

	    String escapedEscapeChar = String.valueOf(escapeChar);

	    // if the escape char for our comma separated list needs to be escaped 
	    // for the regular expression, escape it using the \ char
	    if(regularExpressionSpecialChars.indexOf(escapeChar) != -1) 
	        escapedEscapeChar = "\\" + escapeChar;
		return input.split("(?<=(?<!\\\\)(\\\\\\\\){0,100})" + escapedEscapeChar);
		
	}

}
