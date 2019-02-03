package com.file.format;

import java.util.List;

/**
 * Represents the specific formatting of a value.
 */
public interface ValueFormat {
	/**
	 * Formats the given value according to it's implementation.
	 * @param value The value to format composed of a collection of objects of Type T.
	 * @return A formatted list of the value's component objects.
	 */
	<T> List<T> formatValue( List<T> value );
}
