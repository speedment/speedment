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
package com.speedment.codegen.model.dependency_;

import com.speedment.codegen.model.CodeModel;
import java.util.Objects;

/**
 *
 * @author Duncan
 */
public class Dependency_ implements CodeModel {

    private CharSequence source;
    private boolean isStatic;

    public CharSequence getSource() {
        return source;
    }

    public Dependency_ setSource(final CharSequence source) {
        this.source = source;
        return this;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public Dependency_ setStatic(final boolean isStatic) {
        this.isStatic = isStatic;
        return this;
    }

    @Override
    public Type getModelType() {
        return Type.DEPENDENCY;
    }

    public static Dependency_ of(Class<?> javaClass) {
        return new Dependency_().setSource(javaClass.getName());
    }
	
	public static Dependency_ of(CharSequence className) {
        return new Dependency_().setSource(className);
    }

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 11 * hash + Objects.hashCode(this.source);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Dependency_ other = (Dependency_) obj;
		if (!Objects.equals(this.source, other.source)) {
			return false;
		}
		return true;
	}
}
