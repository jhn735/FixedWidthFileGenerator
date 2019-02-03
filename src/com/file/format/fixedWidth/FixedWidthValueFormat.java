package com.file.format.fixedWidth;

import com.file.format.ValueFormat;
import com.file.format.fixedWidth.fieldutil.FixedWidthFieldAlignment;
import com.file.format.fixedWidth.fieldutil.FixedWidthFormatMode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
  * Implementation of the value format interface specifically for fixed width values.
 **/
public class FixedWidthValueFormat implements ValueFormat {
	private static final FixedWidthFormatMode     DEFAULT_FORMAT_MODE = FixedWidthFormatMode.CHARACTER_MODE;
	private static final FixedWidthFieldAlignment DEFAULT_ALIGNMENT   = FixedWidthFieldAlignment.LEFT;

	private final FixedWidthFormatMode _formatMode;
	private final FixedWidthFieldAlignment _fieldAlignment;

	private final Integer   _fieldLength;
	private final Character _paddingCharacter;
	private final Byte      _paddingByte;

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

	public FixedWidthValueFormat( String mode, String alignment, int length, String padding ) throws ParseException {
		this( FixedWidthFormatMode.parseMode(mode), FixedWidthFieldAlignment.parseAlignment(alignment), length, padding );
	}

	/**
	 * See {@link com.file.format.ValueFormat#formatValue(List)}.
	 * @param value The value to format composed of a collection of objects of Type T.
	 * @return A formatted list of the value's component objects.
	 */
	@Override
	public <T> List<T> formatValue( List<T> value ){
		if( value.size() >= this._fieldLength ){
			return this.truncateValue( value );
		}

		return this.alignValue( value );
	}

	/**
	 * Aligns the given value padded with this value format's padding object.
	 * @param value The value to align composed of type T.
	 * @return The value either left or right aligned padded with this value format's padding object.
	 */
	private <T> List<T> alignValue( List<T> value ){
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

	/**
	 * Cuts the value off at this format's field length.
	 * @param value The value to align composed of type T.
	 * @return The truncated value.
	 */
	private <T> List<T> truncateValue( List<T> value ){
		List<T> truncatedValue = value.subList( 0, this._fieldLength );
		return new ArrayList<>( truncatedValue );
	}

	/**
	 * Constructs padding value of given size.
	 * @param clazz The component type of the padding needed.
	 * @param valueSize The size of the padding.
	 * @return The padding value of the given size.
	 */
	private <T> List<T> constructPadding( Class<T> clazz, int valueSize ){
		Integer paddingSize = this._fieldLength - valueSize;
		T paddingObject = this.getPaddingObject( clazz );
		return Collections.nCopies( paddingSize, paddingObject );
	}

	/**
	 * Retrieves and validates the padding object of the given class.
	 * @param clazz The type of padding object desired.
	 * @return Padding object of the given class.
	 * @throws UnsupportedOperationException If the class of padding object requested is unsupported.
	 * @throws IllegalStateException If the class given does not match the class of this format's format mode.
	 */
	private <T> T getPaddingObject( Class<T> clazz ) throws UnsupportedOperationException, IllegalStateException {
		this._formatMode.validateFormatClass( clazz );

		if( clazz.equals( Character.class ) ){
			return clazz.cast( this._paddingCharacter );
		}

		if( clazz.equals( Byte.class ) ){
			return clazz.cast( this._paddingByte );
		}

		throw new UnsupportedOperationException( "Lists of type '" + clazz.getName() + "' is unsupported." );
	}

	/**
	 * @return true iff this value format is in byte mode.
	 */
	private boolean isInByteMode(){
		return this._formatMode == FixedWidthFormatMode.BYTE_MODE;
	}
}
