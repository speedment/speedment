/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.internal.java.view.trait;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.trait.HasFields;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static java.util.stream.Collectors.toList;

/**
 * A trait with the functionality to render models with the trait 
 * {@link HasFields}.
 * 
 * @author     Emil Forslund
 * @param <M>  The model type
 * @see        Transform
 */
public interface HasFieldsView<M extends HasFields<M>> extends Transform<M, String> {
    
    /**
     * Render the fields-part of the model using the 
     * {@link #fieldSeparator(HasFields)} method to separate the fields and 
     * the {@link #fieldPrefix()}- and {@link #fieldSuffix()}-methods on each 
     * field.
     * 
     * @param gen    the generator
     * @param model  the model
     * @return       the generated code
     */
    default String renderFields(Generator gen, M model) {
        final List<String> rendered;

        if (useTripleDot() && !model.getFields().isEmpty()) {
            final String last = gen.on(wrapField(new LinkedList<>(model.getFields()).getLast())).get();
            if (last.contains("[] ")) {
                rendered = Stream.concat(
                    model.getFields().stream()
                        .limit(model.getFields().size() - 1)
                        .map(this::wrapField)
                        .map(gen::on)
                        .map(Optional::get),
                    Stream.of(last.replaceFirst("\\[\\] ", "... "))
                ).collect(toList());
            } else {
                rendered = model.getFields().stream()
                    .map(this::wrapField)
                    .map(gen::on)
                    .map(Optional::get)
                    .collect(toList());
            }
        } else {
            rendered = model.getFields().stream()
                .map(this::wrapField)
                .map(gen::on)
                .map(Optional::get)
                .collect(toList());
        }

        return rendered.stream()
            .collect(joinIfNotEmpty(
                fieldSuffix() + fieldSeparator(model) + fieldPrefix(), 
                fieldPrefix(), 
                fieldSuffix()
            ));
    }
    
    /**
     * The separator string used when joining fields.
     * 
     * @param model  the model that is being rendered
     * @return       the field separator
     */
    String fieldSeparator(M model);
    
    /**
     * A text to be inserted before each field.
     * 
     * @return  the field prefix
     */
    default String fieldPrefix() {
        return "";
    }
    
    /**
     * A text to be inserted after each field.
     * 
     * @return  the field suffix
     */
    default String fieldSuffix() {
        return "";
    }

    /**
     * If this method returns {@code true}, then the last field in the list
     * should be rendered with triple dot style {@code ...} if it is an array.
     * If this method returns {@code false}, then all fields are rendered as
     * usual.
     * <p>
     * The default value for this method is {@code false}.
     *
     * @return  if triple dots should be used or not
     */
    default boolean useTripleDot() {
        return false;
    }

    /**
     * This method is called for every field being generated to give the
     * implementing class a chance to change the implementation before rendering.
     * There must be a {@link Transform} installed in the generator that can
     * handle the output of this method.
     * <p>
     * The default behaviour of this method is to return the input without any
     * modifications.
     *
     * @param field  the field to wrap
     * @return       a model derived from the field
     */
    default Object wrapField(Field field) {
        return field;
    }
}