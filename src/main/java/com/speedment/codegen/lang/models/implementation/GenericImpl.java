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

import com.speedment.codegen.lang.models.Generic;
import com.speedment.codegen.lang.models.Type;
import com.speedment.codegen.util.Copier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This is the default implementation of the {@link Generic} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link Generic#of()} method to get an instance. In that way, 
 * you can layer change the implementing class without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    Generic
 */
public class GenericImpl implements Generic {
	
	private String lowerBound;
	private final List<Type> upperBounds;

	private BoundType type = BoundType.EXTENDS;
	
	public GenericImpl() {
		lowerBound  = null;
		upperBounds = new ArrayList<>();
	}

	public GenericImpl(String lowerBound) {
		this(lowerBound, new Type[0]);
	}
	
	public GenericImpl(Type... upperBounds) {
		this(null, upperBounds);
	}
	
	public GenericImpl(String lowerBound, Type... upperBounds) {
		this.lowerBound = lowerBound;
		this.upperBounds = Arrays.asList(upperBounds);
	}
	
	protected GenericImpl(Generic prototype) {
		lowerBound  = prototype.getLowerBound().orElse(null);
		upperBounds = Copier.copy(prototype.getUpperBounds());
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public Generic setLowerBound(String lowerBound) {
		this.lowerBound = lowerBound;
		return this;
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public Optional<String> getLowerBound() {
		return Optional.ofNullable(lowerBound);
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
	public Generic setBoundType(BoundType type) {
		this.type = type;
		return this;
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
	public BoundType getBoundType() {
		return type;
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public List<Type> getUpperBounds() {
		return upperBounds;
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
	public Optional<Type> asType() {
		return (lowerBound == null ? 
            Optional.empty() :
            Optional.of(new TypeImpl(lowerBound))
        );
	}
	
    /**
     * {@inheritDoc}
     */
	@Override
	public GenericImpl copy() {
		return new GenericImpl(this);
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.lowerBound);
        hash = 23 * hash + Objects.hashCode(this.upperBounds);
        hash = 23 * hash + Objects.hashCode(this.type);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(o -> Generic.class.isAssignableFrom(o.getClass()))
            .map(o -> (Generic) o)
            .filter(o -> Objects.equals(getLowerBound(), o.getLowerBound()))
            .filter(o -> Objects.equals(getUpperBounds(), o.getUpperBounds()))
            .filter(o -> Objects.equals(getBoundType(), o.getBoundType()))
            .isPresent();
    }
}