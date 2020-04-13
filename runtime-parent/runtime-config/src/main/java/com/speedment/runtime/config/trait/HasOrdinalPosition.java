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

import java.util.Map;

import static com.speedment.runtime.config.util.TraitUtil.viewOf;

/**
 * Trait for {@link Document} implementations that implement the 
 * {@link #getOrdinalPosition()} method.
 * 
 * @author   Emil Forslund
 * @version  2.3.0
 */
public interface HasOrdinalPosition extends Document {

    /**
     * Returns the position to use when ordering this node.
     * 
     * @return the ordinal position.
     */
    default int getOrdinalPosition() {
        return getAsInt(HasOrdinalPositionUtil.ORDINAL_POSITION).orElse(0);
    }
    
    /**
     * Returns a wrapper of the specified document that implements the 
     * {@link HasOrdinalPosition} trait. If the specified document already 
     * implements the trait, it is returned unwrapped.
     * 
     * @param document  the document to wrap
     * @return          the wrapper
     */
    static HasOrdinalPosition of(Document document) {
        return viewOf(document, HasOrdinalPosition.class, HasOrdinalPositionView::new);
    }
}

/**
 * A wrapper class that makes sure that a given {@link Document} implements the
 * {@link HasOrdinalPosition} trait.
 * 
 * @author  Emil Forslund
 * @since   2.3
 */
class HasOrdinalPositionView extends AbstractTraitView implements HasOrdinalPosition {

    /**
     * Constructs a new ordinal position view of with the specified parent and 
     * data.
     * 
     * @param parent         the parent of the wrapped document
     * @param data           the data of the wrapped document
     * @param mainInterface  the main interface of the wrapped document
     */
    HasOrdinalPositionView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data, mainInterface);
    }
}