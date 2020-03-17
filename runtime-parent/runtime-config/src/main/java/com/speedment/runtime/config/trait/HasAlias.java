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
 * Trait for {@link Document} implementations that implement the 
 * {@link #getAlias()} method. If a {@code Document} implements this trait, it
 * is also expected to implement the {@link HasName} trait.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
public interface HasAlias extends Document, HasName {

    /**
     * Returns the alias of the specified document. The alias is an optional
     * string value located under the {@link HasAliasUtil#ALIAS} key.
     * 
     * @return  the alias or an empty {@code Optional} if none was specified
     */
    default Optional<String> getAlias() {
        return getAsString(HasAliasUtil.ALIAS);
    }
    
    /**
     * Returns the java name of this {@link Document}. If an alias is specified 
     * by {@link #getAlias()}, it will be returned, but if no alias exist the 
     * database name returned by {@link #getName()} will be used.
     * 
     * @return  the java name
     */
    default String getJavaName() {
        return getAlias().orElse(getName());
    }
    
    /**
     * Returns a wrapper of the specified document that implements the 
     * {@link HasAlias} trait. If the specified document already implements the
     * trait, it is returned unwrapped.
     * 
     * @param document  the document to wrap
     * @return          the wrapper
     */
    static HasAlias of(Document document) {
        return viewOf(document, HasAlias.class, HasAliasView::new);
    }
}

/**
 * A wrapper class that makes sure that a given {@link Document} implements the
 * {@link HasAlias} trait.
 * 
 * @author  Emil Forslund
 * @since   2.3
 */
class HasAliasView extends AbstractTraitView implements HasAlias {

    /**
     * Constructs a new alias view of with the specified parent and data.
     * 
     * @param parent         the parent of the wrapped document
     * @param data           the data of the wrapped document
     * @param mainInterface  the main interface of the wrapped document
     */
    HasAliasView(Document parent, Map<String, Object> data, Class<? extends Document> mainInterface) {
        super(parent, data, mainInterface);
    }
}