package com.file.format.fixedWidth.fieldutil;

public enum FixedWidthFormatMode {
	CHARACTER, BYTE;

	private static String UNKNOWN_MODE_ERR_MSG = "Given string does not match know format modes.";

	public static FixedWidthFormatMode parseMode( String modeString ){
		for( FixedWidthFormatMode mode: FixedWidthFormatMode.values() ){
			if( mode.name().equalsIgnoreCase(modeString) )
				return mode;
		}

		throw new IllegalArgumentException( FixedWidthFormatMode.UNKNOWN_MODE_ERR_MSG );
	}
}
