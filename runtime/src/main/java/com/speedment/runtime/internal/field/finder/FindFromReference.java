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
package com.speedment.runtime.internal.field.finder;

import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.manager.Manager;

/**
 *
 * @param <ENTITY>     the source entity
 * @param <FK_ENTITY>  the target entity
 * @param <T>          the column type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public class FindFromReference<ENTITY, FK_ENTITY, T extends Comparable<? super T>>
    extends AbstractFindFrom<ENTITY, FK_ENTITY, ReferenceField<ENTITY, ?, T>, ReferenceField<FK_ENTITY, ?, T>> {

    public FindFromReference(ReferenceField<ENTITY, ?, T> source, ReferenceField<FK_ENTITY, ?, T> target, Manager<FK_ENTITY> manager) {
        super(source, target, manager);
    }

    @Override
    public FK_ENTITY apply(ENTITY entity) {
        final T value = getSourceField().getter().apply(entity);
        return getTargetManager().findAny(getTargetField(), value)
            .orElseThrow(() -> new SpeedmentException(
                "Error! Could not find any " + 
                getTargetManager().getEntityClass().getSimpleName() + 
                " with '" + getTargetField().identifier().columnName() + 
                "' = '" + value + "'."
            ));
    }
}
