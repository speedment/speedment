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
package com.speedment.common.lazy.specialized;

import com.speedment.common.lazy.AbstractLazyTest;
import com.speedment.common.lazy.Lazy;

import java.math.BigInteger;

/**
 * @author pemi
 */
public class LazyBigIntegerTest extends AbstractLazyTest<BigInteger> {

    @Override
    protected BigInteger firstValue() {
        return BigInteger.ZERO;
    }

    @Override
    protected BigInteger secondValue() {
        return BigInteger.ONE;
    }

    @Override
    protected Lazy<BigInteger> newInstance() {
        return LazyBigInteger.create();
    }

    @Override
    protected BigInteger makeFromThread(Thread t) {
        return BigInteger.valueOf(t.getId());
    }

}
