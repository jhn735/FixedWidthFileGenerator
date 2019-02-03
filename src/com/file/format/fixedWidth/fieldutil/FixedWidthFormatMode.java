package com.file.format.fixedWidth.fieldutil;

import java.text.ParseException;

/**
 * Represents the type of object which constitutes a formatted value. For fixed width these objects are either characters
 * or bytes.
 */
public enum FixedWidthFormatMode {
	CHARACTER_MODE( Character.class ), BYTE_MODE( Byte.class );

	public final Class FormatClass;
	FixedWidthFormatMode( Class clazz ){
		this.FormatClass = clazz;
	}

	/**
	 * Given a class object, validates that it matches with this instance's FormatClass value.
	 * @param clazz The class to validate.
	 * @throws IllegalStateException If the given class does not match with this instance's FormatClass value.
	 */
	public void validateFormatClass( Class clazz ) throws IllegalStateException {
		if( !this.FormatClass.equals( clazz ) ){
			throw new IllegalStateException(
					"Cannot format a non-" + this.FormatClass.getName() + " based value when in format mode is " + this.name() + "."
			);
		}
	}

	/**
	 * Parses the type of mode from the given string.
	 * @param modeString The string containing the string value of the Format mode.
	 * @return One of the FixedWidthFormatMode values which bests matches the string given.
	 * @throws ParseException When unable to determine format from the given string.
	 */
	public static FixedWidthFormatMode parseMode( String modeString ) throws ParseException {
		if( "char".equalsIgnoreCase(modeString) || "character".equalsIgnoreCase(modeString) ){
			return FixedWidthFormatMode.CHARACTER_MODE;
		}

		if( "byte".equalsIgnoreCase(modeString) ){
			return FixedWidthFormatMode.BYTE_MODE;
		}

		throw new ParseException( "Given string does not match know format modes.", 0 );
	}
}
