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
package com.speedment.runtime.license;

import com.speedment.runtime.annotation.Api;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Represents a third-party dependency. This is used to keep track of the 
 * licenses that are used.
 * 
 * @author  Emil Forslund
 * @since   2.3.0
 */
@Api(version = "3.0")
public interface Software {
    
    final Comparator<Software> COMPARATOR = 
        Comparator.comparing(Software::getLicense, License.COMPARATOR)
            .thenComparing(Software::getName)
            .thenComparing(Software::getVersion);
    
    /**
     * The name of this software.
     * 
     * @return  the name
     */
    String getName();
    
    /**
     * The version of this software.
     * 
     * @return  the version
     */
    String getVersion();
    
    /**
     * A short description of what the component does that can be used 
     * to illustrate it in for an example the user interface.
     * <p>
     * By default this is an empty {@code Optional}.
     * 
     * @return  the description or an empty {@code Optional}
     */
    default Optional<String> getDescription() {
        return Optional.empty();
    }
    
    /**
     * The license of this software.
     * 
     * @return  the license
     */
    License getLicense();
    
    /**
     * Returns a stream of the software dependencies that this software has.
     * 
     * @return  external dependencies
     */
    Stream<Software> getDependencies();
    
    /**
     * Returns true if this software represent an internal module in the
     * Speedment framework that is included in the Speedment Open Source 
     * license.
     * 
     * @return  true if this is an internal software
     */ 
    boolean isInternal();
}