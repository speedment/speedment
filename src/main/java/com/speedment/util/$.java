package com.speedment.util;

/**
 *
 * @author Duncan
 */
public class $ implements CharSequence {

	private static final CharSequence[] EMPTY = new CharSequence[0];
	
	private final StringBuilder str;

	public $() {
		this(EMPTY);
	}

	public $(CharSequence... objects) {
		str = new StringBuilder();
		$(objects);
	}

	public final $ $(Object... objects) {
		for (Object object : objects) {
			str.append(object);
		}
		return this;
	}

	public static $ $(CharSequence... o) {
		return new $(o);
	}

	@Override
	public int length() {
		return str.length();
	}

	@Override
	public char charAt(int index) {
		return str.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return str.subSequence(start, end);
	}

	@Override
	public String toString() {
		return str.toString();
	}
}
