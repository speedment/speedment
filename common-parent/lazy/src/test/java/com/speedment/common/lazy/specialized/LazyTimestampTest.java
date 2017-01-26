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

import java.sql.Timestamp;

/**
 * @author pemi
 */
public class LazyTimestampTest extends AbstractLazyTest<Timestamp> {

    @Override
    protected Timestamp firstValue() {
        return Timestamp.valueOf("2011-01-01 01:01:01");
    }

    @Override
    protected Timestamp secondValue() {
        return Timestamp.valueOf("2012-02-02 02:02:02");
    }

    @Override
    protected Lazy<Timestamp> newInstance() {
        return LazyTimestamp.create();
    }

    @Override
    protected Timestamp makeFromThread(Thread t) {
        return new Timestamp(t.getId());
    }

}
