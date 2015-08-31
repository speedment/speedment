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
package com.speedment.internal.codegen.lang.models.implementation;

import com.speedment.internal.codegen.lang.models.AnnotationUsage;
import com.speedment.internal.codegen.lang.models.Type;
import com.speedment.internal.codegen.lang.models.Value;
import com.speedment.internal.codegen.util.Copier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 * This is the default implementation of the {@link AnnotationUsage} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link AnnotationUsage#of(com.speedment.codegen.lang.models.Type) } method to 
 * get an instance. In that way, you can layer change the implementing class 
 * without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    AnnotationUsage
 */
public final class AnnotationUsageImpl extends AnnotationUsageBase {

    /**
     * Initializes this annotation usage using a type.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link AnnotationUsage#of(com.speedment.codegen.lang.models.Type)} 
     * method!
     * 
     * @param type  the type
     */
	public AnnotationUsageImpl(Type type) {
        super(requireNonNull(type));
	}

    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype
     */
	protected AnnotationUsageImpl(AnnotationUsage prototype) {
		super(requireNonNull(prototype));
	}
	
	public final static class AnnotationUsageConst extends AnnotationUsageBase {
        
		public AnnotationUsageConst(Type type) { 
			super(type); 
		}

        /**
        * {@inheritDoc}
        */
		@Override
		public AnnotationUsage set(Value<?> val) {
			return copy().set(val);
		}
		
        /**
        * {@inheritDoc}
        */
		@Override
		public AnnotationUsage put(String key, Value<?> val) {
			return copy().put(key, val);
		}

        /**
        * {@inheritDoc}
        */
		@Override
		public AnnotationUsage set(Type type) {
			return copy().set(type);
		}
	}
}

abstract class AnnotationUsageBase implements AnnotationUsage {
	
    private Type type;
    private Value<?> value;
    private final List<Entry<String, Value<?>>> values;

    AnnotationUsageBase(Type type) {
        this.type	= type;
        this.value	= null;
        this.values = new ArrayList<>();
    }

    AnnotationUsageBase(AnnotationUsage prototype) {
        type   = prototype.getType();
        value  = prototype.getValue().map(Copier::copy).orElse(null);
        values = Copier.copy(prototype.getValues(), 
            e -> new AbstractMap.SimpleEntry<>(
                e.getKey(), e.getValue().copy()
            )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationUsage set(Value<?> val) {
        value = val;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationUsage put(String key, Value<?> val) {
        values.add(new AbstractMap.SimpleEntry<>(key, val));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Value<?>> getValue() {
        return Optional.ofNullable(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entry<String, Value<?>>> getValues() {
        return values;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationUsage set(Type type) {
        this.type = type;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Type getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnnotationUsageImpl copy() {
        return new AnnotationUsageImpl(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.type);
        hash = 79 * hash + Objects.hashCode(this.value);
        hash = 79 * hash + Objects.hashCode(this.values);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
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
}