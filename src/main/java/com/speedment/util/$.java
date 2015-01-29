/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.util;

import java.util.Optional;

/**
 *
 * @author Duncan
 */
public class $ implements CharSequence, Comparable<CharSequence> {

	private final StringBuilder str;

	public $() {
		this("");
	}
	
	public $(final CharSequence... objects) {
		str = new StringBuilder();
		$(objects);
	}
	
	public $(final Optional<CharSequence>... objects) {
		str = new StringBuilder();
		$(objects);
	}
	
	public final $ $(final CharSequence... objects) {
		for (final CharSequence object : objects) {
			str.append(object);
		}
		return this;
	}
	
	public final $ $(final Optional<CharSequence>... objects) {
		for (final Optional<CharSequence> object : objects) {
			if (object.isPresent()) {
				str.append(object);
			}
		}
		return this;
	}

	@Override
	public int length() {
		return str.length();
	}

	@Override
	public char charAt(final int index) {
		return str.charAt(index);
	}

	@Override
	public CharSequence subSequence(final int start, final int end) {
		return str.subSequence(start, end);
	}

	@Override
	public String toString() {
		return str.toString();
	}

	@Override
	public int compareTo(CharSequence o) {
		return str.toString().compareTo(o.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			return compareTo(obj.toString()) == 0;
		}
	}
}