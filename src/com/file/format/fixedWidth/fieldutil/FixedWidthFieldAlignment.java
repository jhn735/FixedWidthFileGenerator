package com.file.format.fixedWidth.fieldutil;

public enum FixedWidthFieldAlignment {
	LEFT, RIGHT;

	public static FixedWidthFieldAlignment parseAlignment( String alignmentString ){
		if( "left".equalsIgnoreCase(alignmentString) ){
			return FixedWidthFieldAlignment.LEFT;
		}

		if( "right".equalsIgnoreCase(alignmentString) ){
			return FixedWidthFieldAlignment.RIGHT;
		}

		throw new IllegalArgumentException( "Alignment passed in must be either 'left' or 'right'.");
	}
}
