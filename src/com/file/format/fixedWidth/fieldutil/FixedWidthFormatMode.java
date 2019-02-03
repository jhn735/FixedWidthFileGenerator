package com.file.format.fixedWidth.fieldutil;

public enum FixedWidthFormatMode {
	CHARACTER_MODE, BYTE_MODE;

	public static FixedWidthFormatMode parseMode( String modeString ){
		if( "char".equalsIgnoreCase(modeString) || "character".equalsIgnoreCase(modeString) ){
			return FixedWidthFormatMode.CHARACTER_MODE;
		}

		if( "byte".equalsIgnoreCase(modeString) ){
			return FixedWidthFormatMode.BYTE_MODE;
		}

		throw new IllegalArgumentException( "Given string does not match know format modes.");
	}
}
