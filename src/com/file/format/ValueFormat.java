package com.file.format;

import java.util.List;

/**
 * Represents the specific formatting of a value.
 */
public interface ValueFormat {
	/**
	 * Formats the given value according to it's implementation.
	 * @param value The value to format composed of a collection of objects of Type T.
	 * @param type The type of the component of the formatted value.
	 * @return String holding the formatted value.
	 */
	<T> List<T> formatValue( List<T> value );
}
