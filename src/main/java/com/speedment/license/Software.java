/*
 * Copyright 2016 Speedment, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.speedment.license;

import com.speedment.annotation.Api;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Represents a third-party dependency.
 * 
 * @author Emil Forslund
 * @since  2.3
 */
@Api(version = "2.3")
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
}