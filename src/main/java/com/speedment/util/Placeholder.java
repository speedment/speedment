package com.speedment.util;

import java.util.concurrent.Callable;

/**
 *
 * @author Duncan
 */
public class Placeholder implements CharSequence {
	private final Callable<CharSequence> callback;
	private CharSequence data = null;
	
	public Placeholder(Callable<CharSequence> callback) {
		this.callback = callback;
	}

	@Override
	public int length() {
		return data().length();
	}

	@Override
	public char charAt(int index) {
		return data().charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return data().subSequence(start, end);
	}

	@Override
	public String toString() {
		return data().toString();
	}
	
	private CharSequence data() {
		try {
			if (data == null) {
				data = callback.call();
			}
			return data;
		} catch (Exception ex) {
			// TODO: Handle exceptions caused by callback code.
			System.exit(-1);
			return null;
		}
	}
}
