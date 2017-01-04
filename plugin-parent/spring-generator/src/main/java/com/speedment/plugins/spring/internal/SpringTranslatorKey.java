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
package com.speedment.plugins.spring.internal;

import com.speedment.common.codegen.model.Class;
import com.speedment.generator.translator.TranslatorKey;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;

/**
 * Holds the static instances representing the different classes and interfaces
 * that this plugin generates.
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class SpringTranslatorKey {

    public final static TranslatorKey<Project, Class> 
        CONFIGURATION           = TranslatorKey.of("Configuration", Class.class),
        GENERATED_CONFIGURATION = TranslatorKey.of("GeneratedConfiguration", Class.class);
    
    public final static TranslatorKey<Table, Class> 
        CONTROLLER           = TranslatorKey.of("Controller", Class.class),
        GENERATED_CONTROLLER = TranslatorKey.of("GeneratedController", Class.class);
    
    /**
     * This class should never be instantiated.
     */
    private SpringTranslatorKey() {}
}
