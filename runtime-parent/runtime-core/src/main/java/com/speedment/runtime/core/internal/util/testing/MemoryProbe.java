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

/**
 *
 * @author Per Minborg
 */
public class MemoryProbe {

    private final long free, total, max;

    private MemoryProbe() {
        System.gc();
        System.gc();
        final Runtime rt = Runtime.getRuntime();
        this.free = rt.freeMemory();
        this.total = rt.totalMemory();
        this.max = rt.maxMemory();
    }

    protected MemoryProbe(long free, long total, long max) {
        this.free = free;
        this.total = total;
        this.max = max;
    }

    public long free() {
        return free;
    }

    public long total() {
        return total;
    }

    public long max() {
        return max;
    }

    public long used() {
        return total() - free();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{free=" + free()
                + ", total=" + total()
                + ", max=" + max()
                + ", used=" + used()
                + "}";
    }

    
    public MemoryProbe deltaSinceCreated() {
        return new MemoryProbe().substract(this);
    }
    
    public static MemoryProbe create() {
        return new MemoryProbe();
    }

    public MemoryProbe substract(MemoryProbe operand) {
        return new MemoryProbe(free() - operand.free(), total() - operand.total(), max() - operand.max());
    }

}
