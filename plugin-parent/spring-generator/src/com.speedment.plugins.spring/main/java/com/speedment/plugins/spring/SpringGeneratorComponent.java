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
package com.speedment.plugins.spring;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.generator.translator.component.CodeGenerationComponent;
import com.speedment.plugins.spring.internal.*;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;

import static com.speedment.common.injector.State.RESOLVED;

/**
 * Plugs into the Speedment platform, generating Spring annotated classes as
 * part of the regular code generation process.
 * <p>
 * <em>Usage:</em>
 * Add the following configuration tag to the {@code speedment-maven-plugin}:
 * {@code 
 *     <components>
 *         <component>com.speedment.plugins.spring.SpringGeneratorComponent</component>
 *     </components>
 * }
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class SpringGeneratorComponent {

    @ExecuteBefore(RESOLVED)
    void onResolve(@WithState(RESOLVED) CodeGenerationComponent code) {
        code.put(Project.class, 
            SpringTranslatorKey.CONFIGURATION, 
            ConfigurationTranslator::new
        );
        
        code.put(Project.class, 
            SpringTranslatorKey.GENERATED_CONFIGURATION, 
            GeneratedConfigurationTranslator::new
        );
        
        code.put(Table.class, 
            SpringTranslatorKey.CONTROLLER, 
            ControllerTranslator::new
        );
        
        code.put(Table.class, 
            SpringTranslatorKey.GENERATED_CONTROLLER, 
            GeneratedControllerTranslator::new
        );
    }
}