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
package com.speedment.runtime.core.internal.field.method;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.field.ShortField;
import com.speedment.runtime.core.field.ShortForeignKeyField;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Generated;

/**
 * @param <ENTITY>    entity type
 * @param <FK_ENTITY> foreign entity type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class FindFromShort<ENTITY, FK_ENTITY> extends AbstractFindFrom<ENTITY, FK_ENTITY, Short, ShortForeignKeyField<ENTITY, ?, FK_ENTITY>, ShortField<FK_ENTITY, ?>> {
    
    public FindFromShort(ShortForeignKeyField<ENTITY, ?, FK_ENTITY> source, ShortField<FK_ENTITY, ?> target, TableIdentifier<FK_ENTITY> identifier, Supplier<Stream<FK_ENTITY>> streamSupplier) {
        super(source, target, identifier, streamSupplier);
    }
    
    @Override
    public FK_ENTITY apply(ENTITY entity) {
        final short value = getSourceField().getter().applyAsShort(entity);
        return stream()
            .filter(getTargetField().equal(value))
            .findAny()
            .orElseThrow(() -> new SpeedmentException(
                "Error! Could not find any entities in table '" + 
                getTableIdentifier() + 
                "' with '" + getTargetField().identifier().getColumnName() + 
                "' = '" + value + "'."
            ));
    }
}