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
package com.speedment.internal.gui.config;

import com.speedment.Speedment;
import com.speedment.config.Column;
import com.speedment.config.PrimaryKeyColumn;
import com.speedment.config.Table;
import com.speedment.config.aspects.Parent;
import com.speedment.exception.SpeedmentException;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public final class PrimaryKeyColumnProperty extends AbstractNodeProperty implements PrimaryKeyColumn, ChildHelper<PrimaryKeyColumn, Table> {

    private Table parent;
    private int ordinalPosition;
    
    public PrimaryKeyColumnProperty(Speedment speedment) {
        super(speedment);
    }
    
    public PrimaryKeyColumnProperty(Speedment speedment, PrimaryKeyColumn prototype) {
        super(speedment, prototype);
        ordinalPosition = prototype.getOrdinalPosition();
    }
    
    @Override
    public Optional<Table> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setParent(Parent<?> parent) {
        if (parent instanceof Table) {
            this.parent = (Table) parent;
        } else {
            throw wrongParentClass(parent.getClass());
        }
    }

    @Override
    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    @Override
    public int getOrdinalPosition() {
        return ordinalPosition;
    }
    
    @Override
    public Column getColumn() {
        return ancestor(Table.class)
            .orElseThrow(() -> new SpeedmentException(
                "Found no ancestor table from this "
                + getClass().getSimpleName() + "."
            )).findColumn(getName());
    }
}