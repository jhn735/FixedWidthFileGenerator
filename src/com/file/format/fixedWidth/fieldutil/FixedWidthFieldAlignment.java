package com.file.format.fixedWidth.fieldutil;

import java.text.ParseException;

/**
 * Represents the alignment of individual values. In fixed width formats this either Left or Right aligned.
 */
public enum FixedWidthFieldAlignment {
	LEFT, RIGHT;

	/**
	 * Parses the alignment from the given string.
	 * @param alignmentString The string containing the alignment.
	 * @return The FixedWidthFieldAlignment which bests matches the given string.
	 * @throws ParseException When unable to determine the alignment from the given string.
	 */
	public static FixedWidthFieldAlignment parseAlignment( String alignmentString ) throws ParseException {
		if( "left".equalsIgnoreCase(alignmentString) ){
			return FixedWidthFieldAlignment.LEFT;
		}

		if( "right".equalsIgnoreCase(alignmentString) ){
			return FixedWidthFieldAlignment.RIGHT;
		}

		throw new ParseException( "Alignment passed in must be either 'left' or 'right'.", 0 );
	}
}
