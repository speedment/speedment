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

import com.speedment.config.Column;
import com.speedment.config.PrimaryKeyColumn;
import com.speedment.config.Table;
import com.speedment.config.aspects.Parent;
import com.speedment.internal.core.config.aspects.ColumnableHelper;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import java.util.Optional;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class ImmutablePrimaryKeyColumn extends ImmutableAbstractOrdinalConfigEntity implements PrimaryKeyColumn, ColumnableHelper {

    private final Optional<Table> parent;
    private Column column; // Which column is this PK refering to?

    public ImmutablePrimaryKeyColumn(Table parent, PrimaryKeyColumn primaryKeyColumn) {
        super(requireNonNull(primaryKeyColumn).getName(), primaryKeyColumn.isEnabled(), primaryKeyColumn.isExpanded(), primaryKeyColumn.getOrdinalPosition());
        requireNonNull(parent);
        // Fields
        this.parent = Optional.of(parent);
    }

    @Override
    public void setParent(Parent<?> parent) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<Table> getParent() {
        return parent;
    }

    @Override
    public Column getColumn() {
        return column;
    }

    @Override
    public void resolve() {
        column = ColumnableHelper.super.getColumn();
    }

}
