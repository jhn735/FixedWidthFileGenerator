package com.file.format;

import java.util.*;

/**
 * A file format is essentially a list of fields mapped to methods of formatting values.
 * This map is ordered based off of the implementation of FieldIdentifier used.
 */
public interface FileFormat extends SortedMap< FieldIdentifier, ValueFormat > {
	/**
	 * Formats the entire given entry assuming each value is compose of zero or more objects.
	 * @param entry An entry of field names mapped to their respective values.
	 * @return The entry formatted as a string.
	 */
	default <T> List<T> formatEntry( Map<String, List<T> > entry ){
		List<T> formattedEntry = new ArrayList<>();

		for( Map.Entry< FieldIdentifier, ValueFormat> formatEntry: this.entrySet() ){
			ValueFormat     mappedValue = formatEntry.getValue();
			FieldIdentifier fieldID     = formatEntry.getKey();

			List<T> valueToFormat  = entry.get( fieldID.getName() );
			List<T> formattedValue = mappedValue.formatValue( valueToFormat );
			formattedEntry.addAll( formattedValue );
		}

		return formattedEntry;
	}

	/**
	 * Retrieves the list of field names.
	 * @return Immutable list of field names ordered by the natural order defined in the implementation of the FieldIdentifier interface.
	 */
	default List<String> getOrderedListOfFields(){
		List<String> orderedListOfFields = new ArrayList<>();

		for( FieldIdentifier fieldID: this.keySet() ){
			orderedListOfFields.add( fieldID.getName() );
		}

		return Collections.unmodifiableList( orderedListOfFields );
	}
}
