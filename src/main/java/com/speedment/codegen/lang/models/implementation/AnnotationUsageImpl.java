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
package com.speedment.codegen.lang.models.implementation;

import com.speedment.codegen.lang.models.AnnotationUsage;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.lang.models.Value;
import com.speedment.util.Copier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public class AnnotationUsageImpl implements AnnotationUsage {
	
	private Type type;
	private Value<?> value;
	private final List<Entry<String, Value<?>>> values;
	
	public AnnotationUsageImpl(Type type) {
		this.type	= type;
		this.value	= null;
		this.values = new ArrayList<>();
	}
    
    public AnnotationUsageImpl(Type type, Value<?> value) {
		this.type	= type;
		this.value	= value;
		this.values = new ArrayList<>();
	}
	
	protected AnnotationUsageImpl(AnnotationUsage prototype) {
		type   = prototype.getType();
		value  = prototype.getValue().map(Copier::copy).orElse(null);
		values = Copier.copy(prototype.getValues(), 
			e -> new AbstractMap.SimpleEntry<>(
				e.getKey(), e.getValue().copy()
			)
		);
	}
	
	@Override
	public AnnotationUsage set(Value<?> val) {
		value = val;
		return this;
	}
	
    @Override
	public AnnotationUsage put(String key, Value<?> val) {
		values.add(new AbstractMap.SimpleEntry<>(key, val));
		return this;
	}
	
	@Override
	public Optional<Value<?>> getValue() {
		return Optional.ofNullable(value);
	}
	
    @Override
	public List<Entry<String, Value<?>>> getValues() {
		return values;
	}

	@Override
	public AnnotationUsage set(Type type) {
		this.type = type;
		return this;
	}

	@Override
	public Type getType() {
		return type;
	}
    
    @Override
	public AnnotationUsageImpl copy() {
		return new AnnotationUsageImpl(this);
	}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.type);
        hash = 79 * hash + Objects.hashCode(this.value);
        hash = 79 * hash + Objects.hashCode(this.values);
        return hash;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> AnnotationUsage.class.isAssignableFrom(obj.getClass()))
            .map(o -> (AnnotationUsage) o)
            .filter(o -> Objects.equals(getType(), o.getType()))
            .filter(o -> Objects.equals(getValue(), o.getValue()))
            .filter(o -> Objects.equals(getValues(), o.getValues()))
            .isPresent();
    }
	
	public final static class AnnotationUsageConst extends AnnotationUsageImpl {
		public AnnotationUsageConst(Type type) { 
			super(type); 
		}
        
        public AnnotationUsageConst(Type type, Value<?> value) { 
			super(type, value); 
		}

		@Override
		public AnnotationUsage set(Value<?> val) {
			return copy().set(val);
		}
		
		@Override
		public AnnotationUsage put(String key, Value<?> val) {
			return copy().put(key, val);
		}

		@Override
		public AnnotationUsage set(Type type) {
			return copy().set(type);
		}
	}
}
