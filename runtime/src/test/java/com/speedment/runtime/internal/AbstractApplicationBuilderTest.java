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
package com.speedment.runtime.internal;

import com.speedment.common.injector.Injector;
import com.speedment.runtime.Speedment;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Per Minborg
 */
public class AbstractApplicationBuilderTest {

    private AbstractApplicationBuilder<?, ?> instance;

    @Before
    public void setUp() {
        instance = new AbstractApplicationBuilderImpl<>(Injector.builder());
    }

    @Test
    public void checkVersion() {
        final Map<String, Optional<Boolean>> testVector = new LinkedHashMap<>();
        
        testVector.put("1.8.0_39", Optional.of(Boolean.FALSE));
        testVector.put("1.8.0_40", Optional.of(Boolean.TRUE));
        testVector.put("1.8.0_101", Optional.of(Boolean.TRUE));
        testVector.put("1.7.0_40", Optional.of(Boolean.FALSE));
        testVector.put("0.8.0_40", Optional.of(Boolean.FALSE));
        testVector.put("Arne", Optional.empty());

        for (final Entry<String, Optional<Boolean>> e : testVector.entrySet()) {
            final Optional<Boolean> expected = e.getValue();
            final Optional<Boolean> result = instance.isVersionOk(e.getKey());
            assertEquals(e.getKey(), expected, result);
        }

    }

    public class AbstractApplicationBuilderImpl<
        APP extends Speedment,
        BUILDER extends AbstractApplicationBuilder<APP, BUILDER>
    > extends AbstractApplicationBuilder<APP, BUILDER> {

        public AbstractApplicationBuilderImpl(Injector.Builder injector) {
            super(injector);
        }

        @Override
        protected APP build(Injector injector) {
            throw new UnsupportedOperationException("Should never be called.");
        }
    }
}