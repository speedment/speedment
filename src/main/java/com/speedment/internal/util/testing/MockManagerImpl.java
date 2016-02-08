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
package com.speedment.internal.util.testing;

import com.speedment.Manager;
import com.speedment.Speedment;
import com.speedment.config.db.Column;
import com.speedment.config.db.Table;
import com.speedment.db.MetaResult;
import com.speedment.exception.SpeedmentException;
import com.speedment.field.ComparableField;
import com.speedment.stream.StreamDecorator;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
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
    private Function<StreamDecorator, Stream<ENTITY>> nativeStreamer;
    private Function<StreamDecorator, Stream<ENTITY>> streamer;
    private Function<ENTITY, ENTITY> persister;
    private Function<ENTITY, ENTITY> updater;
    private Function<ENTITY, ENTITY> remover;
    private BiFunction<ComparableField<ENTITY, ? extends Comparable<?>>, Comparable<?>, Optional<ENTITY>> finder;

    public MockManagerImpl(Manager<ENTITY> inner) {
        this.inner = inner;
        this.instanceSupplier = inner::newInstance;
        this.nativeStreamer = inner::nativeStream;
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
    public MockManager<ENTITY> setNativeStreamer(Function<StreamDecorator, Stream<ENTITY>> nativeStreamer) {
        this.nativeStreamer = nativeStreamer;
        return this;
    }

    @Override
    public MockManager<ENTITY> setStreamer(Function<StreamDecorator, Stream<ENTITY>> streamer) {
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
    public MockManager<ENTITY> setFinder(BiFunction<ComparableField<ENTITY, ? extends Comparable<?>>, Comparable<?>, Optional<ENTITY>> finder) {
        this.finder = finder;
        return this;
    }

    // Manager
    @Override
    public Object primaryKeyFor(ENTITY entity) {
        return inner.primaryKeyFor(entity);
    }

    @Override
    public Object get(ENTITY entity, Column column) {
        return inner.get(entity, column);
    }

    @Override
    public void set(ENTITY entity, Column column, Object value) {
        inner.set(entity, column, value);
    }

    @Override
    public Table getTable() {
        return inner.getTable();
    }

    @Override
    public ENTITY newInstance() {
        return instanceSupplier.get();
    }

    @Override
    public Class<ENTITY> getEntityClass() {
        return inner.getEntityClass();
    }

    @Override
    public String toJson(ENTITY entity) {
        return inner.toJson(entity);
    }

    @Override
    public Stream<ENTITY> stream(StreamDecorator decorator) {
        return streamer.apply(decorator);
    }

    @Override
    public Stream<ENTITY> nativeStream(StreamDecorator decorator) {
        return nativeStreamer.apply(decorator);
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
    public <V extends Comparable<? super V>> Optional<ENTITY> findAny(ComparableField<ENTITY, V> field, V value) {
        return finder.apply(field, value);
    }

    @Override
    public ENTITY persist(ENTITY entity, Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException {
        return inner.persist(entity, consumer);
    }

    @Override
    public ENTITY update(ENTITY entity, Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException {
        return inner.update(entity, consumer);
    }

    @Override
    public ENTITY remove(ENTITY entity, Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException {
        return inner.remove(entity, consumer);
    }

    @Override
    public Manager<ENTITY> initialize() {
        return inner.initialize();
    }

    @Override
    public Manager<ENTITY> resolve() {
        return inner.resolve();
    }

    @Override
    public Manager<ENTITY> start() {
        return inner.start();
    }

    @Override
    public Manager<ENTITY> stop() {
        return inner.stop();
    }

    @Override
    public State getState() {
        return inner.getState();
    }

    @Override
    public boolean isInitialized() {
        return inner.isInitialized();
    }

    @Override
    public boolean isResolved() {
        return inner.isResolved();
    }

    @Override
    public boolean isStarted() {
        return inner.isStarted();
    }

    @Override
    public boolean isStopped() {
        return inner.isStopped();
    }

    @Override
    public Speedment speedment() {
        return inner.speedment();
    }

}
