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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.internal.codegen.lang.models.implementation;

import com.speedment.internal.codegen.lang.models.Value;
import java.util.Objects;
import java.util.Optional;

/**
 * The default implementation of the {@link Value} interface.
 * 
 * @author Emil Forslund
 * @param <V> The extending type
 */
public abstract class ValueImpl<V> implements Value<V> {
    
	private V value;
	
    /**
     * Initializes this value.
     * 
     * @param val  the inner value
     */
	public ValueImpl(V val) {
		value = val;
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public Value<V> setValue(V value) {
		this.value = value;
		return this;
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public V getValue() {
		return value;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public abstract ValueImpl<V> copy();

    /**
     * {@inheritDoc}
     */
	@Override
	public int hashCode() {
		return value.hashCode();
	}

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
            .filter(other -> getClass().isAssignableFrom(other.getClass()))
            .map(other -> (ValueImpl<V>) other)
            .filter(other -> Objects.equals(value, other.value))
            .isPresent();
	}
}