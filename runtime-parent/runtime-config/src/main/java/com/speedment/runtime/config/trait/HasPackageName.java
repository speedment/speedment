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
import java.util.Optional;

import static com.speedment.runtime.config.util.TraitUtil.viewOf;

/**
 *
 * @author Simon
 */
public interface HasPackageName extends Document{

    /**
     * Returns the name of the generated package where this document will be
     * located.
     *
     * @return the name of the generated package or {@code empty}
     */
    default Optional<String> getPackageName() {
        return getAsString(HasPackageNameUtil.PACKAGE_NAME);
    }
    
        /**
     * Returns a wrapper of the specified document that implements the 
     * {@link HasPackageName} trait. If the specified document already implements the
     * trait, it is returned unwrapped.
     * 
     * @param document  the document to wrap
     * @return          the wrapper
     */
    static HasPackageName of(Document document) {
        return viewOf(document, HasPackageName.class, HasPackageNameView::new);
    }
}

/**
 * A wrapper class that makes sure that a given {@link Document} implements the
 * {@link HasEnabled} trait.
 * 
 * @author  Emil Forslund
 * @since   2.3
 */
class HasPackageNameView extends AbstractTraitView implements HasPackageName {

    /**
     * Constructs a new enabled view of with the specified parent and data.
     * 
     * @param parent         the parent of the wrapped document
     * @param data           the data of the wrapped document
     * @param mainInterface  the main interface of the wrapped document
     */
    HasPackageNameView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data, mainInterface);
    }
}
