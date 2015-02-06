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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.orm.config.model.parameters;

import com.speedment.orm.annotations.Api;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public enum StorageEngineType implements Nameable<StorageEngineType> {

    ON_HEAP("On Heap", ConcurrentHashMap.class, true),
    OFF_HEAP("Off Heap", ConcurrentHashMap.class, true),
    HAZELCAST("Hazelcast", ConcurrentHashMap.class, false);
    private final String name;
    private final Class<? extends Map> implementationClass;
    private final boolean useNativeMaps;
    public final static StorageEngineType DEFAULT_STORAGE_ENGINE = ON_HEAP;

    static final Map<String, StorageEngineType> NAME_MAP = Nameable.Hidden.buildMap(values());

    private StorageEngineType(final String name, final Class<? extends Map> implementationClass, boolean useNativeMaps) {
        this.name = name;
        this.implementationClass = implementationClass;
        this.useNativeMaps = useNativeMaps;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * @return the implName
     */
    public Class<? extends Map> getImplementationClass() {
        return implementationClass;
    }

    public static Optional<StorageEngineType> findByNameIgnoreCase(final String name) {
        return Nameable.Hidden.findByNameIgnoreCase(NAME_MAP, name);
    }

    /**
     * Returns if this StorageEngine shall generate code for on-heap storage.
     * Several engines can rely on this, not only ON_HEAP.
     *
     * @return if this StorageEngine shall generate code for on-heap storage
     */
    public boolean isOnHeap() {
        return this == ON_HEAP;
    }

    /**
     * Returns if this StorageEngine shall generate code for off-heap storage.
     * Several engines can rely on this, not only OFF_HEAP.
     *
     * @return if this StorageEngine shall generate code for off-heap storage
     */
    public boolean isOffHeap() {
        return this == OFF_HEAP;
    }

    /**
     * Returns if this StorageEngine shall generate code for HAZELCAST storage.
     * Several engines can rely on this, not only HAZELCAST.
     *
     * @return if this StorageEngine shall generate code for HAZELCAST storage
     */
    public boolean isHazelcast() {
        return this == HAZELCAST;
    }

    public boolean isUseNativeMaps() {
        return useNativeMaps;
    }

    public static Stream<StorageEngineType> stream() {
        return Stream.of(values());
    }
}
