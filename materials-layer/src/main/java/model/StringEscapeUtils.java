package model;

import connection.parsers.Parser;

public class StringEscapeUtils {

	public static String escapeSpecialCharacters(String line) {

		if (line != null) {
			for (int index = 0; index < Parser.SPECIAL_CHARACTERS.length(); index++) {
				String special = Parser.SPECIAL_CHARACTERS.substring(index, index + 1);
				line = line.replace(special, "\\" + special);
			}
		}

		return line;

	}

	public static String removeEscapers(String line) {

		if (line != null) {
			for (int index = 0; index < Parser.SPECIAL_CHARACTERS.length(); index++) {
				String special = Parser.SPECIAL_CHARACTERS.substring(index, index + 1);
				line = line.replace("\\" + special, special);
			}
		}

		return line;

	}

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
