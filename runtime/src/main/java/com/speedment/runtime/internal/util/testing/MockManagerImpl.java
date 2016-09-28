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
package com.speedment.runtime.internal.util.testing;

import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.manager.Manager;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <ENTITY> type
 */
public class MockManagerImpl<ENTITY> implements MockManager<ENTITY> {

    private final Manager<ENTITY> inner;
    private Supplier<ENTITY> instanceSupplier;
    private Supplier<Stream<ENTITY>> streamer;
    private Function<ENTITY, ENTITY> persister;
    private Function<ENTITY, ENTITY> updater;
    private Function<ENTITY, ENTITY> remover;

    public MockManagerImpl(Manager<ENTITY> inner) {
        this.inner = inner;
        this.instanceSupplier = inner::newEmptyEntity;
        this.streamer = inner::stream;
        this.persister = inner::persist;
        this.updater = inner::update;
        this.remover = inner::remove;
    }

    // MockManager
    @Override
    public MockManager<ENTITY> setInstanceFactory(Supplier<ENTITY> factory) {
        instanceSupplier = factory;
        return this;
    }

    @Override
    public MockManager<ENTITY> setStreamer(Supplier<Stream<ENTITY>> streamer) {
        this.streamer = streamer;
        return this;
    }

    @Override
    public MockManager<ENTITY> setPersister(Function<ENTITY, ENTITY> persister) {
        this.persister = persister;
        return this;
    }

    @Override
    public MockManager<ENTITY> setUpdater(Function<ENTITY, ENTITY> updater) {
        this.updater = updater;
        return this;
    }

    @Override
    public MockManager<ENTITY> setRemover(Function<ENTITY, ENTITY> remover) {
        this.remover = remover;
        return this;
    }

    @Override
    public ENTITY newEmptyEntity() {
        return instanceSupplier.get();
    }

    @Override
    public Class<ENTITY> getEntityClass() {
        return inner.getEntityClass();
    }

    @Override
    public ENTITY persist(ENTITY entity) throws SpeedmentException {
        return persister.apply(entity);
    }

    @Override
    public ENTITY update(ENTITY entity) throws SpeedmentException {
        return updater.apply(entity);
    }

    @Override
    public ENTITY remove(ENTITY entity) throws SpeedmentException {
        return remover.apply(entity);
    }

    @Override
    public ENTITY newCopyOf(ENTITY source) {
        return inner.newCopyOf(source);
    }

    @Override
    public String getDbmsName() {
        return inner.getDbmsName();
    }

    @Override
    public String getSchemaName() {
        return inner.getSchemaName();
    }

    @Override
    public String getTableName() {
        return inner.getTableName();
    }

    @Override
    public Stream<Field<ENTITY>> fields() {
        return inner.fields();
    }

    @Override
    public Stream<Field<ENTITY>> primaryKeyFields() {
        return inner.primaryKeyFields();
    }

    @Override
    public Stream<ENTITY> stream() {
        return streamer.get();
    }
}