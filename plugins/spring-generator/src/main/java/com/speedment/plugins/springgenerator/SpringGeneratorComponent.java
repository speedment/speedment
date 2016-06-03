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
package com.speedment.plugins.springgenerator;

import com.speedment.generator.component.CodeGenerationComponent;
import com.speedment.plugins.springgenerator.internal.ConfigurationTranslator;
import com.speedment.plugins.springgenerator.internal.ControllerTranslator;
import com.speedment.plugins.springgenerator.internal.GeneratedConfigurationTranslator;
import com.speedment.plugins.springgenerator.internal.GeneratedControllerTranslator;
import com.speedment.plugins.springgenerator.internal.SpringTranslatorKey;
import com.speedment.runtime.Speedment;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.internal.component.AbstractComponent;
import com.speedment.runtime.internal.license.AbstractSoftware;
import static com.speedment.runtime.internal.license.OpenSourceLicense.APACHE_2;
import com.speedment.runtime.license.Software;

/**
 * Plugins into the Speedment platform, generating Spring annoted classes as
 * part of the regular code generation process.
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class SpringGeneratorComponent extends AbstractComponent {

    public SpringGeneratorComponent(Speedment speedment) {
        super(speedment);
    }

    @Override
    public void onResolve() {
        final CodeGenerationComponent code = 
            getSpeedment().getOrThrow(CodeGenerationComponent.class);
        
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

    @Override
    public Class<SpringGeneratorComponent> getComponentClass() {
        return SpringGeneratorComponent.class;
    }

    @Override
    public Software asSoftware() {
        return AbstractSoftware.with("Spring Generator", "1.0.0", APACHE_2);
    }

    @Override
    public SpringGeneratorComponent defaultCopy(Speedment speedment) {
        return new SpringGeneratorComponent(speedment);
    }
}