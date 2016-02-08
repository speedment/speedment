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
package com.speedment.internal.core.stream.parallelstrategy;

import java.util.stream.IntStream;

/**
 *
 * @author pemi
 */
public final class ComputeIntensityUtil {

    static IntStream repeatOnHalfAvailableProcessors(int item) {
        return repeat(item, availableProcessors() / 2);
    }

    static int toThePowerOfTwo(int x) {
        return 1 << x;
    }

    static int times256(int x) {
        return x * 256;
    }

    static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    static IntStream repeat(int item, int times) {
        return IntStream.generate(() -> item).limit(times);
    }

    private ComputeIntensityUtil() {
    }

}
