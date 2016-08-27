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
package com.speedment.runtime.config.trait;

import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.util.document.TraitUtil.AbstractTraitView;

import java.util.Map;
import java.util.Optional;

import static com.speedment.runtime.internal.util.document.TraitUtil.viewOf;

/**
 * Trait for {@link Document} implementations that implement the 
 * {@link #getName()} method. If a {@code Document} implements this trait, it
 * is also expected to implement the {@link HasMainInterface} trait.
 * 
 * @author   Emil Forslund
 * @version  2.3.0
 */
@Api(version = "3.0")
public interface HasName extends Document, HasMainInterface {

    /**
     * The key of the {@code name} property.
     */
    final String NAME = "name";

    /**
     * Gets the name of this Document. If no name is present, this method might
     * create a name if the parent implements the {@link HasChildren} interface.
     * If it does not and this document is unnamed, an
     * {@link SpeedmentException} is called.
     *
     * @return the name of this Document
     * @throws SpeedmentException if no name is specified and the parent can't
     * generate one
     */
    default String getName() throws SpeedmentException {
        final Optional<String> name = getAsString(NAME);

        if (name.isPresent()) {
            return name.get();

        } else if (getParent()
                .filter(HasChildren.class::isInstance)
                .isPresent()) {

            final String defaultName = getParent()
                    .map(HasChildren.class::cast)
                    .map(parent -> parent.defaultNameFor(this))
                    .get();

            getData().put(NAME, defaultName);
            return defaultName;
        } else if (this instanceof Project) {
            return Project.class.getSimpleName();
        } else {
            throw new SpeedmentException(
                "A name is required for node of type '" + getClass().getSimpleName() + "'."
            );
        }
    }

    /**
     * Returns a wrapper of the specified document that implements the 
     * {@link HasName} trait. If the specified document already implements the
     * trait, it is returned unwrapped.
     * 
     * @param document  the document to wrap
     * @return          the wrapper
     */
    static HasName of(Document document) {
        return viewOf(document, HasName.class, HasNameView::new);
    }
}

/**
 * A wrapper class that makes sure that a given {@link Document} implements the
 * {@link HasName} trait.
 * 
 * @author  Emil Forslund
 * @since   2.3
 */
class HasNameView extends AbstractTraitView implements HasName {

    /**
     * Constructs a new name view of with the specified parent and data.
     * 
     * @param parent         the parent of the wrapped document
     * @param data           the data of the wrapped document
     * @param mainInterface  the main interface of the wrapped document
     */
    HasNameView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data, mainInterface);
    }
}
