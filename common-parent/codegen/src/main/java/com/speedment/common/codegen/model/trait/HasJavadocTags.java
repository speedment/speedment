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
package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.constant.DefaultJavadocTag;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.JavadocTag;
import com.speedment.common.codegen.util.Formatting;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A trait for models that contain {@link JavadocTag} components.
 *
 * @param <T>  the extending type
 *
 * @author Emil Forslund
 * @since  2.0
 */
public interface HasJavadocTags<T extends HasJavadocTags<T>> extends HasImports<T> {
    
    /**
     * Adds the specified {@link JavadocTag} to this model.
     * 
     * @param tag  the new child
     * @return     a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(final JavadocTag tag) {
        getTags().add(tag);
        return (T) this;
    }

    /**
     * Adds an {@link DefaultJavadocTag#AUTHOR}-tag with the specified value to
     * this javadoc.
     *
     * @param name  the name of the author
     * @return      a reference to this
     */
    default T author(final String name) {
        return add(DefaultJavadocTag.AUTHOR.setValue(name));
    }

    /**
     * Adds an {@link DefaultJavadocTag#RETURN}-tag with the specified value to
     * this javadoc.
     *
     * @param description  description for the {@code return} tag
     * @return             a reference to this
     */
    default T returns(final String description) {
        return add(DefaultJavadocTag.RETURN.setValue(description));
    }

    /**
     * Adds an {@link DefaultJavadocTag#SINCE}-tag with the specified value to
     * this javadoc.
     *
     * @param version  version for the {@code since} tag
     * @return         a reference to this
     */
    default T since(final String version) {
        return add(DefaultJavadocTag.SINCE.setValue(version));
    }

    /**
     * Adds an {@link DefaultJavadocTag#SEE}-tag that references the specified
     * Java type.
     *
     * @param type  the type to reference in the {@code see}-tag
     * @return      a reference to this
     */
    default T see(final Type type) {
        return add(DefaultJavadocTag.SEE.setValue("{@link " +
                Formatting.shortName(type.getTypeName()) + "}"))
            .add(Import.of(type));
    }

    /**
     * Adds an {@link DefaultJavadocTag#PARAM}-tag with the specified value to
     * this javadoc.
     *
     * @param name  the name of the param
     * @param description the description (text) to use
     * @return      a reference to this
     */
    default T param(final String name, String description) {
        return add(DefaultJavadocTag.PARAM.setValue(name).setText(description));
    }
    
    /**
     * Returns a list of all documentation tags of this model.
     * <p>
     * The list returned must be mutable for changes!
     * 
     * @return  the javadoc tags
     */
    List<JavadocTag> getTags();
}