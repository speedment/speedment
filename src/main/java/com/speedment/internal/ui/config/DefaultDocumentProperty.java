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
package com.speedment.internal.ui.config;

import com.speedment.Speedment;
import static com.speedment.component.DocumentPropertyComponent.concat;
import com.speedment.config.Document;
import java.util.Optional;
import java.util.stream.Stream;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class DefaultDocumentProperty extends 
    AbstractDocumentProperty<DefaultDocumentProperty> {
    
    private final AbstractDocumentProperty parent;
    private final String key;
    
    public DefaultDocumentProperty(AbstractDocumentProperty parent, String key) {
        this.parent = parent; // Can be null.
        this.key    = key;    // Can be null.
    }
    
    @Override
    protected String[] keyPathEndingWith(String key) {
        if (parent == null) {
            if (key == null) {
                return new String[0];
            } else {
                return new String[] {key};
            }
        } else {
            return concat(parent.keyPathEndingWith(this.key), key);
        }
    }

    @Override
    public Optional<? extends Document> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public Stream<PropertySheet.Item> getUiVisibleProperties(Speedment speedment) {
        return Stream.empty();
    }

    @Override
    public Class<? extends Document> mainInterface() {
        return Document.class;
    }
}