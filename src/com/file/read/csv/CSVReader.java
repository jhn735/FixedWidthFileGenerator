package com.file.read.csv;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Stream;

public class CSVReader extends BufferedReader {
	private static final Character DEFAULT_VALUE_DELIMITER = ',';

	private final String _delimiter;

	public CSVReader( File csvFile, Character delimiter ) throws FileNotFoundException {
			super( new FileReader( csvFile ));
			this._delimiter = Character.toString( delimiter );
	}

	public CSVReader( File csvFile ) throws FileNotFoundException {
		this( csvFile, CSVReader.DEFAULT_VALUE_DELIMITER );
	}

	public Map< String, List<String> > readAsCSVToMap() throws IOException, ParseException {
		return readAsCSVToMap();
	}

	public CSVMap readAsCSVMap() throws IOException, ParseException {
		List<String> columns = this.readCSVLine( this.readLine() );

		CSVMap csvMap = new CSVMap( columns );

		try {
			Stream<String> lines = this.lines();
			lines.forEach(line -> {
				List<String> values = this.readCSVLine(line);
				csvMap.add(values);
			});
		} catch( IllegalArgumentException i ){
			throw new ParseException( i.getMessage(), csvMap.sizeWithHeader() );
		}

		return csvMap;
	}

	private List<String> readCSVLine( String line ){
		String[] separatedValues = line.split(  this._delimiter );
		return Arrays.asList( separatedValues );
	}

	public static class CSVMap extends HashMap< String, List<String> >{
		private final List<String> _columns;

		private Integer _size;
		public CSVMap( List<String> columns ){
			super();
			this._columns = Collections.unmodifiableList( columns );
			for( String column: this._columns ){
				super.put( column, new ArrayList<>() );
			}

			this._size = 0;
		}

		public void add( List<String> csvLine ){
			if( csvLine.size() != this._columns.size() ){
				throw new IllegalArgumentException("The number of values on a line given must be equal to the number of columns.");
			}
			this._size++;
			for( int columnNumber = 0; columnNumber < this._columns.size(); columnNumber++ ){
				String column = this._columns.get(columnNumber);
				String value  = csvLine.get( columnNumber );
				this.get(column).add( value );
			}
		}

		public String get( String column, int rowNumber ) throws ArrayIndexOutOfBoundsException {
			return this.get( column ).get( rowNumber );
		}

		public int size(){
			return this._size;
		}

		public int sizeWithHeader(){
			return this.size() + 1;
		}
	}

}
