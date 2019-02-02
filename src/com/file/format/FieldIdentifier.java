package com.file.format;

/**
 * Identifies the field within a formatted file. This can be extended to include types of information other than the
 * 'name'. If the fields within a formatted file are ordered in a way that is different from name, then the 'compareTo'
 *  method can be overridden.
 */
public interface FieldIdentifier extends Comparable<FieldIdentifier> {
	/**
	 * Retrieves the name of the field in question.
	 * @return A string containing the name of the field in question.
	 */
	String getName();

	/**
	 * See {@link java.lang.Comparable#compareTo(Object)}.
	 */
	default int compareTo( FieldIdentifier o ){
		return this.getName().compareTo( o.getName() );
	}
}
