package com.file.format.fixedWidth;

import com.file.format.FieldIdentifier;

public class FixedWidthFieldIdentifier implements FieldIdentifier, Comparable<FixedWidthFieldIdentifier> {
	protected static final String INVALID_START_POS_ERR_MSG =
			"The start position of a fixed width field must be greater than or equal to 0";

	protected final String  _name;
	protected final Integer  _startPosition;

	public FixedWidthFieldIdentifier( CharSequence fieldName, int startPosition ){
		this._name = fieldName.toString();
		this._startPosition = new Integer( startPosition );

		if( this._startPosition < 0 ){
			throw new IllegalArgumentException( FixedWidthFieldIdentifier.INVALID_START_POS_ERR_MSG );
		}
	}

	@Override
	public String getName() {
		return this._name;
	}

	@Override
	public int compareTo( FixedWidthFieldIdentifier o ){
		return this._startPosition.compareTo( o._startPosition );
	}
}
