package com.utils;

public class Base64Coder {

	
	// Mapping table from Base64 characters to 6-bit nibbles.
		private static final byte[] index_64 = {
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
			52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
			-1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
			15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
			-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
			41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1
		};

		// Mapping table from 6-bit nibbles to Base64 characters.
		private static final char[] base64_table = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
		
		/** Gets the encoded length of a buffer.
		 *	@param decoded_length Length of the buffer with the data.
		 *
		 *	@return Number of characters the encoded base64 string would have.
		 */
		private static int get_encoded_size( int decoded_length )
		{
			return 4 * ( ( decoded_length - 1 ) / 3 + 1 );
		}

		/** Gets the decoded length of a buffer.
		 *	@param encoded_buffer Buffer with the encoded data.
		 *
		 *	@return Number of characters the decoded buffer would have.
		 */
		private static int get_decoded_size( char[] encoded_buffer )
		{
			int decoded_length = ( encoded_buffer.length / 4 ) * 3;

			/* each '=' character at the end means one less byte in the output */
			for ( int i = 1; i <= 2; i++ )
				if ( encoded_buffer[encoded_buffer.length - i] == '=')
					decoded_length--;
				else
					break;

			return decoded_length;
		}

		/**
		 * Encodes a byte array into Base64 format.
		 * @param input    An array containing the data bytes to be encoded.
		 * @return      A character array containing the Base64 encoded data.
		 */
		public static char[] encode (byte[] input) {
			/* the buffer needs to hold at least 12 bits */
			int buffer = 0;
			int i;
			int j = 0;
			
			/* output buffer */
			char[] output = new char[get_encoded_size(input.length)];
		
			/* how many unprocessed, available bits are in our buffer */
			byte bits_available = 0;
		
			/* for every byte in the input */
			for ( i = 0; i < input.length; i++ )
			{
				/* shift each byte into our buffer from the right*/
				buffer = ( buffer << 8 ) | ( input[i] & 0xFF );
				bits_available += 8;
		
				/* check if there are enough bits available to output a b64 char */
				while ( bits_available >= 6 )
				{
					/* if there are more than 6 bits available, the ones we process first are the leftmost */
					/* mask to ignore already processed ones */
					output[j++] = base64_table[ ( buffer >> ( bits_available - 6 ) ) & 0x3F ];
					bits_available -= 6;
				}
			}
		
			/* special case to handle remaining bits (input size is not divisible by 3) */
			if ( bits_available > 0 )
			{
				/* we want to shift enough zeroes from the right to complete a 6 bits package */
				output[j++] = base64_table[ ( buffer << ( 6 - bits_available ) ) & 0x3F ];
		
				/* add padding ('=') so the output size is divisible by 4 */
				/* the amount of padding to add is calculated using a mapping function like this:
				 * 2 --> 2
				 * 4 --> 1
				 * where 2 and 4 are the only possible values for bits_available at this point*/
				if ( bits_available == 4 ) {
					output[j++] = '=';
				} else if ( bits_available == 2 ) {
					output[j++] = '=';
					output[j++] = '=';
				}
			}
		
			/* return the output */
			return output;
		}

		/**
		 * Decodes a byte array from Base64 format.
		 * @param s     A Base64 String to be decoded.
		 * @return      An array containing the decoded data bytes.
		 * @throws      IllegalArgumentException If the input is not valid Base64 encoded data.
		 */
		public static byte[] decode (String s) {
			/* the buffer needs to hold at least 12 bits */
			int buffer = 0;
			int i;
			int j = 0;
		
			/* how many unprocessed, available bits are in our buffer */
			byte bits_available = 0;
			
			/* character array representing the string */
			char[] input = s.toCharArray();
		
			/* output buffer */
			byte output[] = new byte[get_decoded_size(s.toCharArray())];
		
			/* the length of a valid base64 string is always multiple of 4 */
			if ( s.length() % 4 != 0 )
				throw new IllegalArgumentException ("Length of Base64 encoded input string is not a multiple of 4.");
		
			/* for every character in the input */
			for ( i = 0; i < input.length; i++ )
			{
				/* if a '=' character is detected, it stops processing */
				if ( input[i] == '=' )
					break;
		
				/* gets the 6 bit value representation of the current character */
				byte b = (input[i] > index_64.length) ? -1 : index_64[input[i]];
		
				/* checks if it was a valid base64 character */
				if ( b > 63 )
					throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
		
				/* shift the 6 lowest bits into our buffer from the right*/
				buffer = ( buffer << 6 ) | ( b & 0x3F);
				bits_available += 6;
		
				/* check if there are enough bits available to output a byte */
				while ( bits_available >= 8 )
				{
					/* if there are more than 8 bits available, the ones we process first are the leftmost */
					/* mask to ignore already processed ones */
					output[j++] = (byte)( ( buffer >> ( bits_available - 8 ) ) & 0xFF );
					bits_available -= 8;
				}
			}
		
			/* does some checks regarding the position of the '=' characters */
			if ( i < input.length && input[i] == '=' )
			{
				/* there can't be more than 2 '=' characters at the end */
				if ( s.length() - i > 2 )
					throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
		
				/* if the character before the last one is a '=', then the last one must also
				 * be a '=' */
				if ( s.length() - i == 2 && input[i++] != '=' )
					throw new IllegalArgumentException ("Illegal character in Base64 encoded data.");
			}
		
			/* returns the output */
			return output;
		}

		// Disable instantiation.
		private Base64Coder() {}
	
	
}
