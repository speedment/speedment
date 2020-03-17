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
package com.speedment.tool.config;

import com.speedment.runtime.config.Document;
import com.speedment.tool.config.trait.HasExpandedProperty;
import com.speedment.tool.config.trait.HasNameProperty;

import java.util.List;
import java.util.Optional;

import static com.speedment.tool.config.internal.util.ImmutableListUtil.concat;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class DefaultDocumentProperty extends 
    AbstractDocumentProperty<DefaultDocumentProperty> implements HasExpandedProperty, HasNameProperty {
    
    private final AbstractDocumentProperty<?> parent;
    private final String key;
    
    public DefaultDocumentProperty(AbstractDocumentProperty<?> parent, String key) {
        this.parent = parent; // Can be null.
        this.key    = key;    // Can be null.
    }
    
    @Override
    protected List<String> keyPathEndingWith(String key) {
        if (parent == null) {
            if (key == null) {
                return emptyList();
            } else {
                return singletonList(key);
            }
        } else {
            final List<String> path = parent.keyPathEndingWith(this.key);
            return concat(path, key);
        }
    }

    @Override
    public Optional<? extends Document> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public Class<? extends Document> mainInterface() {
        return Document.class;
    }
}