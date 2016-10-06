/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.internal.model.value;

import com.speedment.common.codegen.internal.model.ValueImpl;
import com.speedment.common.codegen.model.value.EnumValue;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 *
 * @author Emil Forslund
 */
public final class EnumValueImpl extends ValueImpl<String> implements EnumValue {
	
	private Type type;
	
	public EnumValueImpl(Type type, String value) { 
		super (value); 
		this.type = type;
	}
	
	protected EnumValueImpl(EnumValue prototype) {
		this (prototype.getType(), prototype.getValue());
	}

	@Override
	public EnumValueImpl set(Type type) {
		this.type = type;
		return this;
	}

	@Override
	public Type getType() {
		return type;
	}
	
	@Override
	public EnumValueImpl copy() {
		return new EnumValueImpl(this);
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EnumValue other = (EnumValue) obj;
        return Objects.equals(this.type, other.getType());
    }


}