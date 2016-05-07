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
package com.speedment.internal.codegen.model;

import com.speedment.codegen.model.Generic;
import com.speedment.codegen.model.Type;
import com.speedment.internal.codegen.util.Copier;
import static com.speedment.util.NullUtil.requireNonNullElements;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static com.speedment.util.NullUtil.requireNonNullElements;

/**
 * This is the default implementation of the {@link Generic} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link Generic#of()} method to get an instance. In that way, 
 * you can layer change the implementing class without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    Generic
 */
public final class GenericImpl implements Generic {
	
	private String lowerBound;
	private final List<Type> upperBounds;

	private BoundType type = BoundType.EXTENDS;
	
    /**
     * Initializes this generic.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Generic#of()} method!
     */
	public GenericImpl() {
		lowerBound  = null;
		upperBounds = new ArrayList<>();
	}

    /**
     * Initializes this generic using a lower bound.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Generic#of()} method!
     * 
     * @param lowerBound   the lower bound
     */
	public GenericImpl(String lowerBound) {
		this (lowerBound, new Type[0]);
	}
	
    /**
     * Initializes this generic using a number of upper bounds.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Generic#of()} method!
     * 
     * @param upperBounds  the upper bounds
     */
	public GenericImpl(Type... upperBounds) {
		this (null, requireNonNullElements(upperBounds));
	}
	
    /**
     * Initializes this generic using a lower bound and a number of upper bounds.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Generic#of()} method!
     * 
     * @param lowerBound   the lower bound
     * @param upperBounds  the upper bounds
     */
	public GenericImpl(String lowerBound, Type... upperBounds) {
        requireNonNullElements(upperBounds);
        // lowerBound nullable
		this.lowerBound  = lowerBound;
		this.upperBounds = asList(upperBounds);
	}
	
    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype
     */
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.lowerBound);
        hash = 89 * hash + Objects.hashCode(this.upperBounds);
        hash = 89 * hash + Objects.hashCode(this.type);
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
        final GenericImpl other = (GenericImpl) obj;
        if (!Objects.equals(this.lowerBound, other.lowerBound)) {
            return false;
        }
        if (!Objects.equals(this.upperBounds, other.upperBounds)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }


}