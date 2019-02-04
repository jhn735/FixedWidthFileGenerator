package com.file.read.csv;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A class which represents the csv file.
 */
public class CSV {
	private final Map< String, Integer > _columnNameToOffsetMap;

	private List< List<String> > _rows;

	private List<String> _lastAccessedRow;
	private Integer      _lastAcessedRowNumber = -1;

	private Integer _lineCount;
	public CSV( List<String> columns ){
		this._columnNameToOffsetMap = IntStream.range(0, columns.size() ).boxed().collect(
				Collectors.toMap( columns::get, Function.identity() )
		);

		this._rows      = new ArrayList<>();
		this._lineCount = 0;
	}

	/**
	 * Adds the given values to their respective columns' list. Increments the line size variable.
	 * @param csvLine The list of values representing a line on the csv file.
	 */
	public void add( List<String> csvLine ){
		if( csvLine.size() != this._columnNameToOffsetMap.size() ){
			throw new IllegalArgumentException("The number of values on a line given must be equal to the number of columns.");
		}
		this._lineCount++;

		this._rows.add( new ArrayList<>(csvLine) );
	}

	/**
	 * Retrieves the value at the given column and line number.
	 * @param column The name of the column of the value.
	 * @param rowNumber The line number.
	 * @return The string value found at the given row number and column name.
	 * @throws ArrayIndexOutOfBoundsException If row number given exceeds the number of rows in the csv file.
	 */
	public String get( String column, int rowNumber ) throws ArrayIndexOutOfBoundsException {
		Integer columnOffset = this.getColumnOffset( column );

		if( rowNumber != this._lastAcessedRowNumber ){
			this._lastAccessedRow      = this._rows.get( rowNumber );
			this._lastAcessedRowNumber = rowNumber;
		}

		return this._lastAccessedRow.get( columnOffset );
	}

	/**
	 * Retrieves the line count of the the data within the csv file.
	 * @return The number of data rows in the csv file.
	 */
	public int lineCount(){
		return this._lineCount;
	}

	/**
	 * Retrieves the total line count of the csv file excluding blank lines.
	 * @return The number of data rows including the header row.
	 */
	public int lineCountWithHeader(){
		return this.lineCount() + 1;
	}

	/**
	 * Retrieves the column offset for the given column
	 * @param columnName The name of the column.
	 * @return The number of the column as it appears in the file.
	 * @throws IllegalArgumentException If the column given is unknown.
	 */
	private Integer getColumnOffset( String columnName ) throws IllegalArgumentException {
		if( !this._columnNameToOffsetMap.containsKey( columnName ) ){
			throw new IllegalArgumentException( "Unknown column '" + columnName + "'." );
		}
		return this._columnNameToOffsetMap.get( columnName );
	}
}
