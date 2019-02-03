package com.file.format.fixedWidth;

import com.file.format.ValueFormat;
import com.file.format.fixedWidth.fieldutil.FixedWidthFieldAlignment;
import com.file.format.fixedWidth.fieldutil.FixedWidthFormatMode;

public abstract class FixedWidthValueFormat implements ValueFormat {
	protected final FixedWidthFormatMode _formatMode;
	protected final FixedWidthFieldAlignment _fieldAlignment;

	protected final Integer   _fieldLength;
	protected final Character _paddingCharacter;
	protected final Byte      _paddingByte;

	public FixedWidthValueFormat( FixedWidthFormatMode mode, FixedWidthFieldAlignment alignment, int length, String padding ){
		this._formatMode = mode;
		this._fieldAlignment = alignment;

		this._fieldLength = new Integer(length);
		if(this._fieldLength < 0 ){
			throw new IllegalArgumentException( "Field length must be greater than zero." );
		}

		this._paddingCharacter = padding.charAt(0);
		this._paddingByte = this.isInByteMode() ? Byte.decode( padding ) : null;
	}

	@Override
	public String formatValue( CharSequence value ) {
		return null;
	}

	public boolean isInByteMode(){
		return this._formatMode == FixedWidthFormatMode.BYTE_MODE;
	}

	public boolean isInCharacterMode(){
		return this._formatMode == FixedWidthFormatMode.CHARACTER_MODE;
	}
}
