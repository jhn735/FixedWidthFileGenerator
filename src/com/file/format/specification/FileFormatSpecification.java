package com.file.format.specification;

public interface FileFormatSpecification {
	String formatValueAsString( String fieldName, String value );
	byte[] formatValueAsBytes(  String fieldName, String value );
}
