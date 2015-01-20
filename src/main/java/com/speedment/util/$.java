package com.speedment.util;

/**
 *
 * @author Duncan
 */
public class $ implements CharSequence {

	private static final CharSequence[] EMPTY = new CharSequence[0];

	private final StringBuilder str;

	private $() {
		this(EMPTY);
	}

	public $(CharSequence... objects) {
		str = new StringBuilder();
		$(objects);
	}

	public final $ $(Object firstObject) {
		str.append(firstObject);
		return this;
	}

	public final $ $(Object firstObject, Object... theRest) {
		$(firstObject);
		for (Object object : theRest) {
			$(object);
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
