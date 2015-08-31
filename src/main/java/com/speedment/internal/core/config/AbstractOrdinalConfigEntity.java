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
package com.speedment.internal.core.config;

import com.speedment.config.aspects.Ordinable;

/**
 * Generic representation of a ConfigEntity.
 *
 * This class is thread safe.
 *
 * @author pemi
 */
public abstract class AbstractOrdinalConfigEntity extends AbstractConfigEntity
        implements Ordinable, Comparable<Ordinable> {

    private int ordinalPosition;

    public AbstractOrdinalConfigEntity() {
        super(null);
        ordinalPosition = Ordinable.UNSET;
    }

    @Override
    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    @Override
    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    @Override
    public int compareTo(Ordinable that) {
        if (that == null) {
            return Ordinable.UNSET;
        } else {
            return Integer.compare(
                this.getOrdinalPosition(),
                that.getOrdinalPosition()
            );
        }
    }
}
