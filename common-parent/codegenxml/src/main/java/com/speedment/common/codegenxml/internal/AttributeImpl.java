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
package com.speedment.common.codegenxml.internal;

import com.speedment.common.codegenxml.Attribute;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class AttributeImpl implements Attribute {

    private String name;
    private String value;
    private boolean escape;

    public AttributeImpl(String name) {
        this.name = requireNonNull(name);
        this.escape = true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public Attribute setName(String name) {
        this.name = requireNonNull(name);
        return this;
    }

    @Override
    public Attribute setValue(String value) {
        this.value = value;
        return this;
    }
    
    @Override
    public Attribute setEscape(boolean escape) {
        this.escape = escape;
        return this;
    }

    @Override
    public boolean isEscape() {
        return escape;
    }
}