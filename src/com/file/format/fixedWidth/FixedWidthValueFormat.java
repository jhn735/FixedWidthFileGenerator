package com.file.format.fixedWidth;

import com.file.format.ValueFormat;
import com.file.format.fixedWidth.fieldutil.FixedWidthFieldAlignment;
import com.file.format.fixedWidth.fieldutil.FixedWidthFormatMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class FixedWidthValueFormat implements ValueFormat {
	public static final FixedWidthFormatMode     DEFAULT_FORMAT_MODE = FixedWidthFormatMode.CHARACTER_MODE;
	public static final FixedWidthFieldAlignment DEFAULT_ALIGNMENT   = FixedWidthFieldAlignment.LEFT;

	protected final FixedWidthFormatMode _formatMode;
	protected final FixedWidthFieldAlignment _fieldAlignment;

	protected final Integer   _fieldLength;
	protected final Character _paddingCharacter;
	protected final Byte      _paddingByte;

	public FixedWidthValueFormat( FixedWidthFormatMode mode, FixedWidthFieldAlignment alignment, int length, String padding ){
		this._formatMode     = mode;
		this._fieldAlignment = alignment;

		this._fieldLength    = new Integer( length );
		if(this._fieldLength < 0 ){
			throw new IllegalArgumentException( "Field length must be greater than zero." );
		}

		String byteDecodeString = this.isInByteMode() ? padding : "0x00";
		this._paddingByte       = Byte.decode( byteDecodeString );
		this._paddingCharacter  = padding.charAt(0);
	}

	public FixedWidthValueFormat( int length, String padding ){
		this( FixedWidthValueFormat.DEFAULT_FORMAT_MODE, FixedWidthValueFormat.DEFAULT_ALIGNMENT, length, padding );
	}

	@Override
	public <T> List<T> formatValue( List<T> value ){
		if( value.size() >= this._fieldLength ){
			return this.truncateValue( value );
		}

		return this.alignValue( value );
	}

	protected <T> List<T> alignValue( List<T> value ){
		List<T> padding = (List<T>) this.constructPadding( value.get(0).getClass(), value.size() );

		List<T> alignedValue = new ArrayList<>( this._fieldLength );
		if( this._fieldAlignment == FixedWidthFieldAlignment.LEFT ){
			alignedValue.addAll( value );
			alignedValue.addAll( padding );

		} else if( this._fieldAlignment == FixedWidthFieldAlignment.RIGHT ){
			alignedValue.addAll( padding );
			alignedValue.addAll( value );
		}

		return alignedValue;
	}

	protected <T> List<T> truncateValue( List<T> value ){
		List<T> truncatedValue = value.subList( 0, this._fieldLength );
		return new ArrayList<>( truncatedValue );
	}

	protected <T> List<T> constructPadding( Class<T> clazz, int valueSize ){
		Integer paddingSize = this._fieldLength - valueSize;
		T paddingObject = this.getPaddingObject( clazz );
		return Collections.nCopies( paddingSize, paddingObject );
	}

	protected <T> T getPaddingObject( Class<T> clazz ){
		if( clazz.equals( Character.class ) ){
			if( !this.isInCharacterMode() ){
				throw new IllegalStateException( "Cannot format a non-character based value when in Character mode." );
			}

			return clazz.cast( this._paddingCharacter );
		}

		if( clazz.equals( Byte.class ) ){
			if( !this.isInByteMode() ){
				throw new IllegalStateException( "Cannot format a non-byte based value when in Byte mode." );
			}

			return clazz.cast( this._paddingByte );
		}

		throw new UnsupportedOperationException( "Lists of type '" + clazz.getName() + "' is unsupported." );
	}

	public boolean isInByteMode(){
		return this._formatMode == FixedWidthFormatMode.BYTE_MODE;
	}

	public boolean isInCharacterMode(){
		return this._formatMode == FixedWidthFormatMode.CHARACTER_MODE;
	}
}
