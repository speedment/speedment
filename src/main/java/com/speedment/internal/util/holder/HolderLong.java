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
package com.speedment.internal.util.holder;

/**
 * A class for holding a {@link long}. This class can be used when {@code final}
 * or effectively final variables must be used, for example in lambdas or
 * Threads.
 *
 * @author pemi
 */
public final class HolderLong {

    private long value;

    /**
     * Constructs an empty Holder
     */
    public HolderLong() {
    }

    /**
     * Constructs a Holder with the provided value
     *
     * @param value to use
     */
    public HolderLong(long value) {
        this.value = value;
    }

    public void set(long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }

}
