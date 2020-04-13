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
package com.speedment.common.injector.dependency;

import com.speedment.common.injector.State;
import com.speedment.common.injector.internal.dependency.DependencyImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
final class DependencyTest {

    @Mock
    private DependencyNode dependencyNode;

    @Test
    void isSatisfied() {
        final Dependency dependency = new DependencyImpl(dependencyNode, State.STARTED);
        when(dependencyNode.is(State.STARTED)).thenReturn(false);
        assertFalse(dependency.isSatisfied());
    }

}