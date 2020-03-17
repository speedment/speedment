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
import com.speedment.common.codegenxml.Element;
import com.speedment.common.codegenxml.TagElement;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Per Minborg
 */
public class TagElementImpl implements TagElement {

    private String name;
    private final List<Attribute> attributes;
    private final List<Element> elements;

    public TagElementImpl(String name) {
        this.name = name;
        this.attributes = new ArrayList<>();
        this.elements = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public TagElement setName(String name) {
        this.name = requireNonNull(name);
        return this;
    }

    @Override
    public List<Attribute> attributes() {
        return attributes;
    }

    @Override
    public List<Element> elements() {
        return elements;
    }

    @Override
    public String toString() {
        return "<" + name + attributes().stream()
            .map(a -> ".")
            .collect(joining("", " ", "")) + 
            ">...</" + name + ">";
    }
}