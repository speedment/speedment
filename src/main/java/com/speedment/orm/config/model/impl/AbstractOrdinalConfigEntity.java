/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.orm.config.model.impl;

import com.speedment.orm.config.model.ConfigEntity;
import com.speedment.orm.config.model.OrdinalConfigEntity;

/**
 * Generic representation of a ConfigEntity.
 *
 * This class is thread safe.
 *
 * @author pemi
 * @param <T>
 * @param <P>
 * @param <C>
 */
public abstract class AbstractOrdinalConfigEntity<T extends OrdinalConfigEntity<T, P, C>, P extends ConfigEntity<?, ?, ?>, C extends ConfigEntity<?, ?, ?>>
        extends AbstractConfigEntity<T, P, C>
        implements OrdinalConfigEntity<T, P, C> {

    private int ordinalPosition;

    @Override
    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    @Override
    public T setOrdinalPosition(int ordinalPosition) {
        return run(() -> this.ordinalPosition = ordinalPosition);
    }

    @Override
    public int compareTo(T o) {
        return Integer.compare(getOrdinalPosition(), o.getOrdinalPosition());
    }

}
