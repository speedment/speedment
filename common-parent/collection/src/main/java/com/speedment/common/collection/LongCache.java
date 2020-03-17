/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.collection;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.LongSupplier;

/**
 * A cache of {@code long}-values associated with a particular key. The cache
 * has a maximum capacity and will start to evict the oldest entries when the
 * maximum capacity has been reached.
 * <p>
 * This implementation is concurrent and lock-free.
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class LongCache<K> {

    private final AtomicBoolean free;
    private final LinkedHashMap<K, Long> cache;

    /**
     * Creates a new {@code LongCache} with the specified maximum size.
     *
     * @param maxSize  the maximum size
     */
    public LongCache(int maxSize) {
        free  = new AtomicBoolean(true);
        cache = new LinkedHashMap<K, Long>(maxSize, 0.75f, true) {
            private static final long serialVersionUID = 1L;
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, Long> eldest) {
                return size() > maxSize;
            }
        };
    }

    /**
     * This method will return the value for the specified key if it is cached,
     * and if not, will calculate the value using the supplied method. The
     * computed value may or may not be stored in the cache afterwards. This
     * method is guaranteed to be lock-free, and might calculate the value
     * instead of using the cached one to avoid blocking.
     *
     * @param key      the key to retrieve the value for
     * @param compute  method to use to compute the value if it is not cached
     * @return         the cached or computed value
     */
    public long getOrCompute(K key, LongSupplier compute) {
        if (free.compareAndSet(true, false)) {
            try {
                return cache.computeIfAbsent(key, k -> compute.getAsLong());
            } finally {
                free.set(true);
            }
        } else {
            return compute.getAsLong();
        }
    }

    @Override
    public String toString() {
        return "LongCache{cache=" + cache + '}';
    }
}
