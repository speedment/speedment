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
package com.speedment.common.codegen.controller;

import com.speedment.common.codegen.model.trait.HasClasses;
import com.speedment.common.codegen.model.trait.HasCode;
import com.speedment.common.codegen.model.trait.HasConstructors;
import com.speedment.common.codegen.model.trait.HasFields;
import com.speedment.common.codegen.model.trait.HasInitializers;
import com.speedment.common.codegen.model.trait.HasJavadoc;
import com.speedment.common.codegen.model.trait.HasMethods;
import com.speedment.common.codegen.model.value.ReferenceValue;
import com.speedment.common.codegen.util.Formatting;
import static com.speedment.common.codegen.util.Formatting.nl;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @param <T>  the type being operated upon
 * 
 * @author Emil Forslund
 * @since  2.4.3
 */
public final class AlignTabs<T> implements Consumer<T> {

    @Override
    public void accept(T model) {
        if (model instanceof HasCode) {
            @SuppressWarnings("unchecked")
            final HasCode<?> casted = (HasCode<?>) model;
            Formatting.alignTabs(casted.getCode());
        }
        
        if (model instanceof HasClasses) {
            @SuppressWarnings("unchecked")
            final HasClasses<?> casted = (HasClasses<?>) model;
            casted.getClasses().forEach(c -> c.call(new AlignTabs<>()));
        }
        
        if (model instanceof HasMethods) {
            @SuppressWarnings("unchecked")
            final HasMethods<?> casted = (HasMethods<?>) model;
            casted.getMethods().forEach(c -> c.call(new AlignTabs<>()));
        }
        
        if (model instanceof HasConstructors) {
            @SuppressWarnings("unchecked")
            final HasConstructors<?> casted = (HasConstructors<?>) model;
            casted.getConstructors().forEach(c -> c.call(new AlignTabs<>()));
        }
        
        if (model instanceof HasInitializers) {
            @SuppressWarnings("unchecked")
            final HasInitializers<?> casted = (HasInitializers<?>) model;
            casted.getInitializers().forEach(c -> c.call(new AlignTabs<>()));
        }
        
        if (model instanceof HasFields) {
            @SuppressWarnings("unchecked")
            final HasFields<?> casted = (HasFields<?>) model;
            
            alignTabs(casted.getFields(), field -> field.getValue()
                .filter(ReferenceValue.class::isInstance)
                .map(ReferenceValue.class::cast)
                .map(ReferenceValue::getValue)
                .orElse(null),
                (field, value) -> ((ReferenceValue) field.getValue().get())
                    .setValue(value)
            );
        }
        
        if (model instanceof HasJavadoc) {
            @SuppressWarnings("unchecked")
            final HasJavadoc<?> casted = (HasJavadoc<?>) model;
            casted.getJavadoc().ifPresent(javadoc -> {
                final List<String> rows = Stream.of(javadoc.getText()
                    .split(Formatting.nl())
                ).collect(toList());
                
                Formatting.alignTabs(rows);
                
                javadoc.setText(rows.stream().collect(joining(nl())));
            });
        }
    }
    
    private static <T> void alignTabs(
            List<T> models, 
            Function<T, String> getRow, 
            BiConsumer<T, String> setRow) {
        final AtomicInteger maxIndex = new AtomicInteger(-1);
        
        while (true) {
            maxIndex.set(-1);
            
            models.stream().forEachOrdered(model -> {
                final String row = getRow.apply(model);
                if (row != null) {
                    final int index  = row.indexOf('\t');

                    if (index > maxIndex.get()) {
                        maxIndex.set(index);
                    }
                }
            });
            
            if (maxIndex.get() > -1) {
                for (int i = 0; i < models.size(); i++) {
                    final T model = models.get(i);
                    final String row = getRow.apply(model);
                    if (row != null) {
                        final int index = row.indexOf('\t');
                        if (index > -1) {
                            setRow.accept(model, 
                                row.replaceFirst("\t", Formatting.repeat(
                                    " ", 
                                    maxIndex.get() - index
                                ))
                            );
                        }
                    }
                }
            } else {
                break;
            }
        }
    }
}