package com.file.format;

/**
 * Identifies the field within a formatted file.
 */
public interface FieldIdentifier extends Comparable<FieldIdentifier> {
	/**
	 * Retrieves the name of the field in question.
	 * @return A string containing the name of the field in question.
	 */
	String getName();
}
