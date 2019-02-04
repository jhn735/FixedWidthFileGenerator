package com.file.read.csv;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Stream;

/**
 * Special case of buffered reader which reads file as a csv file.
 */
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

	/**
	 * Reads the contents of this reader as a csv into a CSV object.
	 * @return The contents of the reader in the form of a CSV instance.
	 * @throws IOException If something goes wrong with reading contents.
	 * @throws ParseException If contents are not in csv format.
	 */
	public CSV readAsCSV() throws IOException, ParseException {
		List<String> columns = this.readCSVLine();

		CSV csv = new CSV( columns );

		try {
			Stream<String> lines = this.lines();
			lines.forEach(line -> {
				List<String> values = CSVReader.readLineAsCSVLine( line, this._delimiter );
				csv.add(values);
			});
		} catch( IllegalArgumentException i ){
			throw new ParseException( i.getMessage(), csv.lineCountWithHeader() );
		}

		return csv;
	}

	/**
	 * Reads next line and formats it into a list of values which were separated by the reader's delimiter.
	 * @return A list of String values.
	 */
	public List<String> readCSVLine() throws IOException {
		return CSVReader.readLineAsCSVLine( this.readLine(), this._delimiter );
	}

	/**
	 * Formats the given string into a list of value which where separated by the given delimiter.
	 * @param line The given string representing a line in the CSV file.
	 * @param delimiter The delimiter which is supposed to separate individual values within the csv file.
	 * @return A list of string values.
	 */
	private static List<String> readLineAsCSVLine( String line, String delimiter ){
		String[] separatedValues = line.split( delimiter );
		return Arrays.asList( separatedValues );
	}
}
