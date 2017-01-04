/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.util.testing;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.core.manager.Persister;
import com.speedment.runtime.core.manager.Remover;
import com.speedment.runtime.core.manager.Updater;
import com.speedment.runtime.field.Field;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <ENTITY> type
 */
public class MockManagerImpl<ENTITY> implements MockManager<ENTITY> {

    private final Manager<ENTITY> inner;
    private Supplier<ENTITY> entityCreator;
    private Supplier<Stream<ENTITY>> streamer;
    private Persister<ENTITY> persister;
    private Updater<ENTITY> updater;
    private Remover<ENTITY> remover;

    public MockManagerImpl(Manager<ENTITY> inner) {
        this.inner = inner;
//        this.entityCreator = inner.entityCreator();
        this.streamer = inner::stream;
        this.persister = inner.persister();
        this.updater = inner.updater();
        this.remover = inner.remover();
    }

    // MockManager
//    @Override
//    public MockManager<ENTITY> setEntityCreator(EntityCreator<ENTITY> factory) {
//        entityCreator = factory;
//        return this;
//    }

    @Override
    public MockManager<ENTITY> setStreamer(Supplier<Stream<ENTITY>> streamer) {
        this.streamer = streamer;
        return this;
    }

    @Override
    public MockManager<ENTITY> setPersister(Persister<ENTITY> persister) {
        this.persister = persister;
        return this;
    }

    @Override
    public MockManager<ENTITY> setUpdater(Updater<ENTITY> updater) {
        this.updater = updater;
        return this;
    }

    @Override
    public MockManager<ENTITY> setRemover(Remover<ENTITY> remover) {
        this.remover = remover;
        return this;
    }

//    @Override
//    public ENTITY entityCreate() {
//        return entityCreator.get();
//    }
//
//    @Override
//    public Supplier<ENTITY> entityCreator() {
//        return entityCreator;
//    }

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
    public Persister<ENTITY> persister() {
        return persister;
    }

    @Override
    public ENTITY remove(ENTITY entity) throws SpeedmentException {
        return remover.apply(entity);
    }

    @Override
    public Remover<ENTITY> remover() {
        return remover;
    }
//
//    @Override
//    public ENTITY entityCopy(ENTITY source) {
//        return inner.entityCopy(source);
//    }

    @Override
    public TableIdentifier<ENTITY> getTableIdentifier() {
        return inner.getTableIdentifier();
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

//    @Override
//    public UnaryOperator<ENTITY> entityCopier() {
//        return inner.entityCopier();
//    }

    @Override
    public Updater<ENTITY> updater() {
        return updater;
    }
}
