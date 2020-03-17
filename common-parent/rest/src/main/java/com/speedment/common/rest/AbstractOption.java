/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.rest;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Abstract base class for different types of {@link Option} implementations.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
abstract class AbstractOption implements Option {
    
    private final String key;
    private final String value;
    
    AbstractOption(String key, String value) {
        this.key   = requireNonNull(key);
        this.value = requireNonNull(value);
    }

    @Override
    public final String getKey() {
        return key;
    }

    @Override
    public final String getValue() {
        return value;
    }

    @Override
    public final int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.key);
        hash = 53 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        else if (obj == null) return false;
        else if (getClass() != obj.getClass()) return false;
        
        final Option other = (Option) obj;
        return Objects.equals(this.key, other.getKey())
            && Objects.equals(this.value, other.getValue());
    }

    @Override
    public String toString() {
        return key + '=' + value;
    }
}