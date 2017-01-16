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
package com.speedment.common.injector.internal.dependency;

import com.speedment.common.injector.exception.CyclicReferenceException;

import java.util.stream.Stream;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface DependencyGraph {
    
    DependencyNode get(Class<?> clazz) throws CyclicReferenceException;
    
    DependencyNode getOrCreate(Class<?> clazz);
    
    DependencyGraph inject() throws CyclicReferenceException;
    
    Stream<DependencyNode> nodes();
    
}