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
package com.speedment.runtime.core.internal.stream.builder;

import com.speedment.runtime.core.internal.stream.builder.pipeline.PipelineImpl;
import com.speedment.runtime.core.internal.stream.builder.streamterminator.StreamTerminator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author Per Minborg
 */
final class ReferenceStreamBuilderTest {

    private ReferenceStreamBuilder<String> instance;

    @BeforeEach
    void setUp() {
        final PipelineImpl<String> pipeline = new PipelineImpl<>(() -> Stream.of("A", "B"));
        final StreamTerminator streamTerminator = new MockStreamTerminator();
        instance = new ReferenceStreamBuilder<>(pipeline, streamTerminator);
    }


    @Test
    void testIssue384() {
        final Stream<String> s0 = instance;
        final Stream<String> s1 = s0.map(s -> s + " Svensson");
        assertThrows(IllegalStateException.class, s0::count, "s0 could be reused");
        assertDoesNotThrow((Executable) s1::count);
    }

    public final class MockStreamTerminator implements StreamTerminator {
    }

}
