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
package com.speedment.generator.core.component;

import com.speedment.common.injector.annotation.InjectKey;

import java.nio.file.Path;

/**
 * A component that can be used to determine the {@code Path} to the project.
 * This is used by the generator to determine where to put generated files.
 *
 * @author Emil Forslund
 * @since  3.0.0
 */
@InjectKey(PathComponent.class)
public interface PathComponent {

    
    /**
     * Returns the base directory of the project (the same folder as the 
     * {@code pom.xml}-file is located in for Maven-projects.
     * 
     * @return  the base directory
     */
    Path baseDir();
    
    /**
     * Returns the root folder where generated sources will be put. This is
     * usually the same folder as the {@code com}-folder is located in if the
     * generated package name starts with {@code com.company}.
     * 
     * @return  the generated package location
     */
    Path packageLocation();
    
}