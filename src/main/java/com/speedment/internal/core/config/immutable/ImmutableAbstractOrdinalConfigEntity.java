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
package com.speedment.internal.core.config.immutable;

import com.speedment.config.aspects.Ordinable;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import static java.util.Objects.requireNonNull;

/**
 * Generic representation of a ConfigEntity.
 *
 * This class is thread safe.
 *
 * @author pemi
 */
public abstract class ImmutableAbstractOrdinalConfigEntity extends ImmutableAbstractConfigEntity
        implements Ordinable, Comparable<Ordinable> {

    private final int ordinalPosition;

    public ImmutableAbstractOrdinalConfigEntity(String name, boolean enabled, int ordinalPosition) {
        super(name, enabled);
        this.ordinalPosition = ordinalPosition;
    }

    @Override
    public final int getOrdinalPosition() {
        return ordinalPosition;
    }

    @Override
    public final void setOrdinalPosition(int ordinalPosition) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public final int compareTo(Ordinable that) {
        requireNonNull(that);
        return Integer.compare(
                this.getOrdinalPosition(),
                that.getOrdinalPosition()
        );

    }
}
