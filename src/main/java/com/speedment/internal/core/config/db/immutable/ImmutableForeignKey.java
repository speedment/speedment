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
package com.speedment.internal.core.config.db.immutable;

import com.speedment.internal.core.config.*;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Table;
import com.speedment.config.aspects.Ordinable;
import com.speedment.config.aspects.Parent;
import static com.speedment.internal.core.config.db.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import groovy.lang.Closure;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class ImmutableForeignKey extends ImmutableAbstractNamedConfigEntity implements ForeignKey, ImmutableParentHelper<ForeignKeyColumn> {

    private final Optional<Table> parent;
    private final ChildHolder<ForeignKeyColumn> children;

    public ImmutableForeignKey(Table parent, ForeignKey fk) {
        super(requireNonNull(fk).getName(), fk.isExpanded(), fk.isEnabled());
        requireNonNull(parent);
        // Fields
        this.parent = Optional.of(parent);
        // Children
        children = childHolderOf(ForeignKeyColumn.class, fk.stream().map(fkc -> new ImmutableForeignKeyColumn(this, fkc)));
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
    public ChildHolder<ForeignKeyColumn> getChildren() {
        return children;
    }
    
    @Override
    public Stream<? extends ForeignKeyColumn> stream() {
        return getChildren().stream().sorted(Ordinable.COMPARATOR);
    }

    @Override
    public <T extends ForeignKeyColumn> Stream<T> streamOf(Class<T> childClass) {
        if (ForeignKeyColumn.class.isAssignableFrom(childClass)) {
            return getChildren().stream()
                .map(child -> {
                    @SuppressWarnings("unchecked")
                    final T cast = (T) child;
                    return cast;
                }).sorted(Ordinable.COMPARATOR);
        } else {
            throw new IllegalArgumentException(
                getClass().getSimpleName() + 
                " does not have children of type " + 
                childClass.getSimpleName() + "."
            );
        }
    }

    @Override
    public ForeignKeyColumn addNewForeignKeyColumn() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public ForeignKeyColumn foreignKeyColumn(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }
}
