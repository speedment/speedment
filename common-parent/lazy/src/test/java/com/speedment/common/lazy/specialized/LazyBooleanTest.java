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
import org.junit.Ignore;

import java.util.concurrent.ExecutionException;

/**
 * @author pemi
 */
public class LazyBooleanTest extends AbstractLazyTest<Boolean> {

    @Override
    protected Boolean firstValue() {
        return Boolean.FALSE;
    }

    @Override
    protected Boolean secondValue() {
        return Boolean.TRUE;
    }

    @Override
    protected Lazy<Boolean> newInstance() {
        return LazyBoolean.create();
    }

    @Override
    @Ignore
    public void testConcurrency() throws InterruptedException, ExecutionException {
        // Ignore for Boolean with only two values
    }

    @Override
    protected Boolean makeFromThread(Thread t) {
        throw new UnsupportedOperationException();
    }

}
