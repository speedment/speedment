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

import com.speedment.Speedment;
import com.speedment.internal.core.config.aspects.ParentHelper;
import com.speedment.config.ForeignKey;
import com.speedment.config.ForeignKeyColumn;
import com.speedment.config.Schema;
import com.speedment.config.Table;
import com.speedment.config.aspects.Nameable;
import com.speedment.config.aspects.Ordinable;
import com.speedment.config.aspects.Parent;
import com.speedment.internal.core.config.utils.ConfigUtil;
import com.speedment.internal.util.Cast;
import groovy.lang.Closure;
import java.util.Optional;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class ForeignKeyImpl extends AbstractNamedNode implements ForeignKey, ParentHelper<ForeignKeyColumn> {

    private final Speedment speedment;
    private Table parent;
    private final ChildHolder<ForeignKeyColumn> children;
    
    public ForeignKeyImpl(Speedment speedment) {
        this.speedment = requireNonNull(speedment);
        this.children = new ChildHolderImpl<>(ForeignKeyColumn.class);
    }
    
    @Override
    protected void setDefaults() {}

    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.castOrFail(parent, Table.class);
    }

    @Override
    public Optional<Table> getParent() {
        return Optional.ofNullable(parent);
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
        final ForeignKeyColumn e = ForeignKeyColumn.newForeignKeyColumn();
        add(e);
        return e;
    }
    
    @Override
    public ForeignKeyColumn foreignKeyColumn(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewForeignKeyColumn);
    }
}
