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
package com.speedment.orm.core.manager;

import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Buildable;
import com.speedment.orm.core.Persistable;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public class DefaultManager<T> implements Manager<T> {

    private final Table table;

    private Supplier<? extends Buildable<T>> builderSupplier;
    private Function<T, ? extends Buildable<T>> builderFunction;
    private Supplier<? extends Persistable<T>> persistorSupplier;
    private Function<T, ? extends Persistable<T>> persistorFunction;

    public DefaultManager(Table table) {
        this.table = table;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <B extends Buildable<T>> B builder(Class<B> builderClass) {
        return (B) builderSupplier.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <B extends Buildable<T>> B builderOf(Class<B> builderClass, T model) {
        return (B) builderFunction.apply(model);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P extends Persistable<T>> P persister(Class<P> persistableClass) {
        return (P) persistorSupplier.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P extends Persistable<T>> P persisterOf(Class<P> persistableClass, T model) {
        return (P) persistorFunction.apply(model);
    }

    @Override
    public Table getTable() {
        return table;
    }

    @Override
    public Stream<T> stream() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T insert(T entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T update(T entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object pk) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void load() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
