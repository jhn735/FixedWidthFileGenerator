package com.file.format.fixedWidth;

import com.file.format.FieldIdentifier;
import com.file.format.FileFormat;
import com.file.format.ValueFormat;
import com.file.read.csv.CSV;
import com.file.read.csv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.TreeMap;

public class FixedWidthFileFormat extends TreeMap<FieldIdentifier, ValueFormat> implements FileFormat {
	public FixedWidthFileFormat( String specificationPath ) throws IOException, ParseException {
		super();

		CSVReader specificationReader = new CSVReader( new File(specificationPath) );
		CSV specification = specificationReader.readAsCSV();


	}
}
