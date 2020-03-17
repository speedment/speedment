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

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.FloatField;
import com.speedment.runtime.field.FloatForeignKeyField;
import com.speedment.runtime.field.exception.SpeedmentFieldException;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @param <ENTITY>    entity type
 * @param <FK_ENTITY> foreign entity type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@GeneratedCode(value = "Speedment")
public final class FindFromFloat<ENTITY, FK_ENTITY> extends AbstractFindFrom<ENTITY, FK_ENTITY, Float, FloatForeignKeyField<ENTITY, ?, FK_ENTITY>, FloatField<FK_ENTITY, ?>> {
    
    public FindFromFloat(
            FloatForeignKeyField<ENTITY, ?, FK_ENTITY> source,
            FloatField<FK_ENTITY, ?> target,
            TableIdentifier<FK_ENTITY> identifier,
            Supplier<Stream<FK_ENTITY>> streamSupplier) {
        super(source, target, identifier, streamSupplier);
    }
    
    @Override
    public FK_ENTITY apply(ENTITY entity) {
        final float value = getSourceField().getter().applyAsFloat(entity);
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