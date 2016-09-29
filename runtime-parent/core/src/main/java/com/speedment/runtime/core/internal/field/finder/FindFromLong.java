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
package com.speedment.runtime.core.internal.field.finder;

import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.field.LongField;
import com.speedment.runtime.core.field.LongForeignKeyField;
import com.speedment.runtime.core.manager.Manager;

import javax.annotation.Generated;

/**
 * @param <ENTITY>    entity type
 * @param <FK_ENTITY> foreign entity type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
@Generated(value = "Speedment")
public final class FindFromLong<ENTITY, FK_ENTITY> extends AbstractFindFrom<ENTITY, FK_ENTITY, Long, LongForeignKeyField<ENTITY, ?, FK_ENTITY>, LongField<FK_ENTITY, ?>> {
    
    public FindFromLong(LongForeignKeyField<ENTITY, ?, FK_ENTITY> source, LongField<FK_ENTITY, ?> target, Manager<FK_ENTITY> manager) {
        super(source, target, manager);
    }
    
    @Override
    public FK_ENTITY apply(ENTITY entity) {
        final long value = getSourceField().getter().applyAsLong(entity);
        return getTargetManager().stream()
            .filter(getTargetField().equal(value))
            .findAny()
            .orElseThrow(() -> new SpeedmentException(
                "Error! Could not find any " + 
                getTargetManager().getEntityClass().getSimpleName() + 
                " with '" + getTargetField().identifier().getColumnName() + 
                "' = '" + value + "'."
            ));
    }
}