package com.file.format;

import java.util.*;

public interface FileFormat extends SortedMap< FieldIdentifier, ValueFormat > {
	default String formatEntry( Map<String, String> entry ){
		StringBuilder formattedEntryString = new StringBuilder();

		for( Map.Entry< FieldIdentifier, ValueFormat> formatEntry: this.entrySet() ){
			ValueFormat     mappedValue = formatEntry.getValue();
			FieldIdentifier fieldID     = formatEntry.getKey();

			String valueToFormat  = entry.get( fieldID.getName() );
			String formattedValue = mappedValue.formatValue( valueToFormat );
			formattedEntryString.append( formattedValue );
		}

		return formattedEntryString.toString();
	}

	default List<String> getOrderedListOfFields(){
		List<String> orderedListOfFields = new ArrayList<>();

		for( FieldIdentifier fieldID: this.keySet() ){
			orderedListOfFields.add( fieldID.getName() );
		}

		return Collections.unmodifiableList( orderedListOfFields );
	}
}
