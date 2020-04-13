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
package com.speedment.runtime.config.trait;

import com.speedment.runtime.config.Document;
import com.speedment.runtime.config.exception.SpeedmentConfigException;
import static com.speedment.runtime.config.util.TraitUtil.viewOf;
import java.util.Map;

/**
 * Trait for {@link Document} implementations that implement the
 * {@link #getId()} method. If a {@code Document} implements this trait, it is
 * also expected to implement the {@link HasName} trait.
 *
 * @author Per Minborg
 * @version 3.0.5
 */
public interface HasId extends Document, HasName {

    /**
     * Gets the id of this Document. If no id is present, this method defaults
     * to the name of the Document as described in the {@link HasName}
     * interface.
     *
     * @return the id of this Document or else, returns the name of this
     * Document
     * @throws SpeedmentConfigException if no id is specified and no name is
     * available
     */
    default String getId() {
        return getAsString(HasIdUtil.ID).orElse(getName());
    }

    /**
     * Returns a wrapper of the specified document that implements the
     * {@link HasId} trait. If the specified document already implements the
     * trait, it is returned unwrapped.
     *
     * @param document the document to wrap
     * @return the wrapper
     */
    static HasId of(Document document) {
        return viewOf(document, HasId.class, HasIdView::new);
    }
}

/**
 * A wrapper class that makes sure that a given {@link Document} implements the
 * {@link HasId} trait.
 *
 * @author Emil Forslund
 * @since 3.0.5
 */
class HasIdView extends AbstractTraitView implements HasId {

    /**
     * Constructs a new name view of with the specified parent and data.
     *
     * @param parent the parent of the wrapped document
     * @param data the data of the wrapped document
     * @param mainInterface the main interface of the wrapped document
     */
    HasIdView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data, mainInterface);
    }
}
