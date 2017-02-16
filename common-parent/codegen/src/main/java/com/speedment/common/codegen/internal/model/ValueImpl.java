/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.model.Value;

import java.util.Objects;

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

    @Override
	public Value<V> setValue(V value) {
		this.value = value;
		return this;
	}

    @Override
	public V getValue() {
		return value;
	}

	@Override
	public abstract ValueImpl<V> copy();

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.value);
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
        final ValueImpl<?> other = (ValueImpl<?>) obj;
        return Objects.equals(this.value, other.value);
    }


}