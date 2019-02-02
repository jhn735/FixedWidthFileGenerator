package com.file.format;

/**
 * Represents the specific formatting of a value.
 */
public interface ValueFormat {
	/**
	 * Formats the given value according to it's implementation.
	 * @param value The value to format.
	 * @return String holding the formatted value.
	 */
	String formatValue( CharSequence value );
}
