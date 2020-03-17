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
package com.speedment.runtime.field.internal.method;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.exception.SpeedmentFieldException;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.field.trait.HasFinder;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @param <ENTITY>     the source entity
 * @param <FK_ENTITY>  the target entity
 * @param <V>          the column type
 * @param <SOURCE>     the source field type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public class FindFromReference<
    ENTITY,
    FK_ENTITY,
    V extends Comparable<? super V>,
    SOURCE extends Field<ENTITY> & HasComparableOperators<ENTITY, V> & HasFinder<ENTITY, FK_ENTITY>>
extends AbstractFindFrom<ENTITY, FK_ENTITY, V, SOURCE, HasComparableOperators<FK_ENTITY, V>> {

    public FindFromReference(SOURCE source, HasComparableOperators<FK_ENTITY, V> target, TableIdentifier<FK_ENTITY> identifier, Supplier<Stream<FK_ENTITY>> streamSupplier) {
        super(source, target, identifier, streamSupplier);
    }

    @Override
    public FK_ENTITY apply(ENTITY entity) {
        @SuppressWarnings("unchecked")
        final V value = (V) getSourceField().getter().apply(entity);
        if (value == null) {
            return null;
        } else {
            return stream()
                .filter(getTargetField().equal(value))
                .findAny()
                .orElseThrow(() -> new SpeedmentFieldException(
                    "Error! Could not find any entities in table '" + 
                    getTableIdentifier() + 
                    "' with '" + getTargetField().identifier().getColumnId() +
                    "' = '" + value + "'."
                ));
        }
    }
}